package com.tianxing.system.service.impl;

import com.tianxing.common.utils.Constants;
import com.tianxing.system.entity.SysUser;
import com.tianxing.system.mapper.SysUserMapper;
import com.tianxing.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 许仕昊
 * @since 2019-12-15
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
    * 校验用户名是否唯一
    * @param userName
    * @return
    */
    @Override
    public String checkSysUserName(String userName) {
        int count = sysUserMapper.checkSysUserName(userName);
        if (count > 0){
            return Constants.USER_NAME_NOT_UNIQUE;
        }
        return Constants.USER_NAME_UNIQUE;
    }

}
