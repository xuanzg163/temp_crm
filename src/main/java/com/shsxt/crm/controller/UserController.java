package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserInfo;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.util.calendar.BaseCalendar;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangxuan
 * @date 2018/10/24
 * @time 11:02
 */

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("updateUserPwd")
    @ResponseBody
    public ResultInfo updateUserPwd(String oldPassword,
                                    String newPassword,
                                    String confirmPassword,
                                    HttpServletRequest request){

        ResultInfo resultInfo = new ResultInfo();

        /**
         * 取前台存入cookie中的id
         */
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);

        userService.updateUserPwd(oldPassword,newPassword,confirmPassword,userId);

        return success("修改成功");

    }
    /**
     * 用户登陆
     * @param userName
     * @param userPwd
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(String userName, String userPwd){
             UserInfo userInfo = userService.login(userName,userPwd);
             return success("登陆成功",userInfo);
    }
}
