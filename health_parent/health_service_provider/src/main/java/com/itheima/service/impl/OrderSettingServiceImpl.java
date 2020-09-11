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
        String beginDate = date + "-1";
        String endDate = date + "-31";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateBegin = simpleDateFormat.parse(beginDate);
            Date dateEnd = simpleDateFormat.parse(endDate);

            Map map = new HashMap();
            map.put("dateBegin",dateBegin);
            map.put("dateEnd",dateEnd);

            HashMap<String,Object> resultMap = (HashMap)orderSettingDao.getOrderSettingByMonth(map);

            ArrayList<Map> list = new ArrayList<>();
            list.add(resultMap);
            return list;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
