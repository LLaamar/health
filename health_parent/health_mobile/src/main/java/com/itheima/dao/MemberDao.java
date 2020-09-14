package com.itheima.dao;

import com.itheima.pojo.Member;

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
}
