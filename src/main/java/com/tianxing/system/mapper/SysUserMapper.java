package com.tianxing.system.mapper;

import com.tianxing.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 许仕昊
 * @since 2019-12-15
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

     /**
     * 校验用户名是否唯一
     * @param userName
     * @return
     */
     int checkSysUserName(String userName);
 }
