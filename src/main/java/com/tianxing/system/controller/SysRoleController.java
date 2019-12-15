package com.tianxing.system.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianxing.common.enumeration.ApiResult;
import com.tianxing.common.utils.Constants;
import com.tianxing.common.utils.DateUtils;
import com.tianxing.system.entity.SysRole;
import com.tianxing.system.service.ISysRoleService;
import org.springframework.web.bind.annotation.RestController;
import com.tianxing.common.controller.BaseController;
import java.util.Arrays;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 许仕昊
 * @since 2019-12-15
 */
@RestController
@RequestMapping("/system/sysRole/")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService iSysRoleService;

    /**
    * 查询角色列表
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(){
        Page< SysRole> page = new Page< SysRole>();
        return new ApiResult(iSysRoleService.page(page, Wrappers.emptyWrapper()));
    }

    /**
    * 新增角色信息
    *
    * @param sysRole
    * @return
    */
    @RequestMapping("add")
    @ResponseBody
    public ApiResult add ( SysRole  sysRole){

        //添加时间
        sysRole.setCreateTime(DateUtils.getNowDate());

        //校验角色名是否唯一
        if (Constants.ROLE_NAME_NOT_UNIQUE.equals(iSysRoleService.checkSysRoleName(sysRole.getRoleName()))){
        return new ApiResult("新增角色"+sysRole.getRoleName()+"失败,角色名已存在");
        }

        //添加角色信息
        iSysRoleService.save(sysRole);
        return ApiResult.ok("添加成功!");
    }

    /**
    * 修改角色信息
    * @param sysRole
    * @return
    */
    @RequestMapping("update" )
    @ResponseBody
    public ApiResult update(SysRole  sysRole){

        //修改时间
        sysRole.setUpdateTime(DateUtils.getNowDate());

        //校验角色名是否唯一
        if (Constants.ROLE_NAME_NOT_UNIQUE.equals(iSysRoleService.checkSysRoleName(sysRole.getRoleName()))){
        return new ApiResult("修改角色"+sysRole.getRoleName()+"失败,角色名已存在");
        }

        //修改角色信息
        iSysRoleService.updateById(sysRole);
        return ApiResult.ok("修改成功!");
    }

    /**
    * 批量删除角色信息
    * @param sysRoleIds
    * @return
    */
    @RequestMapping("deletes/{sysRoleId}")
    @ResponseBody
    public ApiResult deletes(@PathVariable("sysRoleId") String[] sysRoleIds){
        iSysRoleService.removeByIds(Arrays.asList(sysRoleIds));
        return new ApiResult("删除成功");
    }


}
