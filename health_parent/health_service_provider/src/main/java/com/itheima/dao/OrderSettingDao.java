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

    /**
     * 添加预约设置
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 根据预约的日期获取预约设置的数量
     * @param orderDate
     * @return
     */
    long findCountByOrderDate(Date orderDate);

    /**
     * 修改指定日期的可预约人数
     * @param orderSetting
     */
    void editNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 根据月份获取预约设置
     * @param paramMap
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(Map paramMap);

    /**
     * 根据预约的日期查询对应日期的信息
     * @param orderDate
     * @return
     */
    OrderSetting getOrderSettingByDate(Date orderDate);

    /**
     * 将指定日期的已预约人数加一
     * @param orderDate
     * @param
     */
    void upOneReservations(@Param("orderDate") Date orderDate, @Param("reservations")Integer reservations);

    /**
     * 修改预约设置
     * @param orderSetting
     */
    void edit(OrderSetting orderSetting);
}
