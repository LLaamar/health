package com.itheima.service;

import java.util.Map;

/**
 * 操作报表
 * @author LLaamar
 * @date 2020/9/19 20:35
 */
public interface ReportService {
    /**
     * 获取报表相关的数据
     * 1.todayNewMember -> number       今日新增
     * 2.totalMember -> number          截止今日总量
     * 3.thisWeekNewMember -> number    本周新增
     * 4.thisMonthNewMember -> number   本月新增
     * 5.todayOrderNumber -> number
     * 6.todayVisitsNumber -> number
     * 7.thisWeekOrderNumber -> number
     * 8.thisWeekVisitsNumber -> number
     * 9.thisMonthOrderNumber -> number
     * 10.thisMonthVisitsNumber -> number
     * 11.hotSetmeals -> List<Setmeal>
     * @Exception 一个异常
     * @return
     */
    Map<String, Object> getBusinessReport() throws Exception;
}
