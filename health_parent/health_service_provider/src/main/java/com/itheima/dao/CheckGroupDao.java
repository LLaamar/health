package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LLaamar
 * @date 2020/9/8 11:24
 */
public interface CheckGroupDao {

    /**
     * 根据查询条件分页查询检查组信息
     * @param queryString
     * @return
     */
//    List<CheckGroup> selectByCondition(String queryString);
    Page<CheckGroup> selectByCondition(String queryString);

    /**
     * 添加检查组信息
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 在检查组和检查项的关系表中添加表之间的关系
     * @param checkGroupId
     * @param checkitemId
     */
    void setCheckGroupAndCheckItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);

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
     * 更新检查组表中的检查组信息
     * @param checkGroup
     */
    void edit(CheckGroup checkGroup);

    /**
     * 删除关系表中的原有的关系
     * @param checkGroupId
     */
    void deleteAssociation(Integer checkGroupId);

    /**
     * 根据检查组id删除检查组表中的对应数据
     * @param checkGroupId
     */
    void delete(Integer checkGroupId);
}
