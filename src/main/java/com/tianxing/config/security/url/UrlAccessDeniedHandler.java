package com.tianxing.config.security.url;

import com.tianxing.common.enumeration.ApiResult;
import com.tianxing.common.enumeration.ResultCode;
import com.tianxing.common.utils.ResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p> 认证url权限 - 登录后访问接口无权限 - 自定义403无权限响应内容 </p>
 *
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-12-18 00:08
 **/

@Component
public class UrlAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResponseUtils.out(httpServletResponse, ApiResult.fail(ResultCode.UNAUTHORIZED.getCode(),e.getMessage()));
    }
}
