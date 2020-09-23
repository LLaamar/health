package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LLaamar
 * @date 2020/9/19 20:35
 */
@Service(interfaceClass = ReportService.class)
@Transactional(rollbackFor = {})
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private MemberDao memberDao;

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        // 本周周一
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        // 今日日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        // 本月第一天
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

        // 1.根据今日日期获取今天注册的会员数
        Integer todayNewMember = memberDao.findMemberCountByDate(today);
        // 2.截止今日总会员量
        Integer totalMember = memberDao.findMemberTotalCount();
        // 3.获取本周新增--即获取本周一到最新时间的注册人数
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);
        // 4.获取本月新增--即获取本月第一天到最新事件的注册人数
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDay4ThisMonth);
        // 5.获取今日新增订单数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
        // 6.获取今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
        // 7.获取本周新增订单数
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(thisWeekMonday);
        // 8.获取本周到诊数
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(thisWeekMonday);
        // 9.获取本月新增订单数
        Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(firstDay4ThisMonth);
        // 10.获取本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDay4ThisMonth);
        // 11.获取热门套餐
        List<Map> hotSetmeal = orderDao.findHotSetmeal();

        map.put("reportDate",today);
        map.put("todayNewMember",todayNewMember);
        map.put("totalMember",totalMember);
        map.put("thisWeekNewMember",thisWeekNewMember);
        map.put("thisMonthNewMember",thisMonthNewMember);
        map.put("todayOrderNumber",todayOrderNumber);
        map.put("thisWeekOrderNumber",thisWeekOrderNumber);
        map.put("thisMonthOrderNumber",thisMonthOrderNumber);
        map.put("todayVisitsNumber",todayVisitsNumber);
        map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        map.put("hotSetmeal",hotSetmeal);
        return map;



    }
}
