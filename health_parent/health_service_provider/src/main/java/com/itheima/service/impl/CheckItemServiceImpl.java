package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LLaamar
 * @date 2020/9/7 10:27
 */
@Service(interfaceClass = CheckItemService.class) // 加了事务注解后,必须声明这个实现类所实现的接口
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
//        checkItemDao.add(checkItem);
        checkItemDao.add(checkItem);
    }


    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
//        List<CheckItem> checkItemList = checkItemDao.selectByCondition(queryString);
//        PageInfo<CheckItem> page = new PageInfo<>(checkItemList);
//        PageResult pageResult = new PageResult(page.getTotal(),page.getList());
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());

        return pageResult;
    }

    @Override
    public void delete(Integer checkItemId) {
        /*
            由于检查项和检查组之间有关联关系,所以不能直接删除
            判断该检查项是否关联了检查组
            只需查询中间表"t_checkgroup_checkitem"中是否能根据检查项id查出检查组id
         */
        Long count = checkItemDao.findCountByCheckItemId(checkItemId);

        if(count > 0){
            // 有关联的检查组,不能删除
            throw new RuntimeException("当前检查项被引用，不能删除");
        }else{
            // 没有关联的检查组,可以删除
            checkItemDao.deleteById(checkItemId);
        }

    }

    @Override
    public CheckItem findById(Integer checkItemId) {
        CheckItem checkItem =  checkItemDao.findById(checkItemId);
        return checkItem;
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItemList = checkItemDao.findAll();
        return checkItemList;
    }


}
