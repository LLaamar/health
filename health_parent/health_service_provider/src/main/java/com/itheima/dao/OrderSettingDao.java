package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LLaamar
 * @date 2020/9/11 19:31
 */
public interface OrderSettingDao {

    void add(OrderSetting orderSetting);

    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map paramMap);

    /**
     * 查询对应日期的信息
     * @param orderDate
     * @return
     */
    OrderSetting getOrderSettingByDate(String orderDate);


    /**
     *
     * @param date
     * @param
     */
    void upOneReservations(@Param("orderDate") Date date, @Param("reservations")Integer reservations);
}
