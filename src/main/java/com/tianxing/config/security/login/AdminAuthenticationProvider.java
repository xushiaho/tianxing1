package com.tianxing.config.security.login;

import com.tianxing.common.utils.PasswordUtils;
import com.tianxing.config.Constants;
import com.tianxing.config.security.entity.SecurityUser;
import com.tianxing.config.security.service.impl.UserDetailsServiceImpl;
import com.tianxing.system.entity.SysUser;
import com.tianxing.system.mapper.SysUserMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Date;

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

        //获取前端表单中输入返回后的密码
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(username);

        boolean validPassword = PasswordUtils.isValidPassword(password, securityUser.getPassword(), securityUser.getCurrentUserInfo().getSalt());

        //验证密码
        if (!validPassword){
            throw new BadCredentialsException("密码错误!");
        }

        //更新登录令牌
        String token = PasswordUtils.encodePassword(String.valueOf(System.currentTimeMillis()), securityUser.getCurrentUserInfo().getSalt());

        //当前用户所拥有的的角色
        String roleCodes = securityUser.getRoleCodes();

        //生成jwt访问令牌
        String jwt = Jwts.builder()
                //用户角色
                .claim(Constants.ROLE_LOGIN, roleCodes)
                //用户名
                .setSubject(authentication.getName())
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                //加密算法和秘钥
                .signWith(SignatureAlgorithm.HS512, Constants.SALT)
                .compact();

        SysUser sysUser = sysUserMapper.selectById(securityUser.getCurrentUserInfo().getUserId());
        sysUser.setToken(jwt);
        sysUserMapper.updateById(sysUser);
        securityUser.getCurrentUserInfo().setToken(jwt);
        return new UsernamePasswordAuthenticationToken(securityUser,password,securityUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
