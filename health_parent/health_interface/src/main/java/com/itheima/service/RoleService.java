package com.itheima.service;

import com.itheima.pojo.Role;

import java.util.List;

/**
 * @author LLaamar
 * @date 2020/9/22 20:09
 */
public interface RoleService {
    /**
     * 查询所有的角色信息
     * @return
     */
    List<Role> findAll();

    /**
     * 查询用户拥有的角色信息ID
     * @param userId
     * @return
     */
    List<Integer> findRoleIdsByUserId(Integer userId);
}
