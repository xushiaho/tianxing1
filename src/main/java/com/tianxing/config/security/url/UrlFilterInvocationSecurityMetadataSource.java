package com.tianxing.config.security.url;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tianxing.config.Constants;
import com.tianxing.config.MyProperties;
import com.tianxing.system.entity.SysMenu;
import com.tianxing.system.entity.SysRole;
import com.tianxing.system.entity.SysRoleMenu;
import com.tianxing.system.mapper.SysMenuMapper;
import com.tianxing.system.mapper.SysRoleMapper;
import com.tianxing.system.mapper.SysRoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>  获取访问该url所需要的用户角色权限信息 </p>
 *
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-12-18 00:10
 **/

@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private MyProperties myProperties;

    /**
     * 返回该URL所需的用户权限信息
     *
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // 获取当前请求url
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        // TODO 忽略url请放在此处进行过滤放行
        for (String ignoreUrl : myProperties.getAuth().getIgnoreUrls()) {
            if (ignoreUrl.equals(requestUrl)){
                return null;
            }
        }

        if (requestUrl.contains("/login")){
            return null;
        }

        // 数据库中所有url
        List<SysMenu> sysMenuList = sysMenuMapper.selectList(null);
        for (SysMenu sysMenu : sysMenuList) {
            // 获取该url所对应的权限
            if (("/api"+sysMenu.getUrl()).equals(requestUrl)){
                List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("menu_id", sysMenu.getMenuId()));
                List<String> sysRoles = new LinkedList<>();
                if (!CollectionUtils.isEmpty(sysRoleMenus)){
                    sysRoleMenus.forEach( e -> {
                        Long roleId = e.getRoleId();
                        SysRole sysRole = sysRoleMapper.selectById(roleId);
                        sysRoles.add(sysRole.getRoleKey());
                    });
                }
                // 保存该url对应角色权限信息
                return SecurityConfig.createList(sysRoles.toArray(new String[sysRoles.size()]));
            }
        }
        // 如果数据中没有找到相应url资源则为非法访问，要求用户登录再进行操作
        return SecurityConfig.createList(Constants.ROLE_LOGIN);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
