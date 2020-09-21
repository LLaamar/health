package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MenuDao;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.*;
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

    Integer count = 1;
    @Override
    public List<Menu> getMenuByUsername(String username) {
        long begin = System.currentTimeMillis();

        /**
         * 1.根据用户名查询用户所能访问的所有菜单
         * 2.遍历菜单,找出父类菜单
         * 3.根据父类菜单的id,在所有菜单集合中找到子菜单
         * 4.根据子菜单id,在所有菜单集合中找到子菜单的子菜单
         * 5.一直查询
         */

        // 查询用户能访问的所有菜单集合
        List<Menu> menus = menuDao.findAllMenuByUsername(username);

        List<Menu> menuParent = new ArrayList<>();
        /*List<Menu> menuChildren = new ArrayList<>();*/

        int initSize = (int) (menus.size() / 0.75f);

        System.out.println(initSize);

        // 为了快速查询,我们将建立映射关系
        Map<Integer,ArrayList<Menu>> menuMapChildren = new HashMap<>(initSize);

        if(menus != null && menus.size() > 0){
            for (Menu menu : menus) {
//                count++;
                if (menu.getParentMenuId() == null){
                    // 将找到的父类菜单添加到容器中
                    menuParent.add(menu);
                    continue;
                }

                /*menuChildren.add(menu);*/
                // 不是顶级父菜单,完善父类菜单和子类菜单集合的映射关系

                ArrayList menuTemp = menuMapChildren.get(menu.getParentMenuId());
                if(menuTemp == null){
                    // 第一次建立映射关系,创建子菜单集合
                    menuTemp = new ArrayList<>();
                }
                menuTemp.add(menu);
                menuMapChildren.put(menu.getParentMenuId(),menuTemp);

            }
        }
        // 完善子菜单的关系
        /*for (Menu menu : menuChildren) {
            count++;
            List<Menu> menuListChildren = menuMapChildren.get(menu.getId());
            menu.setChildren(menuListChildren);
        }*/

        // 完善父菜单的关系
        /*for (Menu menu : menuParent) {
            count++;
            // findChildrenMenu
            List<Menu> menuListChildren = menuMapChildren.get(menu.getId());
            menu.setChildren(menuListChildren);
        }*/

        // 既然要完善父菜单和子菜单,这里直接遍历所有的菜单就把父类菜单和子类菜单都完善了
        // 既然直接完善所有的菜单就行,而且代码执行完最终只用返回父类,那就没有必要再留着子类了
        for (Menu menu : menus) {
            count++;
            // findChildrenMenu
            List<Menu> menuListChildren = menuMapChildren.get(menu.getId());
            menu.setChildren(menuListChildren);
        }

        System.out.println("count:"+count);

        // 遍历父类菜单,根据父类菜单来挑选父类的次一级菜单
        /*
            for (Menu menu : menuParent) {
                List<Menu> menuChildren = findChildrenMenu(menu.getId(),menus);
                menu.setChildren(menuChildren);
                menus.remove(menu);
            }
        */

        long end = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis() + ": 循环" + count + "次" + "耗时 = " + (end - begin));
        count = 1;
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
