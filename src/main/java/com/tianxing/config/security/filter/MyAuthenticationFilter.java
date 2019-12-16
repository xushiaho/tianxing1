package com.tianxing.config.security.filter;

import com.tianxing.common.utils.MultiReadHttpServletRequest;
import com.tianxing.common.utils.MultiReadHttpServletResponse;
import com.tianxing.config.Constants;
import com.tianxing.config.security.entity.SecurityUser;
import com.tianxing.config.security.login.AdminAuthenticationEntryPoint;
import com.tianxing.config.security.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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

        try {
            stopWatch.start();

            //记录请求消息
            logRequestBody(multiReadHttpServletRequest);

            SecurityContext context = SecurityContextHolder.getContext();
            if (context.getAuthentication() != null && context.getAuthentication().isAuthenticated()){
                filterChain.doFilter(multiReadHttpServletRequest,multiReadHttpServletResponse);
                return;
            }

            String jwtToken = multiReadHttpServletRequest.getHeader(Constants.REQUEST_HEADER);
            log.debug("后台检查令牌:{}", jwtToken);
            if (StringUtils.isNoneBlank(jwtToken)){
                // 获取jwt中的信息
                Claims claims = Jwts.parser().setSigningKey(Constants.SALT).parseClaimsJws(jwtToken.replace("Bearer", "")).getBody();

                //检查token
                SecurityUser securityUser = userDetailsService.getUserByToken(jwtToken);
                if (securityUser == null || securityUser.getCurrentUserInfo() == null){
                    throw new BadCredentialsException("TOKEN已过期,请重新登录!");
                }
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
                //全局注入角色权限信息和登录用户基本信息
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            filterChain.doFilter(multiReadHttpServletRequest, multiReadHttpServletResponse);
        } catch (ExpiredJwtException e){
            //jwt令牌过期
            SecurityContextHolder.clearContext();
            this.adminAuthenticationEntryPoint.commence(multiReadHttpServletRequest,response,null);
        } catch (AuthenticationException e){
            SecurityContextHolder.clearContext();
            this.adminAuthenticationEntryPoint.commence(multiReadHttpServletRequest, response , e);
        } finally {
            stopWatch.stop();
            long totalTimeMillis = stopWatch.getTotalTimeMillis();
            logRequestBody(multiReadHttpServletRequest, multiReadHttpServletResponse,totalTimeMillis);
        }
    }

    private String logRequestBody(MultiReadHttpServletRequest multiReadHttpServletRequest) {
        MultiReadHttpServletRequest multiReadHttpServletRequest1 = multiReadHttpServletRequest;
        if (multiReadHttpServletRequest1 != null){
            try {
                String bodyJsonStrByJson = multiReadHttpServletRequest1.getBodyJsonStrByJson(multiReadHttpServletRequest);
                String url = multiReadHttpServletRequest1.getRequestURI().replace("//", "/");
                Constants.URL_MAPPING_MAP.put(url,url);
                log.info("`{}` 接收到的参数: {}", url, bodyJsonStrByJson);
                return bodyJsonStrByJson;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    private void logRequestBody(MultiReadHttpServletRequest multiReadHttpServletRequest, MultiReadHttpServletResponse multiReadHttpServletResponse, long totalTimeMillis) {
        MultiReadHttpServletResponse multiReadHttpServletResponse1 = multiReadHttpServletResponse;
        if (multiReadHttpServletResponse1 != null){
            byte[] body = multiReadHttpServletResponse1.getBody();
            if (body.length>0){
                String payload;
                try {
                    payload = new String(body, 0, body.length, multiReadHttpServletResponse1.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    payload = "[unknown]";
                }
                log.info("`{}`  耗时:{}ms  返回的参数: {}", Constants.URL_MAPPING_MAP.get(multiReadHttpServletRequest.getRequestURI()), totalTimeMillis, payload);
            }
        }
    }

}
