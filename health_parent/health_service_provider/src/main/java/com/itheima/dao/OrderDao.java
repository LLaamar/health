package com.itheima.dao;

import com.itheima.pojo.Order;

/**
 * @author LLaamar
 * @date 2020/9/14 20:29
 */
public interface OrderDao {
    /**
     * 新增订单
     * @param order
     */
    void add(Order order);

    Integer findSetmealIdByMemberId(Integer memberId);
}
