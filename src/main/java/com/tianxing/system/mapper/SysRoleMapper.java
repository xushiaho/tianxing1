package com.tianxing.system.mapper;

import com.tianxing.system.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author 许仕昊
 * @since 2019-12-15
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

     /**
     * 校验角色名是否唯一
     * @param roleName
     * @return
     */
     int checkSysRoleName(String roleName);
 }
