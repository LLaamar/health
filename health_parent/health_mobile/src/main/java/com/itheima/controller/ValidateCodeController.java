package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author LLaamar
 * @date 2020/9/14 15:26
 */
@Controller
@ResponseBody
@RequestMapping("/validateCode")
public class ValidateCodeController {

    /**
     * 注入JedisPool方便操作redis
     */
    @Autowired
    private JedisPool jedisPool;

    /**
     * 给指定的手机号码发送验证码,并且将验证码定时存储到Redis中,方便后期做校验
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        // 生成验证码
        String param = ValidateCodeUtils.generateValidateCode4String(4);
        System.out.println("=====================");
        System.out.println("你的预约验证码:" + param);
        System.out.println("=====================");
        String key = telephone + RedisMessageConstant.SENDTYPE_ORDER;
        Jedis jedis = null;
        try {
            // 调用工具类中的方法,发送验证码
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,param);

            // 发送成功后,将验证码定时存储到redis中
            jedisPool.getResource().setex(key,300,param);

            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

    }


    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        // 生成验证码
        // 生成验证码
        String param = ValidateCodeUtils.generateValidateCode4String(4);
        String key = telephone + RedisMessageConstant.SENDTYPE_LOGIN;

        System.out.println("=====================");
        System.out.println("你的登录验证码:" + param);
        System.out.println("=====================");

        try {
            /*SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,param);*/
            // 发送成功后,将验证码存储到redis数据库中
            jedisPool.getResource().setex(key,300,param);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (/*Client*/Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
