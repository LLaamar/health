package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.service.impl.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

/**
 * @author LLaamar
 * @date 2020/9/9 16:19
 */
@Controller // 交给Spring管理
@ResponseBody // 表明返回自定义类型的Json
@RequestMapping("/setmeal") // 访问的主路径
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    /**
     *  注入JedisPool操作Redis数据库
     */
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        // 上传完成后,将图片的云地址返回,方便页面做展示
        try{
            // 获取原始的文件名
            String originalFilename = imgFile.getOriginalFilename();
            // 获取文件的后缀名
            int lastIndexOf = originalFilename.lastIndexOf(".");
            String suffix = originalFilename.substring(lastIndexOf);
            // 使用UUID和后缀名拼接为新的不重复的ID
            String fileName = UUID.randomUUID().toString() + suffix;
            // 使用工具类上传图片
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            // 从JedisPool中获取操作redis的对象
            Jedis resource = jedisPool.getResource();
            // 将预计上传的图片名添加到redis数据库中的set中
            resource.sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            System.out.println("=========获取预计上传的文件名=======");
            System.out.println(fileName);
            System.out.println("=========获取预计上传的文件名=======");

            // 上传成功后
            return new Result(true , MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal , Integer[] checkgroupIds ){
        try{
            setmealService.add(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageResult pageResult =  setmealService.pageQuery(currentPage,pageSize,queryString);

        return pageResult;
    }


    @RequestMapping("/findById")
    public Result findById(Integer id){
        System.out.println("findById:方法成功执行");
        Integer setmealId = id;
        Setmeal setmeal = setmealService.findById(setmealId);
        Result result = new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        return result;
    }


    @RequestMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(Integer id){

        try{
            List<Integer> checkGroupIds = setmealService.findCheckgroupIdsBySetmealId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkGroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }


    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal , Integer[] checkgroupIds){

        System.out.println("=======编辑窗口传递过来的图片名======");
        System.out.println(setmeal.getImg());
        System.out.println("=======编辑窗口传递过来的图片名======");

        try {
            setmealService.edit(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
        }catch (Exception e){
            //新增失败
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            setmealService.delete(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        }catch (Exception e){
            //新增失败
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }


}
