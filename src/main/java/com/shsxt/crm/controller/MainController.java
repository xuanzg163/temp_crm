package com.shsxt.crm.controller;

import com.shsxt.crm.po.User;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangxuan
 * @date 2018/10/24
 * @time 10:08
 */

@Controller
public class MainController {

    @Autowired
    private UserService userService;
    /**
     * 主页面
     * @param request
     * @return
     */
    @RequestMapping("main")
    public String index(HttpServletRequest request) {
        request.setAttribute("ctx", request.getContextPath());

        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userService.queryById(userId);
        request.setAttribute("user",user);
        //当前项目路径
        return "main";
    }
}
