package com.eric.modules.sys.controller;

import com.eric.common.exception.AMSException;
import com.eric.common.utils.BaseController;
import com.eric.common.utils.Res;
import com.eric.modules.sys.entity.SysMenuEntity;
import com.eric.modules.sys.service.ShiroService;
import com.eric.common.utils.Constant.MenuType;
import com.eric.modules.sys.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 * @author Chen 2018/1/12
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private ShiroService shiroService;

    /**
     * 获取导航菜单
     */
    @RequestMapping("/nav")
    public Res nav(){
        //菜单
        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
        //权限
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        return Res.ok().put("menuList", menuList).put("permissions", permissions);
    }
    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenuEntity> list(){
        List<SysMenuEntity> menuList = sysMenuService.queryList();

        return menuList;
    }
    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public Res select(){
        //查询列表数据
        List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return Res.ok().put("menuList", menuList);
    }

    /**
     * 菜单信息
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public Res info(@PathVariable("menuId") Long menuId){
        SysMenuEntity menu = sysMenuService.selectByMenuId(menuId);
        return Res.ok().put("menu", menu);
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public Res save(@RequestBody SysMenuEntity menu){
        sysMenuService.save(menu);

        return Res.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public Res update(@RequestBody SysMenuEntity menu){
        //数据校验
        verifyForm(menu);
        sysMenuService.update(menu);
        return Res.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public Res delete(long menuId){
        if(menuId <= 31){
            return Res.error("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
        if(menuList.size() > 0){
            return Res.error("请先删除子菜单或按钮");
        }

        sysMenuService.deleteBatch(new Long[]{menuId});

        return Res.ok();
    }


    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenuEntity menu){
        if(StringUtils.isBlank(menu.getName())){
            throw new AMSException("菜单名称不能为空");
        }

        if(menu.getParentId() == null){
            throw new AMSException("上级菜单不能为空");
        }

        //菜单
        if(menu.getType() == MenuType.MENU.getValue()){
            if(StringUtils.isBlank(menu.getUrl())){
                throw new AMSException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = MenuType.CATALOG.getValue();
        if(menu.getParentId() != 0){
            SysMenuEntity parentMenu = sysMenuService.queryByMenuId(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if(menu.getType() == MenuType.CATALOG.getValue() ||
                menu.getType() == MenuType.MENU.getValue()){
            if(parentType != MenuType.CATALOG.getValue()){
                throw new AMSException("上级菜单只能为目录类型");
            }
            return ;
        }

        //按钮
        if(menu.getType() == MenuType.BUTTON.getValue()){
            if(parentType != MenuType.MENU.getValue()){
                throw new AMSException("上级菜单只能为菜单类型");
            }
            return ;
        }
    }
}
