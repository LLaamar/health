package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author LLaamar
 * @date 2020/9/18 9:02
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    /**
     * 根据用户名查询数据库获取用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*System.out.println("用户输入的用户名为:" + username);*/
        User user = userService.findByUsername(username);

        if (user == null){
            // 用户不存在
            return null;
        }
        /*System.out.println(user.getPassword());*/

        List<GrantedAuthority> list = new ArrayList<>();
        // 用户存在,获取用户所有的角色
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {

            /*
                System.out.println("role ===========>" + role);
                System.out.println("========================");
            */
            // 遍历用户的所有角色,并把这些角色添加到框架中
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            // 获取用户拥有的权限
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                /*
                    System.out.println("permission ===========>" + permission);
                    System.out.println("========================");
                */

                // 为用户的角色授权
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
        /*System.out.println(securityUser.getPassword());*/
        return securityUser;
    }
}
