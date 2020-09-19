package com.itheima.dao;
import com.itheima.pojo.User;


/**
 * @author LLaamar
 * @date 2020/9/18 10:20
 */
public interface UserDao {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}
