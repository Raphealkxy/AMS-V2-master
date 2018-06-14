package com.eric.modules.sys.service.impl;

import com.eric.common.utils.Constant;
import com.eric.modules.sys.dao.SysMenuDao;
import com.eric.modules.sys.dao.SysUserDao;
import com.eric.modules.sys.dao.SysUserTokenDao;
import com.eric.modules.sys.entity.SysMenuEntity;
import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.entity.SysUserTokenEntity;
import com.eric.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * shiro服务实现
 * @author Chen 2018/1/10
 */
@Service
public class ShiroServiceImpl implements ShiroService{
    @Autowired
    private SysUserTokenDao sysUserTokenDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysMenuDao sysMenuDao;

    @Override
    public Set<String> getUserPermissions(Long userId) {
        List<String> permsList;
        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<SysMenuEntity> menuList = sysMenuDao.selectAll();
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity queryByToken(String accessToken) {
        Example example = new Example(SysUserTokenEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("token",accessToken);
        List<SysUserTokenEntity> entities = sysUserTokenDao.selectByExample(example);
        if(entities!=null && entities.size()>0)
            return entities.get(0);
        return null;
    }

    @Override
    public SysUserEntity queryUser(Long userId) {
        return sysUserDao.selectByPrimaryKey(userId);
    }
}
