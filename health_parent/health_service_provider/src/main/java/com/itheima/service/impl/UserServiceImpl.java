package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MenuDao;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author LLaamar
 * @date 2020/9/18 10:16
 */
@Service(interfaceClass = UserService.class)
@Transactional(rollbackFor = {})
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private MenuDao menuDao;

    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if(user == null){
            return null;
        }
        Set<Role> roles = roleDao.findByUserId(user.getId());
        if(roles != null && roles.size() > 0){
            for (Role role : roles) {
                // 查询角色的权限
                Set<Permission> permissions = permissionDao.findByRoleId(role.getId());
                if (permissions != null && permissions.size() > 0){
                    role.setPermissions(permissions);
                }
                role.setPermissions(permissions);
            }
        }
        user.setRoles(roles);

        return user;
    }

    @Override
    public List<Menu> getMenuByUsername(String username) {
        /**
         * 1.查询用户id
         * 2.根据用户id查询用户对应的角色id
         * 3.根据角色id,查询角色对应的菜单
         */
        List<Menu> menus = new ArrayList<>();

        User user = userDao.findByUsername(username);

        // 根据用户的id查询用户的角色
        Set<Role> roles = roleDao.findByUserId(user.getId());

        if (roles != null && roles.size() > 0){
            for (Role role : roles) {
                // 根据角色ID查询角色对应的菜单
                menus = menuDao.findByRoleId(role.getId());
            }
        }

        return menus;
    }




}
