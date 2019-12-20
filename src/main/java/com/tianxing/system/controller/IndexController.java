package com.tianxing.system.controller;

import com.tianxing.common.controller.BaseController;
import com.tianxing.common.enumeration.ApiResult;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p> 首页 </p>
 *
 * @program: tianxing
 * @author: 许仕昊
 * @create: 2019-12-18 01:06
 **/

@Controller
public class IndexController extends BaseController {

    // @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    //@ResponseBody
//    public ApiResult login(){
//        return ApiResult.ok("登录系统成功", null);
//    }
//    public String login(){
//        return "login";
//    }
}
