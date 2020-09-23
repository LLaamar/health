package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.List;
import java.util.Set;

/**
 * @author LLaamar
 * @date 2020/9/18 11:48
 */
public interface RoleDao {

    /**
     * 根据用户的ID查询用户拥有的角色
     * @param userId
     * @return
     */
    Set<Role> findByUserId(Integer userId);

    /**
     * 查询所有的角色信息
     * @return
     */
    List<Role> findAll();

    /**
     * 根据用户ID查询用户的角色信息ID
     * @param userId
     * @return
     */
    List<Integer> findRoleIdsByUserId(Integer userId);
}
