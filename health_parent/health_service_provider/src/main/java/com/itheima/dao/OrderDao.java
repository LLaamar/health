package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据会员的ID查询用户的套餐信息,用于判断用户是否重复预约
     * @param memberId
     * @return
     */
    Integer findSetmealIdByMemberId(Integer memberId);

    /**
     * 查询所有的预约信息
     * @param order
     * @return
     */
    List<Order> findByCondition(Order order);

    /**
     * 根据订单ID查询订单信息,返回Order对象,Order对象中包含了Member属性和Setmeal属性
     * @param orderId
     * @return
     */
    /*Order findById(Integer orderId);*/
    /**
     * 根据订单ID查询订单信息,返回一个Map,前端直接在map中获取数据
     * @param orderId
     * @return
     */
    Map findById(Integer orderId);

    /**
     * 获取传入日期新增的订单数
     * @param orderDate
     * @return
     */
    Integer findOrderCountByDate(String orderDate);

    /**
     * 获取传入日期的到访数
     * @param orderDate
     * @return
     */
    Integer findVisitsCountByDate(String orderDate);

    /**
     * 获取指定日期之后的新增预约订单数
     * @param orderDate
     * @return
     */
    Integer findOrderCountAfterDate(String orderDate);

    /**
     * 获取指定日期后的到访订单数
     * @param orderDate
     * @return
     */
    Integer findVisitsCountAfterDate(String orderDate);

    /**
     * 获取热门套餐(取前4)
     * @return
     */
    List<Map> findHotSetmeal();
}
