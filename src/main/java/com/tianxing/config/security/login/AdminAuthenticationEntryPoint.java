package com.tianxing.config.security.login;

import com.tianxing.common.enumeration.ApiResult;
import com.tianxing.common.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证权限入口,未登录的情况下都会拦截至此
 *
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-12-15 23:12
 **/

@Slf4j
@Component
public class AdminAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (e != null) {
            ResponseUtils.out(httpServletResponse, ApiResult.expired(e.getMessage()));
        } else {
            ResponseUtils.out(httpServletResponse,  ApiResult.expired(e.getMessage()));
        }
    }
}
