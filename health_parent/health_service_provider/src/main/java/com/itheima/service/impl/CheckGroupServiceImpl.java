package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LLaamar
 * @date 2020/9/8 11:21
 */
@Service(interfaceClass = CheckGroupService.class) // 开启事务之后必须指定实现的接口
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /*@Override
   public List<CheckGroup> pageQuery(String queryString) {
       List<CheckGroup> checkGroupList = checkGroupDao.selectByCondition(queryString);
       return checkGroupList;
   }*/

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 在新增检查组信息的时候,除了操作【检查组表】还要操作【检查组和检查项的关系表】
        // 操作检查组表
        checkGroupDao.add(checkGroup);
        // 获取关系表中所需要的检查组id
        Integer checkGroupId = checkGroup.getId();

        // 遍历出检查项的id,然后将上一次在检查组表中添加的数据的id一起添加到关系表中
        if(checkitemIds != null && checkitemIds.length > 0){
            Map<String,String> map = new HashMap<>();
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.setCheckGroupAndCheckItem(checkGroupId,checkitemId);
            }
        }

/*
        TemplateUtils.generateMobileStaticHtml();
*/

    }

    @Override
    public CheckGroup findById(Integer id) {
        CheckGroup checkGroup =  checkGroupDao.findById(id);
        return checkGroup;
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        List<Integer> checkitemList = checkGroupDao.findCheckItemIdsByCheckGroupId(id);
        return checkitemList;
    }


    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        Integer checkGroupId = checkGroup.getId();
        // 修改关系表中的数据
        // 1.先清除原有关系
        checkGroupDao.deleteAssociation(checkGroupId);
        // 2.写入
        if (checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.setCheckGroupAndCheckItem(checkGroupId,checkitemId);
            }
        }
        // 修改检查组表中的信息
        checkGroupDao.edit(checkGroup);

    }

    @Override
    public void delete(Integer checkGroupId) {
        // 由于【检查组和检查项的关系表】和【检查组表】之间存在关联关系
        // 所以要先删除【关系】,再删除【检查组】数据
        checkGroupDao.deleteAssociation(checkGroupId);
        checkGroupDao.delete(checkGroupId);
    }

    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> checkGroupList =  checkGroupDao.findAll();
        return checkGroupList;
    }

}
