package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

/**
 * @author LLaamar
 * @date 2020/9/14 20:28
 */
public interface OrderSettingDao {
    /**
     * 查询对应日期的信息
     * @param orderDate
     * @return
     */
    OrderSetting getOrderSettingByDate(String orderDate);
}
