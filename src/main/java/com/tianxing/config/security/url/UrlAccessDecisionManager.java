package com.tianxing.config.security.url;

import com.tianxing.config.Constants;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * <p> 对访问URL进行权限认证处理 </p>
 *
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-12-17 23:52
 **/

@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {

    /**
     *
     * @param authentication:当前登录用户的角色信息
     * @param o: 请求url信息
     * @param collection
     * @throws AccessDeniedException: `UrlFilterInvocationSecurityMetadataSource`中的getAttributes方法传来的，表示当前请求需要的角色（可能有多个）
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {

        //遍历角色
        for (ConfigAttribute ca : collection) {
            //当前URL请求需要的权限
            String needRole = ca.getAttribute();
            if (Constants.ROLE_LOGIN.equals(needRole)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("未登录");
                } else {
                    throw new AccessDeniedException("未授权该url！");
                }
            }

            //当前用户所具有的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("请联系管理员分配角色");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
