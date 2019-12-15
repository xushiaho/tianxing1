package com.tianxing.system.service;

import com.tianxing.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 许仕昊
 * @since 2019-12-15
 */
public interface ISysUserService extends IService<SysUser> {

    /**
    * 校验用户名是否唯一
    * @param userName
    * @return
    */
    String checkSysUserName(String userName);
}
