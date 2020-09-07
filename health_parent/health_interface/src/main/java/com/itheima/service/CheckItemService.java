package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;

/**
 * @author LLaamar
 * @date 2020/9/7 9:55
 */
public interface CheckItemService {
    /**
     * 新增检查项
     * @param checkItem
     * @return
     */
    public void add(CheckItem checkItem);



    /**
     * 检查项的分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据传递的id删除检查项
     * @param id
     */
    void delete(Integer id);

    CheckItem findById(Integer checkItemId);

    void edit(CheckItem checkItem);
}
