package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.Map;

/**
 * @author LLaamar
 * @date 2020/9/11 19:31
 */
public interface OrderSettingDao {

    void add(OrderSetting orderSetting);

    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    Map<String,Object> getOrderSettingByMonth(Map date);
}
