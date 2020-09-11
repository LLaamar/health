package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;

import java.util.List;

/**
 * @author LLaamar
 * @date 2020/9/8 11:20
 */
public interface CheckGroupService {

    /**
     * 分页查询所有的检查组信息
     * @param queryString
     * @return
     */
//    List<CheckGroup> pageQuery(String queryString);
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 新增检查组,同时要新增【检查组和检查项的关系】
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 根据检查组id查询检查组信息
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 根据检查组id查询该检查组包含的检查项的id
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    /**
     * 修改检查组的信息、检查组中包含的检查项的信息
     * @param checkGroup
     * @param checkitemIds
     */
    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 根据检查组id在检查组表中删除检查组数据,另外要在关系表中把相应的关系删除掉
     * @param checkGroupId
     */
    void delete(Integer checkGroupId);

    /**
     * 查询所有的检查组信息,在操作套餐时提供数据
     * @return
     */
    List<CheckGroup> findAll();
}
