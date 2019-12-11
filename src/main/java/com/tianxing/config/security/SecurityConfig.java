package com.tianxing.config.security;

import com.tianxing.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-12-11 11:44
 **/
@Configuration
@EnableWebSecurity
//拦截@PreAuthrize注解的配置.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ISysUserService sysUserService;


}
