package com.tianxing.system.service;

import com.tianxing.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 许仕昊
 * @since 2019-12-15
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
    * 校验角色名是否唯一
    * @param roleName
    * @return
    */
    String checkSysRoleName(String roleName);
}
