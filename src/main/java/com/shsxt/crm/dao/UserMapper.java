package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseDao;
import com.shsxt.crm.po.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper extends BaseDao<User> {

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    public User queryUserByName(String userName);

}