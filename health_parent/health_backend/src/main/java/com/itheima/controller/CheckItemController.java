package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;

import com.itheima.service.CheckItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author LLaamar
 * @date 2020/9/7 9:41
 */
@Controller // 交给Spring管理
@ResponseBody // 返回值是自定义类型
@RequestMapping("/checkitem") // 访问的主路径
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @RequestMapping(value = "/add")
    public Result add(@RequestBody CheckItem checkItem){
//        System.out.println("===========================");
//        System.out.println("我执行了");
//        System.out.println("===========================");
        try{
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        }catch (Exception e){
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

    }

    @RequestMapping(value = "/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        // 开启分页查询
        PageResult pageResult = checkItemService.findPage(currentPage,pageSize,queryString);
        return pageResult;
    }


    @RequestMapping(value = "/delete")
    public Result delete(/*@RequestBody */Integer id){
        try{
            checkItemService.delete(id);
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }catch (RuntimeException e){
            return new Result(false,e.getMessage());
        }catch (Exception e){
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }


    @RequestMapping(value = "/findById")
    public Result findById(/*@RequestBody */Integer id){
        try{
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping(value = "/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        System.out.println("编辑的请求抵达");
        try{
            checkItemService.edit(checkItem);
//            System.out.println("====================");
//            System.out.println("编辑成昆");
//            System.out.println("====================");

            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }catch (Exception e){
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    @RequestMapping(value = "/findAll")
    public Result findAll(){
            List<CheckItem> checkItemList = checkItemService.findAll();
            if(checkItemList != null && checkItemList.size() > 0 ){
                return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
            }else{
                return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
            }
    }
}
