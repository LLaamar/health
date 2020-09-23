package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author LLaamar
 * @date 2020/9/22 20:02
 */
@Controller
@ResponseBody
@RequestMapping("/role")
public class RoleController {
    @Reference
    private RoleService roleService;

    /**
     * 查询所有的角色信息
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        // 查询所有的角色信息
        try{
            List<Role> roleList = roleService.findAll();
            if (roleList != null && roleList.size() > 0){
                return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,roleList);
            }else{
                return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }


    }

    /**
     * 根据用户id查询用户所拥有的角色信息
     * @param id
     * @return
     */
    @RequestMapping("/findRoleIdsByUserId")
    public Result findRoleIdsByUserId(Integer id){
        try{
            List<Integer> roleList = roleService.findRoleIdsByUserId(id);
            if(roleList != null && roleList.size() > 0){
                return new Result(true,MessageConstant.QUERY_ROLE4USER_SUCCESS,roleList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.QUERY_ROLE4USER_FAIL);
    }
}
