package com.tianxing.system.service.impl;

import com.tianxing.common.utils.Constants;
import com.tianxing.system.entity.SysMenu;
import com.tianxing.system.mapper.SysMenuMapper;
import com.tianxing.system.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author 许仕昊
 * @since 2019-12-15
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
    * 校验菜单名是否唯一
    * @param menuName
    * @return
    */
    @Override
    public String checkSysMenuName(String menuName) {
        int count = sysMenuMapper.checkSysMenuName(menuName);
        if (count > 0){
            return Constants.MENU_NAME_NOT_UNIQUE;
        }
        return Constants.MENU_NAME_UNIQUE;
    }

}
