package com.eric.modules.sys.service.impl;

import com.eric.common.utils.Constant;
import com.eric.common.utils.Constant.MenuType;
import com.eric.modules.sys.dao.SysMenuDao;
import com.eric.modules.sys.entity.SysMenuEntity;
import com.eric.modules.sys.service.SysMenuService;
import com.eric.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chen 2018/1/12
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据父菜单，查询子菜单
     */
    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
        //根据父ID查询所有菜单
        List<SysMenuEntity> menuList = queryListParentId(parentId);
        //如果要查询的子菜单为null，返回传入父菜单Id的所有子菜单
        if(menuIdList == null){
            return menuList;
        }
        //如果要给定指定自菜单ID
        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for(SysMenuEntity menu : menuList){
            //遍历该父菜单的所有子菜单
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    /**
     * 获取该用户的菜单
     */
    @Override
    public List<SysMenuEntity> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            return getAllMenuList(null);
        }
        //用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId) {
        return sysMenuDao.queryListParentId(parentId);
    }

    /**
     * 获取所有菜单
     */
    @Override
    public List<SysMenuEntity> queryList() {
        return sysMenuDao.queryList();
    }

    /**
     * 获取不包含按钮的菜单列表
     */
    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return sysMenuDao.queryNotButtonList();
    }

    /**
     * 根据ID查询
     */
    @Override
    public SysMenuEntity selectByMenuId(Long menuId) {
        return sysMenuDao.selectByPrimaryKey(menuId);
    }

    /**
     * 保存菜单
     */
    @Override
    public void save(SysMenuEntity menu) {
        sysMenuDao.insert(menu);
    }

    @Override
    public SysMenuEntity queryByMenuId(Long parentId) {
        return sysMenuDao.selectByPrimaryKey(parentId);
    }

    @Override
    public void update(SysMenuEntity menu) {
        sysMenuDao.updateByPrimaryKeySelective(menu);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] menuIds) {
        sysMenuDao.deleteBatch(menuIds);
    }

    /**
     * 获取所有菜单
     */
    private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);
        return menuList;
    }

    /**
     *
     * @param menuList 所有根菜单
     * @param menuIdList
     * @return
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

        for(SysMenuEntity entity : menuList){
            if(entity.getType() == MenuType.CATALOG.getValue()){//目录
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }
}
