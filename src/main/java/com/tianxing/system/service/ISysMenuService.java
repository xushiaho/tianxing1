package com.tianxing.system.service;

import com.tianxing.system.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author 许仕昊
 * @since 2019-12-15
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
    * 校验菜单名是否唯一
    * @param menuName
    * @return
    */
    String checkSysMenuName(String menuName);
}
