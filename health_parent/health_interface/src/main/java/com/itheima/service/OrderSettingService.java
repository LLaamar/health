package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author LLaamar
 * @date 2020/9/11 19:29
 */
public interface OrderSettingService {
    /**
     * Excel文件上传，并解析文件内容保存到数据库
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList);

    /**
     * 根据日期查询预约设置参数
     * @param date
     * @return
     */
    List<Map> getOrderSettingByMonth(String date);

    /**
     * 设置可预约人数
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);
}
