package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.model.UserInfo;
import com.shsxt.crm.po.User;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangxuan
 * @date 2018/10/24
 * @time 11:05
 */

@Service
public class UserService extends BaseService<User> {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登陆
     * @param userName
     * @param userPwd
     */
    public UserInfo login(String userName, String userPwd){

        //1、校验参数
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名为空");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户密码为空");

        //2、通过用户名查询用户
        User user = userMapper.queryUserByName(userName);
        AssertUtil.isTrue(user == null,"用户不存在");

        //3、匹配加密过后的密码与后台密码是否一致
        AssertUtil.isTrue(!Md5Util.encode(userPwd).equals(user.getUserPwd())
                ,"用户密码或用户名不正确");

        return createUserInfo(user);

    }

    private UserInfo createUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userInfo.setUserName(user.getUserName());
        userInfo.setRealName(user.getTrueName());
        return userInfo;
    }
}
