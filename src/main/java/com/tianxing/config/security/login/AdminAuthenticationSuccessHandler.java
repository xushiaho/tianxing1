package com.tianxing.config.security.login;

import com.tianxing.common.enumeration.ApiResult;
import com.tianxing.common.utils.ResponseUtils;
import com.tianxing.config.security.entity.SecurityUser;
import com.tianxing.system.entity.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理
 *
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-12-16 10:25
 **/

@Component
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SysUser sysUser = new SysUser();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        sysUser.setToken(securityUser.getCurrentUserInfo().getToken());
        ResponseUtils.out(response, ApiResult.ok("登陆成功!", sysUser));
    }
}
