package com.tianxing.system.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianxing.common.enumeration.ApiResult;
import com.tianxing.common.utils.Constants;
import com.tianxing.common.utils.DateUtils;
import com.tianxing.system.entity.SysMenu;
import com.tianxing.system.service.ISysMenuService;
import org.springframework.web.bind.annotation.RestController;
import com.tianxing.common.controller.BaseController;
import java.util.Arrays;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author 许仕昊
 * @since 2019-12-15
 */
@RestController
@RequestMapping("/system/sysMenu/")
public class SysMenuController extends BaseController {

    @Autowired
    private ISysMenuService iSysMenuService;

    /**
    * 查询菜单列表
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(){
        Page< SysMenu> page = new Page< SysMenu>();
        return new ApiResult(iSysMenuService.page(page, Wrappers.emptyWrapper()));
    }

    /**
    * 新增菜单信息
    *
    * @param sysMenu
    * @return
    */
    @RequestMapping("add")
    @ResponseBody
    public ApiResult add ( SysMenu  sysMenu){

        //添加时间
        sysMenu.setCreateTime(DateUtils.getNowDate());

        //校验菜单名是否唯一
        if (Constants.MENU_NAME_NOT_UNIQUE.equals(iSysMenuService.checkSysMenuName(sysMenu.getMenuName()))){
        return new ApiResult("新增菜单"+sysMenu.getMenuName()+"失败,菜单名已存在");
        }

        //添加菜单信息
        iSysMenuService.save(sysMenu);
        return ApiResult.ok("添加成功!");
    }

    /**
    * 修改菜单信息
    * @param sysMenu
    * @return
    */
    @RequestMapping("update" )
    @ResponseBody
    public ApiResult update(SysMenu  sysMenu){

        //修改时间
        sysMenu.setUpdateTime(DateUtils.getNowDate());

        //校验菜单名是否唯一
        if (Constants.MENU_NAME_NOT_UNIQUE.equals(iSysMenuService.checkSysMenuName(sysMenu.getMenuName()))){
        return new ApiResult("修改菜单"+sysMenu.getMenuName()+"失败,菜单名已存在");
        }

        //修改菜单信息
        iSysMenuService.updateById(sysMenu);
        return ApiResult.ok("修改成功!");
    }

    /**
    * 批量删除菜单信息
    * @param sysMenuIds
    * @return
    */
    @RequestMapping("deletes/{sysMenuId}")
    @ResponseBody
    public ApiResult deletes(@PathVariable("sysMenuId") String[] sysMenuIds){
        iSysMenuService.removeByIds(Arrays.asList(sysMenuIds));
        return new ApiResult("删除成功");
    }


}
