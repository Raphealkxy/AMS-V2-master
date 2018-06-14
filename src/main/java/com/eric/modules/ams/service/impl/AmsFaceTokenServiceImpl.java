package com.eric.modules.ams.service.impl;

import com.eric.common.result.ListQueryResult;
import com.eric.common.result.OperateResult;
import com.eric.common.utils.AipFaceClient;
import com.eric.common.utils.AmsUtils;
import com.eric.modules.ams.dao.AmsAttlistDao;
import com.eric.modules.ams.entity.AmsAttlistEntity;
import com.eric.modules.ams.service.AmsFaceTokenService;
import com.eric.modules.sys.dao.SysUserDao;
import com.eric.modules.sys.entity.SysDeptEntity;
import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.service.SysDeptService;
import com.eric.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chen 2018/2/18
 */
@Service
public class AmsFaceTokenServiceImpl implements AmsFaceTokenService {
    @Autowired
    private AipFaceClient aipFaceClient;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private AmsAttlistDao amsAttlistDao;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 检测是否符合标准
     * @param bytes
     * @return
     */
    @Override
    public OperateResult checkStandards(byte[] bytes) {
        JSONObject res = aipFaceClient.detect(bytes);
        if (!res.toString().contains("result_num")) return new OperateResult("检测失败，请联系管理员");
        int result_num = res.getInt("result_num");
        if (result_num == 0) return new OperateResult("未检测到人脸,操作失败");
        if (result_num > 1) return new OperateResult("检测到多张人脸,操作失败");
        JSONArray result = res.getJSONArray("result");
        JSONObject jsonObject = result.getJSONObject(0);
        //TODO 人脸注册标准
        double face_probability = jsonObject.getDouble("face_probability");
        if (face_probability > 0.8) return new OperateResult(1,"");
        else return new OperateResult("人脸置信度低，请选择其他照片");
    }


    @Override
    public OperateResult addUser(byte[] bytes, Long userId) {
        SysUserEntity entity = sysUserDao.selectByPrimaryKey(userId);
        if (entity == null) return new OperateResult("用户数据有误");
        if (entity.getDeptId() == null) return new OperateResult("该学生无班级，请先绑定班级");
        SysDeptEntity deptEntity = sysDeptService.queryByDeptId(entity.getDeptId());
        if (deptEntity == null) return new OperateResult("该学生无班级，请先绑定班级");
        if (StringUtils.isBlank(deptEntity.getGroupName())) {
            String groupName = AmsUtils.getGroupName();
            deptEntity.setGroupName(groupName);
            sysDeptService.update(deptEntity);
        }
        JSONObject jsonObject = aipFaceClient.addUser(bytes, entity.getLoginNum(), entity.getUserName(), deptEntity.getGroupName());
        if (jsonObject.toString().contains("error_msg")){
            String error_msg = jsonObject.getString("error_msg");
            return new OperateResult(error_msg);
        }
        entity.setRegister(true);
        sysUserDao.updateByPrimaryKey(entity);
        return new OperateResult(1,"");
    }

    @Override
    public OperateResult updateUser(byte[] bytes, Long userId) {
        SysUserEntity entity = sysUserDao.selectByPrimaryKey(userId);
        if (entity == null) return new OperateResult("用户数据有误");
        if (entity.getDeptId() == null) return new OperateResult("该学生无班级，请先绑定班级");
        SysDeptEntity deptEntity = sysDeptService.queryByDeptId(entity.getDeptId());
        if (deptEntity == null) return new OperateResult("该学生无班级，请先绑定班级");
        if (StringUtils.isBlank(deptEntity.getGroupName())) return new OperateResult("分组信息有误，请先注册人脸");
        JSONObject jsonObject = aipFaceClient.updateUser(bytes, entity.getLoginNum(), entity.getUserName(), deptEntity.getGroupName());
        if (jsonObject.toString().contains("error_msg")){
            String error_msg = jsonObject.getString("error_msg");
            return new OperateResult(error_msg);
        }
        return new OperateResult(1,"");
    }

    @Override
    public OperateResult delete(Long userId) {
        SysUserEntity entity = sysUserDao.selectByPrimaryKey(userId);
        if (entity == null) return new OperateResult("用户数据有误");
        if (entity.getDeptId() == null) return new OperateResult("该学生无班级，请先绑定班级");
        SysDeptEntity deptEntity = sysDeptService.queryByDeptId(entity.getDeptId());
        if (deptEntity == null) return new OperateResult("该学生无班级，请先绑定班级");
        if (StringUtils.isBlank(deptEntity.getGroupName())) return new OperateResult("分组信息有误，请先注册人脸");
        JSONObject jsonObject =aipFaceClient.deleteUser(entity.getLoginNum(),deptEntity.getGroupName());
        if (jsonObject.toString().contains("error_msg")){
            String error_msg = jsonObject.getString("error_msg");
            return new OperateResult(error_msg);
        }
        entity.setRegister(false);
        sysUserDao.updateByPrimaryKey(entity);
        return new OperateResult(1,"");
    }

    @Override
    public ListQueryResult<String> multiIdentify(byte[] bytes, Long attId) {
        AmsAttlistEntity amsAttlistEntity = amsAttlistDao.selectByPrimaryKey(attId);
        if (amsAttlistEntity == null) return new ListQueryResult<>("考勤单参数有误");
        String groupId = sysDeptService.getDeptGroupNameByAttId(attId);
        if (StringUtils.isBlank(groupId)) return new ListQueryResult<>("未查询到选课学生");
        JSONObject jsonObject = aipFaceClient.multiIdentify(bytes, groupId);
        if (jsonObject.toString().contains("error_msg")){
            String error_msg = jsonObject.getString("error_msg");
            return new ListQueryResult<>(error_msg);
        }
        if (!jsonObject.toString().contains("result")) return new ListQueryResult<>("识别失败");
        JSONArray result = jsonObject.getJSONArray("result");
        List<String> entities = new ArrayList<>();
        if (result == null) return new ListQueryResult<>(entities);
        for (int i = 0 ;i<result.length();i++){
            JSONObject resultObject = result.getJSONObject(i);
            if (!resultObject.toString().contains("uid"))
                continue;
            String uid = resultObject.getString("uid");
            SysUserEntity sysUserEntity = sysUserService.queryByLoginNum(uid);
            if (sysUserEntity == null)
                continue;
            entities.add(sysUserEntity.getLoginNum());
        }
        return new ListQueryResult<>(entities);
    }
}
