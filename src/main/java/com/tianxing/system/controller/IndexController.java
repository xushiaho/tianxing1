package com.tianxing.system.controller;

import com.tianxing.common.controller.BaseController;
import com.tianxing.common.enumeration.ApiResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 首页 </p>
 *
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-12-18 01:06
 **/

@RestController
public class IndexController extends BaseController {

    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    public ApiResult login(){
        return ApiResult.ok("登录系统成功", null);
    }
}
