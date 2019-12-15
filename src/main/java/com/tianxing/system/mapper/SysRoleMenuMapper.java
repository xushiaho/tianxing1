package com.tianxing.system.mapper;

import com.tianxing.system.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 角色菜单关联表  Mapper 接口
 * </p>
 *
 * @author 许仕昊
 * @since 2019-12-15
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

     /**
     * 校验角色名是否唯一
     * @param roleName
     * @return
     */
     int checkSysRoleMenuName(String roleName);
 }
