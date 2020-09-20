package com.itheima.dao;

import com.itheima.pojo.Member;

import java.util.List;

/**
 * @author LLaamar
 * @date 2020/9/14 20:29
 */
public interface MemberDao {
    /**
     * 根据电话号码查询 Member
     * @param phoneNumber
     * @return
     */
    Member findByPhoneNumber(String phoneNumber);

    /**
     * 新增用户
     * @param newMember
     */
    void add(Member newMember);

    /**
     * 根据用户ID查询用户信息
     * @param memberId
     * @return
     */
    Member findMemberById(Integer memberId);

    /**
     * 根据时间查询会员数量的辩护按情况
     * @param value
     * @return
     */
    Integer findMemberCountBeforeDate(String value);

    /**
     * 获取今日新增会员数
     * @param today
     * @return
     */
    Integer findMemberCountByDate(String today);

    /**
     * 获取会员总数
     * @return
     */
    Integer findMemberTotalCount();

    /**
     * 传入一个周一的数据,查询本周新增的会员数
     * @param date
     * @return
     */
    Integer findMemberCountAfterDate(String date);
}
