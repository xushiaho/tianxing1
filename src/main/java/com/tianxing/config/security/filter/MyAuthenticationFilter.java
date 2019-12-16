package com.tianxing.config.security.filter;

import com.tianxing.common.utils.MultiReadHttpServletRequest;
import com.tianxing.common.utils.MultiReadHttpServletResponse;
import com.tianxing.config.Constants;
import com.tianxing.config.security.login.AdminAuthenticationEntryPoint;
import com.tianxing.config.security.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问鉴权 - 每次访问接口都会经过此
 *
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-12-16 11:39
 **/
@Slf4j
@Component
public class MyAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AdminAuthenticationEntryPoint adminAuthenticationEntryPoint;

    private final UserDetailsServiceImpl userDetailsService;

    public MyAuthenticationFilter(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ((request.getContentType() == null && request.getContentLength() > 0) || (request.getContentType() != null && !request.getContentType().contains(Constants.REQUEST_HEADERS_CONTENT_TYPE))){
            filterChain.doFilter(request,response);
            return;
        }

        MultiReadHttpServletRequest multiReadHttpServletRequest = new MultiReadHttpServletRequest(request);
        MultiReadHttpServletResponse multiReadHttpServletResponse = new MultiReadHttpServletResponse(response);

        StopWatch stopWatch = new StopWatch();
    }
}
