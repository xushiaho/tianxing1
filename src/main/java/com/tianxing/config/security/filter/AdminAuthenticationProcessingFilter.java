package com.tianxing.config.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.tianxing.common.utils.MultiReadHttpServletRequest;
import com.tianxing.config.Constants;
import com.tianxing.config.security.login.AdminAuthenticationFailureHandler;
import com.tianxing.config.security.login.AdminAuthenticationSuccessHandler;
import com.tianxing.config.security.login.CusAuthenticationManager;
import com.tianxing.system.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthInternalFrameUI;
import java.io.IOException;

/**
 * 自定义用户密码校验过滤器
 *
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-12-16 10:32
 **/
@Slf4j
@Component
public class AdminAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    /**
     *
     * @param authenticationManager:            认证管理器
     * @param adminAuthenticationSuccessHandler:认证成功处理
     * @param adminAuthenticationFailureHandler:认证失败处理
     */
    protected AdminAuthenticationProcessingFilter(CusAuthenticationManager authenticationManager, AdminAuthenticationSuccessHandler adminAuthenticationSuccessHandler, AdminAuthenticationFailureHandler adminAuthenticationFailureHandler) {
        super(new AntPathRequestMatcher("/login","POST"));
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(adminAuthenticationSuccessHandler);
        this.setAuthenticationFailureHandler(adminAuthenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (request.getContentType() == null || !request.getContentType().contains(Constants.REQUEST_HEADERS_CONTENT_TYPE)){
            throw new AuthenticationServiceException("请求头类型不支持："+request.getContentType());
        }
        UsernamePasswordAuthenticationToken authenticationToken;
        try {
            MultiReadHttpServletRequest multiReadHttpServletRequest = new MultiReadHttpServletRequest(request);
            SysUser sysUser = JSONObject.parseObject(multiReadHttpServletRequest.getBodyJsonStrByJson(multiReadHttpServletRequest), SysUser.class);
            authenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword(), null);
            authenticationToken.setDetails(authenticationDetailsSource.buildDetails(multiReadHttpServletRequest));
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}
