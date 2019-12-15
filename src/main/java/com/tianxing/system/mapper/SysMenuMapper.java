package com.tianxing.system.mapper;

import com.tianxing.system.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author 许仕昊
 * @since 2019-12-15
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

     /**
     * 校验菜单名是否唯一
     * @param menuName
     * @return
     */
     int checkSysMenuName(String menuName);
 }
