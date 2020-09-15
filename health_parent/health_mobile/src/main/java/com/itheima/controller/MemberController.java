package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author LLaamar
 * @date 2020/9/15 19:00
 */
@Controller
@ResponseBody
@RequestMapping("/member")
public class MemberController {

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/login")
    public Result login(HttpServletResponse response , @RequestBody Map map){
        /**
         * 2.用户登录:
         * 登录成功的前提:验证码校验通过
         * 	2.1验证码校验:从redis数据库中取出验证码和用户输入的验证码比对
         * 	2.2用户登录:
         * 用户登录成功有下列情况:
         * 	1)用户信息在t_member表中,允许登录
         * 	2)用户信息不在t_member表中,同样允许登录,但是要将用户信息进行注册
         */

        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        // 结合redis判断用户输入的验证码是否正确
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if(validateCode == null || !validateCode.equals(validateCodeInRedis)){
            // 验证码不匹配
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        // 用户验证码输入正确,判断用户是否已经注册过
        Member member = memberService.findByPhoneNumber(telephone);
        if(member == null){
            // 用户未注册,自动注册,向t_member表中添加数据
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);
        }
        // 登录成功,将用户的手机号码写入到Cookie中
        // 写入Cookie，跟踪用户
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        // 路径
        cookie.setPath("/");
        // 设置Cookie的有效期,这里是30天
        cookie.setMaxAge(60*60*24*30);
        response.addCookie(cookie);

        // 将member对象序列化为redis能保存的Json
        String json = JSON.toJSON(member).toString();
        jedisPool.getResource().setex(telephone,60*30,json);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
