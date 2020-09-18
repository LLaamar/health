package com.itheima.dao;

import com.itheima.pojo.Permission;

import java.util.Set;

/**
 * @author LLaamar
 * @date 2020/9/18 14:16
 */
public interface PermissionDao {

    /**
     * 根据角色ID查询角色包含的所有权限
     * @param roleId
     * @return
     */
    Set<Permission> findByRoleId(Integer roleId);
}
