package com.tianxing.system.service.impl;

import com.tianxing.system.entity.SysUser;
import com.tianxing.system.mapper.SysUserMapper;
import com.tianxing.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 许仕昊
 * @since 2019-12-02
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
