package com.tianxing.config.security;

import com.tianxing.config.MyProperties;
import com.tianxing.config.security.filter.AdminAuthenticationProcessingFilter;
import com.tianxing.config.security.filter.MyAuthenticationFilter;
import com.tianxing.config.security.login.AdminAuthenticationEntryPoint;
import com.tianxing.config.security.url.UrlAccessDecisionManager;
import com.tianxing.config.security.url.UrlAccessDeniedHandler;
import com.tianxing.config.security.url.UrlFilterInvocationSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
    private MyProperties myProperties;

    /**
     * 访问鉴权 - 认证token,签名...
     */
    private final MyAuthenticationFilter myAuthenticationFilter;

    /**
     * 访问鉴权异常处理
     */
    private final AdminAuthenticationEntryPoint adminAuthenticationEntryPoint;

    /**
     * 用户密码校验过滤器
     */
    private final AdminAuthenticationProcessingFilter adminAuthenticationProcessingFilter;

    /**
     * 获取访问url所需要的角色信息
     */
    private final UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    /**
     * 认证权限处理 - 将上面所获得角色权限与当前登录用户的角色做对比，如果包含其中一个角色即可正常访问
     */
    private final UrlAccessDecisionManager urlAccessDecisionManager;

    /**
     * 自定义访问无权限接口时403响应内容
     */
    private final UrlAccessDeniedHandler urlAccessDeniedHandler;

    public SecurityConfig(MyAuthenticationFilter myAuthenticationFilter, AdminAuthenticationEntryPoint adminAuthenticationEntryPoint, AdminAuthenticationProcessingFilter adminAuthenticationProcessingFilter, UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource, UrlAccessDecisionManager urlAccessDecisionManager, UrlAccessDeniedHandler urlAccessDeniedHandler) {
        this.myAuthenticationFilter = myAuthenticationFilter;
        this.adminAuthenticationEntryPoint = adminAuthenticationEntryPoint;
        this.adminAuthenticationProcessingFilter = adminAuthenticationProcessingFilter;
        this.urlFilterInvocationSecurityMetadataSource = urlFilterInvocationSecurityMetadataSource;
        this.urlAccessDecisionManager = urlAccessDecisionManager;
        this.urlAccessDeniedHandler = urlAccessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = http.antMatcher("/**").authorizeRequests();

        //禁用csrf 开启跨域
        http.csrf().disable().cors();

        //未登录异常
        http.exceptionHandling().authenticationEntryPoint(adminAuthenticationEntryPoint);

        //登录过后访问无权限接口时自定义403响应内容
        http.exceptionHandling().accessDeniedHandler(urlAccessDeniedHandler);

        /**
         * url权限认证处理
         */
        expressionInterceptUrlRegistry.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                object.setAccessDecisionManager(urlAccessDecisionManager);
                return object;
            }
        });

        //不创建会话 - 即通过前端传token到后台中验证是否存在访问权限
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 允许匿名的url - 可理解为放行接口 - 除配置文件忽略url以外，其它所有请求都需经过认证和授权
        //expressionInterceptUrlRegistry.antMatchers("/login","/system/sysUser/add").permitAll();
        expressionInterceptUrlRegistry.antMatchers("/system/sysUser/**").permitAll();
//        for (String url :  myProperties.getAuth().getIgnoreUrls()) {
//            expressionInterceptUrlRegistry.antMatchers(url).permitAll();
//        }

        expressionInterceptUrlRegistry.antMatchers(HttpMethod.OPTIONS,"/**").denyAll();

        // 自动登录 - cookie储存方式
        expressionInterceptUrlRegistry.and().rememberMe();
        // 其余所有请求都需要认证
        expressionInterceptUrlRegistry.anyRequest().authenticated();
        // 防止iframe 造成跨域
        expressionInterceptUrlRegistry.and().headers().frameOptions().disable();

        // 自定义过滤器在登录时认证用户名、密码
        http.addFilterAt(adminAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class).addFilterBefore(myAuthenticationFilter, BasicAuthenticationFilter.class);
    }

    /**
     * 忽略拦截url或静态资源文件夹
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET,
                "/favicon.ico",
                "/**/*.png",
                "/**/*.ttf",
                "/*.html",
                "/**/*.css",
                "/**/*.js");
    }
}
