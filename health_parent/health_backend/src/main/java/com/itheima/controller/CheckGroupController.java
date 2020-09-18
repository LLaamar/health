package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author LLaamar
 * @date 2020/9/8 11:13
 */
@Controller // 交给Spring管理
@ResponseBody // 返回自定义类型的Json
@RequestMapping("/checkgroup") // 访问路径的主路径
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        // 页码
        Integer currentPage = queryPageBean.getCurrentPage();
        // 每页的条数
        Integer pageSize = queryPageBean.getPageSize();
        // 查询的条件
        String queryString = queryPageBean.getQueryString();

        /*
            PageHelper.startPage(currentPage,pageSize);
            List<CheckGroup> checkGroupList = checkGroupService.pageQuery(queryString);
            PageInfo<CheckGroup> pageInfo = new PageInfo<>(checkGroupList);
            PageResult result = new PageResult(pageInfo.getTotal(),pageInfo.getList());
        */

        PageResult pageResult = checkGroupService.pageQuery(currentPage, pageSize, queryString);
        return pageResult;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){

        try {
            if(checkGroup == null){
                throw new Exception("============检查项里啥也没有=========");
            }else{
                checkGroupService.add(checkGroup,checkitemIds);
            }
            return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            //新增失败
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }


    @RequestMapping("/findById")
    public Result findById(Integer id){
        CheckGroup checkGroup = checkGroupService.findById(id);
        if(checkGroup != null){
            Result result = new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS);
            result.setData(checkGroup);
            return result;
        }else{
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        try{
            List<Integer> checkitemIds =
                    checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            //新增失败
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkGroupService.delete(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            //新增失败
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<CheckGroup> checkGroupList = checkGroupService.findAll();
            if (checkGroupList != null && checkGroupList.size() > 0){
                return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
            }else{
                return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
            }
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
