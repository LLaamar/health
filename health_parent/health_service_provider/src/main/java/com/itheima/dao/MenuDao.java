package com.itheima.dao;

import com.itheima.pojo.Menu;

import java.util.List;


/**
 * @author LLaamar
 * @date 2020/9/18 19:07
 */
public interface MenuDao {

    /**
     * 根据角色ID查询角色能看到的菜单,但是这个方法在SQL语句中用了循环,不好
     * @param roleId
     * @return
     */
    List<Menu> findByRoleId(Integer roleId);

    /**
     * 根据id查询菜单
     * @param menuId
     * @return
     */
    List<Menu> findChildrenMenuById(Integer menuId);

    /**
     * 根据用户名查询该用户所有的菜单,SQL语句中没有循环,在程序代码中使用了递归
     * @param username
     * @return
     */
    List<Menu> findAllMenuByUsername(String username);
}
