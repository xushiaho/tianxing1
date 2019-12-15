package com.tianxing.system.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianxing.common.enumeration.ApiResult;
import com.tianxing.common.utils.Constants;
import com.tianxing.common.utils.DateUtils;
import com.tianxing.system.entity.SysUser;
import com.tianxing.system.service.ISysUserService;
import org.springframework.web.bind.annotation.RestController;
import com.tianxing.common.controller.BaseController;
import java.util.Arrays;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 许仕昊
 * @since 2019-12-15
 */
@RestController
@RequestMapping("/system/sysUser/")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService iSysUserService;

    /**
    * 查询用户列表
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(){
        Page< SysUser> page = new Page< SysUser>();
        return new ApiResult(iSysUserService.page(page, Wrappers.emptyWrapper()));
    }

    /**
    * 新增用户信息
    *
    * @param sysUser
    * @return
    */
    @RequestMapping("add")
    @ResponseBody
    public ApiResult add ( SysUser  sysUser){

        //添加时间
        sysUser.setCreateTime(DateUtils.getNowDate());

        //校验用户名是否唯一
        if (Constants.USER_NAME_NOT_UNIQUE.equals(iSysUserService.checkSysUserName(sysUser.getUsername()))){
        return new ApiResult("新增用户"+sysUser.getUsername()+"失败,用户名已存在");
        }

        //添加用户信息
        iSysUserService.save(sysUser);
        return ApiResult.ok("添加成功!");
    }

    /**
    * 修改用户信息
    * @param sysUser
    * @return
    */
    @RequestMapping("update" )
    @ResponseBody
    public ApiResult update(SysUser  sysUser){

        //修改时间
        sysUser.setUpdateTime(DateUtils.getNowDate());

        //校验用户名是否唯一
        if (Constants.USER_NAME_NOT_UNIQUE.equals(iSysUserService.checkSysUserName(sysUser.getUsername()))){
        return new ApiResult("修改用户"+sysUser.getUsername()+"失败,用户名已存在");
        }

        //修改用户信息
        iSysUserService.updateById(sysUser);
        return ApiResult.ok("修改成功!");
    }

    /**
    * 批量删除用户信息
    * @param sysUserIds
    * @return
    */
    @RequestMapping("deletes/{sysUserId}")
    @ResponseBody
    public ApiResult deletes(@PathVariable("sysUserId") String[] sysUserIds){
        iSysUserService.removeByIds(Arrays.asList(sysUserIds));
        return new ApiResult("删除成功");
    }


}
