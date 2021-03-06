package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author LLaamar
 * @date 2020/9/9 16:23
 */
public interface SetmealService {

    /**
     * 新建套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据id查询套餐
     * @param setmealId
     * @return
     */
    Setmeal findById(Integer setmealId);

    /**
     * 根据套餐的ID查询包含的检查组id
     * @param setmealId
     * @return
     */
    List<Integer> findCheckgroupIdsBySetmealId(Integer setmealId);

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void edit(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 删除套餐
     * @param setmealId
     */
    void delete(Integer setmealId);

    /**
     * 查询所有的套餐数据,为移动端的展示提供数据
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 获取套餐的订单数据制饼形图
     * @return
     */
    List<Map<String, Object>> findSetmealCount();
}
