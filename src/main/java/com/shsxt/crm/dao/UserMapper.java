package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseDao;
import com.shsxt.crm.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;


@Repository
public interface UserMapper extends BaseDao<User> {

    /**
     * 根据用户id更新用户密码
     * @param userPwd
     * @param id
     * @return
     */
    public Integer updateUserPwd(@Param("userPwd") String userPwd,
                                 @Param("id") Integer id);

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    public User queryUserByName(String userName);


}