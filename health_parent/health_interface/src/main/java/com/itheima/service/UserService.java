package com.itheima.service;

import com.itheima.pojo.Menu;
import com.itheima.pojo.User;

import java.util.List;
import java.util.Set;


/**
 * @author LLaamar
 * @date 2020/9/18 10:15
 */
public interface UserService {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据用户名查询用户拥有的菜单
     * @param username
     * @return
     */
    List<Menu> getMenuByUsername(String username);
}
