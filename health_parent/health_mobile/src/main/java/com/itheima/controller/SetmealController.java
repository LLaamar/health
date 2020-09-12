package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author LLaamar
 * @date 2020/9/12 14:39
 */
@Controller
@ResponseBody
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try{
            List<Setmeal> setmealList = setmealService.findAll();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmealList);
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }


    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

}
