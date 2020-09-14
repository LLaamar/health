package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map;

/**
 * @author LLaamar
 * @date 2020/9/14 18:55
 */
public interface OrderService {
    /**
     * 用户预约参加体检
     * @param map
     * @return
     */
    Result order(Map map);
}
