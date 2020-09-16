package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.service.impl.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 处理预约请求
 * @author LLaamar
 * @date 2020/9/14 18:34
 */
@Controller
@ResponseBody
@RequestMapping("/order")
public class OrderController {

    /**
     * 注入Service
     */
    @Reference
    private OrderService orderService;

    /**
     * 注入JedisPool资源
     */
    @Autowired
    private JedisPool jedisPool;

    /**
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        // 在Controller层中,只需要对用户输入的验证码做判断,验证码正确是预约成功的前提

        // 获取用户输入的验证码
        String validateCode = (String) map.get("validateCode");
        // 查看redis中是否有对应的验证码信息
        String telephone = (String) map.get("telephone");
        // 获取redis中的验证码的key
        String key = telephone + RedisMessageConstant.SENDTYPE_ORDER;

        Jedis jedis = jedisPool.getResource();
        String validateCodeInRedis = jedis.get(key);

        if(validateCodeInRedis == null || !validateCodeInRedis.equals(validateCode)){
            // redis中的验证码为空,或者不为空时和用户输入的验证码不同,则预约直接失败
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        // 释放redis资源
        jedis.close();

        Result result = null;
        try {

            /**
             * 这里不直接返回boolean是因为用户预约失败有多种可能,比如重复预约,人数已满等
             * 将这些业务相关的事交给Service层做,Controller层只负责接收请求,准备参数,发送响应
             */
            // 验证码正确,则调用Service层的order方法
            map.put("orderType", Order.ORDERTYPE_WEIXIN);

            result = orderService.order(map);

            // 判断用户是否预约成功
            Boolean flag = result.isFlag();
            if(flag){
                // 预约成功,发送预约成功的短信,告知用户预约的时间
                String orderDate = (String) map.get("orderDate");
                /*try{
                    SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
                }catch (ClientException e){
                    e.printStackTrace();
                }*/
                System.out.println("============================");
                System.out.println("预约成功!请您于" + orderDate + "到指定地点进行体检");
                System.out.println("============================");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"系统错误,请检查");
        }
        return result;

    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Result result = orderService.findById(id);
        return result;
    }
}
