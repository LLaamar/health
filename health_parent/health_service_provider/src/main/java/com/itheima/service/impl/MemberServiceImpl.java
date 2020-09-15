package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.service.impl.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;

/**
 * @author LLaamar
 * @date 2020/9/15 19:48
 */
@Service(interfaceClass = MemberService.class)
@Transactional(rollbackFor = {})
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByPhoneNumber(String telephone) {
        return memberDao.findByPhoneNumber(telephone);
    }

    @Override
    public void add(Member member) {
        // 在这里,不仅是通过快速登录可能会调用这个add方法来注册,也有可能用户会通过一般方式填写密码注册
        String password = member.getPassword();
        if(password != null){
            // 将明文密码加密
            password = MD5Utils.md5(password);
            member.setPassword(password);
        }
        memberDao.add(member);
    }
}
