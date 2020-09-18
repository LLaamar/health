package com.itheima.dao;

import com.itheima.pojo.Role;

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
}
