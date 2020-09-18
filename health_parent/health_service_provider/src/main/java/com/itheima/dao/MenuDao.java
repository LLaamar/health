package com.itheima.dao;

import com.itheima.pojo.Menu;

import java.util.List;
import java.util.Set;

/**
 * @author LLaamar
 * @date 2020/9/18 19:07
 */
public interface MenuDao {
    /**
     * 根据角色ID查询角色能看到的菜单
     * @param roleId
     * @return
     */
    List<Menu> findByRoleId(Integer roleId);

    /**
     * 根据菜单ID查询所有的子菜单
     * @param menuId
     * @return
     */
    List<Menu> findChildrenMenu(Integer menuId);

    /**
     * 根据id查询所有的父类菜单
     * @param id
     * @return
     */
    Set<Menu> findParentMenuByRoleId(Integer id);

    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    Set<Menu> findChildrenMenuById(Integer id);
}
