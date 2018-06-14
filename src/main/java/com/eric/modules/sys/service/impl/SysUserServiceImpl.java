package com.eric.modules.sys.service.impl;

import com.eric.common.utils.AmsUtils;
import com.eric.common.utils.Constant;
import com.eric.common.utils.PageUtils;
import com.eric.modules.sys.dao.SysDeptDao;
import com.eric.modules.sys.dao.SysUserDao;
import com.eric.modules.sys.entity.SysDeptEntity;
import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.param.SearchSysUserParam;
import com.eric.modules.sys.service.SysUserRoleService;
import com.eric.modules.sys.service.SysUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author Chen 2018/1/10
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysDeptDao sysDeptDao;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public PageUtils queryList(SearchSysUserParam param) {
        Page<Object> page = PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = new Example(SysUserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userType",param.getUserType());
        if (StringUtils.isNotBlank(param.getUserName())){
            criteria.andLike("userName","%"+param.getUserName()+"%");
        }
        if (StringUtils.isNotBlank(param.getSidx())){
            example.setOrderByClause(param.getSidx()+" "+param.getOrder());
        }
        if (StringUtils.isNotBlank(param.getLoginNum())){
            criteria.andLike("loginNum","%"+param.getLoginNum()+"%");
        }
        List<SysUserEntity> entities = sysUserDao.selectByExample(example);
        for (SysUserEntity entity :entities){
            if (entity.getDeptId() != null){
                SysDeptEntity sysDeptEntity = sysDeptDao.selectByPrimaryKey(entity.getDeptId());
                entity.setDeptName(sysDeptEntity.getName());
            }
        }
        return new PageUtils(entities, page.getTotal(), param.getLimit(), param.getPage());
    }

    @Override
    public SysUserEntity queryByLoginNum(String loginNum) {
        Example example = new Example(SysUserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("loginNum",loginNum);
        List<SysUserEntity> entities = sysUserDao.selectByExample(example);
        if(entities !=null && entities.size()>0){
            return entities.get(0);
        }
        return null;
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return sysUserDao.queryAllMenuId(userId);
    }

    @Override
    public SysUserEntity queryByUserId(Long userId) {
        return sysUserDao.selectByPrimaryKey(userId);
    }

    @Override
    @Transactional
    public void save(SysUserEntity user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        sysUserDao.insert(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void update(SysUserEntity user) {
        if(StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else{
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        sysUserDao.updateByPrimaryKeySelective(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] userId) {
        sysUserDao.deleteBatch(userId);
    }

    @Override
    public int updatePassword(Long userId, String password, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("password", password);
        map.put("newPassword", newPassword);
        return sysUserDao.updatePassword(map);
    }

    /**
     * 根据部门ID查询列表
     */
    @Override
    public PageUtils queryListByDeptId(SearchSysUserParam userParam) {
        if (userParam.getDeptId() == null){
            return new PageUtils(Collections.emptyList(),0,userParam.getLimit(),userParam.getPage());
        }
        Example example = new Example(SysUserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deptId",userParam.getDeptId());
        criteria.andEqualTo("userType", userParam.getUserType());
        if (StringUtils.isNotBlank(userParam.getUserName()))
            criteria.andLike("userName", AmsUtils.like(userParam.getUserName()));
        if (StringUtils.isNotBlank(userParam.getLoginNum()))
            criteria.andLike("loginNum",AmsUtils.like(userParam.getLoginNum()));
        Page<Object> page = PageHelper.startPage(userParam.getPage(), userParam.getLimit());
        List<SysUserEntity> entities = sysUserDao.selectByExample(example);
        if (entities == null || entities.size() < 1){
            return new PageUtils(Collections.emptyList(),0,userParam.getLimit(),userParam.getPage());
        }
        return new PageUtils(entities,page.getTotal(),userParam.getLimit(),userParam.getPage());
    }
}
