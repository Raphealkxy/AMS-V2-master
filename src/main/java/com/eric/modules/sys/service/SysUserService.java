package com.eric.modules.sys.service;

import com.eric.common.utils.PageUtils;
import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.param.SearchSysUserParam;

import java.util.List;

/**
 * @author Chen 2018/1/10
 */
public interface SysUserService {

    /**
     * 查询用户列表
     */
    PageUtils queryList(SearchSysUserParam param);

    /**
     * 根据学工号查询用户
     */
    SysUserEntity queryByLoginNum(String loginNum);

    /**
     * 根据用户id，查询用户的菜单Id
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 根据ID查询改用户信息
     */
    SysUserEntity queryByUserId(Long userId);

    /**
     * 新建管理员
     */
    void save(SysUserEntity user);

    /**
     * 更新
     */
    void update(SysUserEntity user);

    void deleteBatch(Long[] userIds);

    int updatePassword(Long userId, String password, String newPassword);

    /**
     * 根据部门ID查询列表
     */
    PageUtils queryListByDeptId(SearchSysUserParam param);
}
