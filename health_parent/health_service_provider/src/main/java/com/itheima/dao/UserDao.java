package com.itheima.dao;
import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
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

    /**
     * 根据条件查询所有的用户信息
     * @param queryString
     * @return
     */
    Page<User> selectByCondition(String queryString);

    /**
     * 新增用户
     * @param user
     */
    void add(User user);
}
