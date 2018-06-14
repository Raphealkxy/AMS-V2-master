package com.eric.modules.ams.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.eric.common.utils.*;
import com.eric.face.entity.FaceSetEntity;
import com.eric.face.entity.Response;
import com.eric.face.operate.FaceSetOperate;
import com.eric.modules.ams.dao.AmsCourseDao;
import com.eric.modules.ams.dao.AmsFacesetDao;
import com.eric.modules.ams.entity.AmsFacesetEntity;
import com.eric.modules.ams.param.SearchFacesetParam;
import com.eric.modules.ams.service.AmsFaceManageService;
import com.eric.modules.ams.service.AmsFacesetService;
import com.eric.modules.sys.dao.SysDeptDao;
import com.eric.modules.sys.dao.SysUserDao;
import com.eric.modules.sys.entity.SysDeptEntity;
import com.eric.modules.sys.entity.SysUserEntity;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Chen 2018/1/27
 */
@Service
public class AmsFacesetServiceImpl implements AmsFacesetService {
    private static final String apiKey = "oYwQhiv7TB3k8s2YBvfK3Yfb2b8WDXCk";
    private static final String apiSecret = "PW-jHfdcws8gqfOfL83xYN7Xw7RhHxOC";
    private static final boolean isInternationalVersion =false;//是否是国际版
    private static final String attributes = "facequality"; //获取人脸属性的参数
    private static FaceSetOperate faceSetOperate = new FaceSetOperate(apiKey,apiSecret,isInternationalVersion);
    @Autowired
    private AmsFacesetDao amsFacesetDao;
    @Autowired
    private AmsFaceManageService amsFaceManageService;
    @Autowired
    private AmsCourseDao amsCourseDao;
    @Autowired
    private SysDeptDao sysDeptDao;
    @Autowired
    private SysUserDao sysUserDao;
    /**
     * 列表
     */
    @Override
    public PageUtils queryList(SearchFacesetParam param) {
        Example example = BaseExample.getExample(AmsFacesetEntity.class,param);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getDeptName()))
            criteria.andLike("deptName", AmsUtils.like(param.getDeptName()));
        if (StringUtils.isNotBlank(param.getDisplayName()))
            criteria.andLike("displayName", AmsUtils.like(param.getDisplayName()));
        Page<Object> page = PageHelper.startPage(param.getPage(), param.getLimit());
        List<AmsFacesetEntity> entities = amsFacesetDao.selectByExample(example);
        return new PageUtils(entities,page.getTotal(),param.getLimit(),param.getPage());
    }

    /**
     * 同步
     */
    @Override
    public Res reload() {
        Response response;
        try {
            response = faceSetOperate.getFaceSets();
        } catch (Exception e) {
            response = new Response(null,404);
            e.printStackTrace();
        }
        if (response.getStatus()!=200){
            String msg = new String(response.getContent());
            return Res.error(response.getStatus(),msg);
        }
        List<FaceSetEntity> entities = AmsUtils.parseFaceSet(response);
        //删除脸集所有数据
        amsFacesetDao.deleteAll();
        List<AmsFacesetEntity> entityList = new ArrayList<>();
        for (FaceSetEntity entity :entities){
            AmsFacesetEntity e = new AmsFacesetEntity();
            e.setFacesetToken(entity.getFaceset_token());
            e.setDisplayName(entity.getDisplay_name());
            e.setTags(entity.getTags());
            if (StringUtils.isNotBlank(entity.getOuter_id())&& NumberUtil.isNumber(entity.getOuter_id())){//课程ID不为空
                SysDeptEntity deptEntity = sysDeptDao.selectByPrimaryKey(Long.parseLong(entity.getOuter_id()));
                if (deptEntity != null) {
                    e.setDeptId(deptEntity.getDeptId());
                    e.setDeptName(deptEntity.getName());
                }
            }
            entityList.add(e);
        }
        if (entityList.size()>0)
        amsFacesetDao.insertList(entityList);
        return Res.ok();
    }

    /**
     * 保存
     */
    @Override
    public Res save(AmsFacesetEntity facesetEntity) {
        Response response;
        String tags ="";
        if (StringUtils.isNotBlank(facesetEntity.getTags())){
            tags = facesetEntity.getTags();
        }
        //云端保存脸集
        try {
            response = faceSetOperate.createFaceSet(
                    facesetEntity.getDisplayName(),
                    facesetEntity.getDeptId().toString(),
                    tags, "b822dc9f191406c2109f4988b1c723f4",null , 1);
        } catch (Exception e) {
            response = new Response(null,404);
            e.printStackTrace();
        }
        if (response.getStatus()!=200){
            String msg = new String(response.getContent());
            return Res.error(response.getStatus(),msg);
        }
        String json = new String(response.getContent());
        if (!json.contains("faceset_token")){
            return Res.error(response.getStatus(),"faceset_token获取失败");
        }
        String faceset_token = AmsUtils.parseResponse(response, "faceset_token");
        facesetEntity.setFacesetToken(faceset_token);
        //本地数据库保存脸集
        int insert = amsFacesetDao.insert(facesetEntity);
        return Res.ok();
    }

    /**
     * 批量删除
     */
    @Override
    public Res deleteBatch(List<Long> ids) {
        Response response;
        List<Response> responses = new ArrayList<>();
        for (Long id : ids){
            AmsFacesetEntity entity = amsFacesetDao.selectByPrimaryKey(id);
            String facesetToken = entity.getFacesetToken();
            try {
                response = faceSetOperate.deleteFaceSetByToken(facesetToken, 0);
            } catch (Exception e) {
                response = new Response(null,404);
                e.printStackTrace();
            }
            responses.add(response);
        }
        for (Response r : responses){
            if (r.getStatus()!=200){
                String msg = new String(r.getContent());
                return Res.error(r.getStatus(),"删除出现错误，请同步数据后再次删除");
            }
        }
        amsFacesetDao.deleteBatch(ids);
        //删除云端数据

        return Res.ok();
    }

    /**
     * 根据脸集ID查询对应的学生
     */
    @Override
    public PageUtils getStuListByFaceSetId(SearchFacesetParam facesetParam) {
        Long facesetId = facesetParam.getFacesetId();
        AmsFacesetEntity entity = amsFacesetDao.selectByPrimaryKey(facesetId);
        if (entity == null || entity.getDeptId() == null)
            return new PageUtils(Collections.emptyList(),0,facesetParam.getLimit(),facesetParam.getPage());
        Example example = new Example(SysUserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deptId",entity.getDeptId());
        criteria.andEqualTo("userType", Constant.UserType.STUDENT.getValue());
        if (StringUtils.isNotBlank(facesetParam.getUserName()))
            criteria.andLike("userName",AmsUtils.like(facesetParam.getUserName()));
        if (StringUtils.isNotBlank(facesetParam.getLoginNum()))
            criteria.andLike("loginNum",AmsUtils.like(facesetParam.getLoginNum()));
        Page<Object> page = PageHelper.startPage(facesetParam.getPage(), facesetParam.getLimit());
        List<SysUserEntity> entities = sysUserDao.selectByExample(example);
        if (entities == null || entities.size() < 1){
            return new PageUtils(Collections.emptyList(),0,facesetParam.getLimit(),facesetParam.getPage());
        }
        return new PageUtils(entities,page.getTotal(),facesetParam.getLimit(),facesetParam.getPage());
    }

    @Override
    public AmsFacesetEntity selectByPrimaryKey(Long facesetId) {
        AmsFacesetEntity entity = amsFacesetDao.selectByPrimaryKey(facesetId);
        return entity;
    }

    @Override
    public Res update(AmsFacesetEntity facesetEntity) {
        //TODO
        Response response;
        String tags ="";
        if (StringUtils.isNotBlank(facesetEntity.getTags())){
            tags = facesetEntity.getTags();
        }
        try {
            response = faceSetOperate.updateFaceSetByFaceSetToken(facesetEntity.getFacesetToken(),
                    facesetEntity.getDeptId().toString(),
                    facesetEntity.getDisplayName(),
                    "",
                    facesetEntity.getTags());
        } catch (Exception e) {
            response = new Response(null,404);
            e.printStackTrace();
        }
        if (response.getStatus()!=200){
            String msg = new String(response.getContent());
            return Res.error(response.getStatus(),msg);
        }
        String json = new String(response.getContent());
        if (!json.contains("faceset_token")){
            return Res.error(response.getStatus(),"faceset_token获取失败");
        }
        String faceset_token = AmsUtils.parseResponse(response, "faceset_token");
        facesetEntity.setFacesetToken(faceset_token);
        //本地数据库保存脸集
        int insert = amsFacesetDao.updateByPrimaryKey(facesetEntity);
        return Res.ok();
    }
}
