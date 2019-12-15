package com.tianxing.config.security.login;

import com.tianxing.config.security.service.impl.UserDetailsServiceImpl;
import com.tianxing.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

/**
 * <p> 自定义认证处理 </p>
 *
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-12-15 23:30
 **/

@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
