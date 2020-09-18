package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

/**
 * @author LLaamar
 * @date 2020/9/18 18:36
 */
@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/getUsername")
    public Result getUsername(){
        // 完成认证后,会将用户信息保存到框架提供的上下文中
        try{
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }



    @RequestMapping("/getUsersMenu")
    public Result getUsersMenu(){

        try{
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // 用户名是唯一的,根据用户名查询这个用户拥有的菜单
            List<Menu> menus =  userService.getMenuByUsername(user.getUsername());

            return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,menus);
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_MENU_FAIL);
        }

    }





}
