package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @author LLaamar
 * @date 2020/9/7 11:21
 */
public interface CheckItemDao {
    public abstract void add(CheckItem checkItem);

//    List<CheckItem> selectByCondition(String queryString);

    public Page<CheckItem> selectByCondition(String queryString);

    /**
     *【t_checkgroup_checkitem】表中查询检查组id以此作为该检查项是否能删除的依据
     * @param checkItemId
     * @return
     */
    Long findCountByCheckItemId(Integer checkItemId);

    void deleteById(Integer checkItemId);

    CheckItem findById(Integer checkItemId);

    void edit(CheckItem checkItem);

    /**
     * 查询所有的检查项信息
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 根据检查组ID查询所有的检查项
     * @param checkGroupId
     * @return
     */
    List<CheckItem> findCheckItemById(Integer checkGroupId);
}
