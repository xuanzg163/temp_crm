package com.shsxt.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangxuan
 * @date 2018/10/24
 * @time 10:08
 */

@Controller
public class IndexController {

    /**
     * 登陆页面
     * @param request
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request) {
        request.setAttribute("ctx", request.getContextPath());

        //当前项目路径
        return "index";
    }
}
