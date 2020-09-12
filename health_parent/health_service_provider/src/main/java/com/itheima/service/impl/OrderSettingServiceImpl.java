package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LLaamar
 * @date 2020/9/11 19:29
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> list) {

        if(list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                //检查此数据（日期）是否存在
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if(count > 0){
                    //已经存在，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else{
                    //不存在，执行添加操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }


    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String dateBegin = date + "-1";
        String dateEnd = date + "-31";
        /*[
        { date: 1, number: 120, reservations: 1 },
        { date: 3, number: 120, reservations: 1 },
        { date: 4, number: 120, reservations: 120 },
        { date: 6, number: 120, reservations: 1 },
        { date: 8, number: 120, reservations: 1 }
                    ]*/
        Map paramMap = new HashMap<>();
        paramMap.put("dateBegin",dateBegin);
        paramMap.put("dateEnd",dateEnd);

        List<OrderSetting> orderSettingList = orderSettingDao.getOrderSettingByMonth(paramMap);

        List<Map> list = new ArrayList<>();

        for (OrderSetting orderSetting : orderSettingList) {
            Map map = new HashMap();
            map.put("date",orderSetting.getOrderDate().getDate()); // 获取date日期
            map.put("number",orderSetting.getNumber()); // 获取可预约人数
            map.put("reservations",orderSetting.getReservations()); // 获取已预约人数

            list.add(map);
        }
        return list;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        // 1.如果要设置的日期已经设置过了数据,则执行修改操作
        if(count > 0){
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            // 2.如果要设置的日期没有进行过预约人数的设置,则是执行添加的操作
            orderSettingDao.add(orderSetting);
        }
    }
}
