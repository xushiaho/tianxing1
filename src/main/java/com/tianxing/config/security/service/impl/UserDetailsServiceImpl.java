package com.tianxing.config.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tianxing.config.security.entity.SecurityUser;
import com.tianxing.system.entity.SysRole;
import com.tianxing.system.entity.SysUser;
import com.tianxing.system.entity.SysUserRole;
import com.tianxing.system.mapper.SysRoleMapper;
import com.tianxing.system.mapper.SysUserMapper;
import com.tianxing.system.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 自定义userDetailsService - 认证用户详情
 *
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-12-11 14:38
 **/

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 根据账号获取用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库中取出用户信息
        List<SysUser> userList = sysUserMapper.selectList(new QueryWrapper<SysUser>().eq("username", username));
        SysUser sysUser;
        if (!CollectionUtils.isEmpty(userList)){
            sysUser = userList.get(0);
        }else {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new SecurityUser(sysUser,getUserRoles(sysUser.getUserId()));
    }

    public SecurityUser getUserByToken(String token){
        SysUser sysUser = null;
        List<SysUser> loginList = sysUserMapper.selectList(new QueryWrapper<SysUser>().eq("token", token));
        if (!CollectionUtils.isEmpty(loginList)){
            sysUser = loginList.get(0);
        }
        return sysUser != null ? new SecurityUser(sysUser,getUserRoles(sysUser.getUserId())) : null;
    }

    /**
     * 根据用户id获取角色权限信息
     * @param userId
     * @return
     */
    private List<SysRole> getUserRoles(Long userId) {
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(new QueryWrapper<SysUserRole>().eq("user_id", userId));
        List<SysRole> roleList = new LinkedList<>();
        for (SysUserRole sysUserRole: sysUserRoles) {
            SysRole sysRole = sysRoleMapper.selectById(sysUserRole.getRoleId());
            roleList.add(sysRole);
        }
        return roleList;
    }
}
