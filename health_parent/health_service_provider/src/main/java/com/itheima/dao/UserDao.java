package com.itheima.dao;

import com.itheima.pojo.Menu;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;

import java.util.List;
import java.util.Set;

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
     * 根据用户名查询用户能看见的菜单
     * @param username
     * @return
     */
    List<Menu> getMenuByUsername(String username);
}
