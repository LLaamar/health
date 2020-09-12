package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LLaamar
 * @date 2020/9/9 18:17
 */
public interface SetmealDao {
    /**
     * 在套餐表中新增套餐
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 在套餐和检查组的关系表中添加数据
     * @param setmealId
     * @param checkgroupId
     */
    void setSetmealAndCheckGroup(@Param("setmealId") Integer setmealId , @Param("checkgroupId") Integer checkgroupId);

    /**
     * 查询所有的套餐信息
     * @param queryString
     * @return
     */
    Page<Setmeal> pageQuery(String queryString);

    /**
     * 根据id查询套餐
     * @param setmealId
     * @return
     */
    Setmeal findById(Integer setmealId);

    /**
     * 查询套餐中包含的检查组id
     * @param setmealId
     * @return
     */
    List<Integer> findCheckgroupIdsBySetmealId(Integer setmealId);

    /**
     * 删除套餐和检查组之间的关系
     * @param setmealId
     */
    void deleteAssociation(Integer setmealId);

    /**
     * 修改套餐信息
     * @param setmeal
     */
    void edit(Setmeal setmeal);

    /**
     * 删除套餐表中的套餐数据
     * @param id
     */
    void delete(Integer id);

    /**
     * 查询所有的套餐数据
     * @return
     */
    List<Setmeal> findAll();
}
