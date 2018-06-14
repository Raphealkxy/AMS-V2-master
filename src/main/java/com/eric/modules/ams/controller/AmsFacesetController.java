package com.eric.modules.ams.controller;

import com.eric.common.utils.PageUtils;
import com.eric.common.utils.Res;
import com.eric.face.entity.FaceSetEntity;
import com.eric.modules.ams.dao.AmsFacesetDao;
import com.eric.modules.ams.entity.AmsFacesetEntity;
import com.eric.modules.ams.param.SearchFacesetParam;
import com.eric.modules.ams.service.AmsFacesetService;
import com.eric.modules.sys.entity.SysDeptEntity;
import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.service.SysDeptService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;
import java.util.Arrays;
import java.util.List;

/**
 * @author Chen 2018/1/27
 */
@RestController
@RequestMapping("/ams/faceset")
public class AmsFacesetController {
    @Autowired
    private AmsFacesetService amsFacesetService;
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:faceset:list")
    public Res list(SearchFacesetParam param){
        PageUtils pageUtils =  amsFacesetService.queryList(param);
        return Res.ok().put("page",pageUtils);
    }

    /**
     * 列表
     */
    @RequestMapping("/reload")
    @RequiresPermissions("sys:faceset:reload")
    public Res reload(){
        return amsFacesetService.reload();
    }

    /**
     * 保存FaceSet
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:faceset:save")
    public Res save(@RequestBody AmsFacesetEntity facesetEntity){
        if (StringUtils.isBlank(facesetEntity.getDisplayName())){
            return Res.error("脸集名称不能为空");
        }
        if (facesetEntity.getDeptId()==null){
            return Res.error("请选择班级");
        }
        SysDeptEntity deptEntity = sysDeptService.queryByDeptId(facesetEntity.getDeptId());
        if (deptEntity==null){
            return Res.error("班级选择有误");
        }
        if (deptEntity.getDeptType()!=1){
            return Res.error("请选择班级，不要选择部门");
        }
        facesetEntity.setDeptName(deptEntity.getName());
        if (facesetEntity.getFacesetId() == null){
            return amsFacesetService.save(facesetEntity);
        }else {
            return amsFacesetService.update(facesetEntity);
        }
    }

    /**
     * 删除Faceset
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:faceset:delete")
    public Res delete(@RequestBody Long[] faceSetIds){
        if (faceSetIds.length<0) return Res.error("请选择要删除的参数");
        List<Long> ids = Arrays.asList(faceSetIds);
        return amsFacesetService.deleteBatch(ids);
    }

    /**
     * 根据faceSetId查询用户列表
     */
    @RequestMapping("/stulist")
    public Res stuList(SearchFacesetParam facesetParam){
        if (facesetParam.getFacesetId() == null){
            return Res.error("参数有误");
        }
        PageUtils pageUtils = amsFacesetService.getStuListByFaceSetId(facesetParam);
        return Res.ok().put("page",pageUtils);
    }

    @RequestMapping("/info/{facesetId}")
    public Res info(@PathVariable("facesetId") Long facesetId){
        if (facesetId == null){
            return Res.error("参数有误");
        }
        AmsFacesetEntity faceSetEntity = amsFacesetService.selectByPrimaryKey(facesetId);
        return Res.ok().put("faceset",faceSetEntity);
    }

}
