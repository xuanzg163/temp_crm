package com.shsxt.crm.controller;

import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserInfo;
import com.shsxt.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.util.calendar.BaseCalendar;

/**
 * @author zhangxuan
 * @date 2018/10/24
 * @time 11:02
 */

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(String userName, String userPwd){
        ResultInfo resultInfo = new ResultInfo();

        try {
             UserInfo userInfo = userService.login(userName,userPwd);
            resultInfo.setMsg("登陆成功");
            resultInfo.setResult(userInfo);
        } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setCode(300);
            resultInfo.setMsg(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(300);
            resultInfo.setMsg(e.getMessage());
        }

        return resultInfo;
    }
}
