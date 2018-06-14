package com.eric.modules.sys.service;

import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * Shiro服务类
 * @author Chen 2018/1/10
 */
public interface ShiroService {

    /**
     * 获取用户权限集
     */
    Set<String> getUserPermissions(Long userId);

    /**
     * 根据Token查询用户
     */
    SysUserTokenEntity queryByToken(String accessToken);

    /**
     * 查询用户
     */
    SysUserEntity queryUser(Long userId);
}
