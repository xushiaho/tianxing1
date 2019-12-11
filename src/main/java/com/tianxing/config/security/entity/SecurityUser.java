package com.tianxing.config.security.entity;

import com.tianxing.system.entity.SysRole;
import com.tianxing.system.entity.SysUser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

/**
 * 安全认证用户详情
 *
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-12-11 14:49
 **/

@Data
@Slf4j
public class SecurityUser implements UserDetails {

    /**
     * 当前登录用户
     */
    private transient SysUser currentUserInfo;

    /**
     * 角色
     */
    private transient List<SysRole> roleList;

    /**
     * 当前用户所拥有的角色
     */
    private transient String roleCodes;

    public SecurityUser() {
    }

    public SecurityUser(SysUser sysUser) {
        if (sysUser != null) {
            this.currentUserInfo = sysUser;
        }
    }

    public SecurityUser(SysUser sysUser, List<SysRole> roleList) {
        if (sysUser != null) {
            this.currentUserInfo = sysUser;
            this.roleList = roleList;
            if (!CollectionUtils.isEmpty(roleList)){
                StringJoiner roleCodes = new StringJoiner(",", "[", "]");
                roleList.forEach(e -> roleCodes.add(e.getRoleName()));
                this.roleCodes = roleCodes.toString();
            }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(this.roleList)){
            for (SysRole sysRole: this.roleList) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(sysRole.getRoleName());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return currentUserInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return currentUserInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
