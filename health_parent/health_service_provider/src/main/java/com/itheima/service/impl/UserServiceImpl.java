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

    /**
     * 根据用户名查询用户以及用户所拥有的权限
     * @param username
     * @return
     */
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
         * 1.根据用户名查询用户所能访问的所有菜单
         * 2.遍历菜单,找出父类菜单
         * 3.根据父类菜单的id,在所有菜单集合中找到子菜单
         * 4.根据子菜单id,在所有菜单集合中找到子菜单的子菜单
         * 5.一直查询
         */
        List<Menu> menuParent = new ArrayList<>();
        // 查询用户能访问的所有菜单集合
        List<Menu> menus = menuDao.findAllMenuByUsername(username);

        if(menus != null && menus.size() > 0){
            for (Menu menu : menus) {
                if (menu.getParentMenuId() == null){
                    // 将找到的父类菜单添加到容器中
                    menuParent.add(menu);
                }
            }
        }
        // 父类菜单挑选完成,所有的子类菜单也挑选完成
        // 遍历父类菜单,根据父类菜单来挑选父类的次一级菜单
        for (Menu menu : menuParent) {
            List<Menu> menuChildren = findChildrenMenu(menu.getId(),menus);
            menu.setChildren(menuChildren);
        }
        return menuParent;
    }

    /**
     * 根据菜单的id,在菜单集合中查询菜单的【子菜单】
     * @param menuId
     * @param menus
     * @return
     */
    private List<Menu> findChildrenMenu(Integer menuId, List<Menu> menus) {
        // 创建集合容器存储子菜单
        List<Menu> children = new ArrayList<>();
        // 遍历所有菜单,找到子菜单
        for (Menu menuChildren : menus) {
            if(menuId.equals(menuChildren.getParentMenuId())){
                // 找到子菜单后,递归调用方法找到子菜单的子菜单
                List<Menu> list = findChildrenMenu(menuChildren.getId(),menus);
                // 将子菜单的子菜单set到Children属性中
                if(list !=null && list.size()>0){
                    menuChildren.setChildren(list);
                }
                // 将子菜单添加到集合中
                children.add(menuChildren);
            }
        }
        // 返回子菜单的集合
        return children;
    }

}
