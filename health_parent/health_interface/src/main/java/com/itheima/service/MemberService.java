package com.itheima.service;

import com.itheima.entity.Result;
import com.itheima.pojo.Member;

import java.util.List;
import java.util.Map;

/**
 * @author LLaamar
 * @date 2020/9/15 19:22
 */
public interface MemberService {

    /**
     * 根据用户输入的电话号码在t_member表中查询用户的信息
     * @param telephone
     * @return
     */
    Member findByPhoneNumber(String telephone);

    /**
     * 添加用户信息到t_member表中
     * @param member
     */
    void add(Member member);

    /**
     * 查询会员数量随月份的变化情况
     * @param months
     * @return
     */
    List<Integer> findMemberCountByMonth(List<String> months);
}
