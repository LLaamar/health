package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.CheckItemDao;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LLaamar
 * @date 2020/9/9 16:24
 */
@Service(interfaceClass = SetmealService.class) // 使用了事务之后要指定该类实现的接口
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /*

    @Autowired
    private CheckGroupDao checkGroupDao;
    @Autowired
    private CheckItemDao checkItemDao;

    */

    // 注入JedisPool
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        // 获取操作redis的Jedis对象
        Jedis resource = jedisPool.getResource();
        // 将确定要添加的图片名添加到redis数据库的set中
        // 注: setmeal.getImg()获取的是图片的存储路径
        //  this.imageUrl = "http://qgdch5zua.hd-bkt.clouddn.com/" + response.data;
        if (setmeal.getImg() != null){
            resource.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES , setmeal.getImg());
        }

        System.out.println("__________获取确定存储的文件名____________");
        System.out.println(setmeal.getImg());
        System.out.println("__________获取确定存储的文件名____________");

        if(checkgroupIds != null && checkgroupIds.length > 0){
            setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        }
    }

    private void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkgroupIds) {
        for (Integer checkgroupId : checkgroupIds) {
            setmealDao.setSetmealAndCheckGroup(setmealId,checkgroupId);
        }
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.pageQuery(queryString);
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());

        return pageResult;
    }

    @Override
    public Setmeal findById(Integer setmealId) {
        Setmeal setmeal = setmealDao.findById(setmealId);

/*
        // 查询套餐包含的检查组信息
        List<Integer> checkgroupIds = setmealDao.findCheckgroupIdsBySetmealId(setmealId);
        List<CheckGroup> checkGroupList = new ArrayList<>();
        List<CheckItem> checkItemList = new ArrayList<>();
        for (Integer checkgroupId : checkgroupIds) {
            // 遍历检查组id,根据检查组id查询检查组
            CheckGroup checkGroup = checkGroupDao.findById(checkgroupId);
            List<Integer> checkItemIds = checkGroupDao.findCheckItemIdsByCheckGroupId(checkgroupId);
            for (Integer checkItemId : checkItemIds) {
                // 遍历检查项id,根据检查项id查询检查项
                CheckItem checkItem = checkItemDao.findById(checkItemId);
                checkItemList.add(checkItem);
            }
            // 完善套餐中的检查组中的检查项信息
            checkGroup.setCheckItems(checkItemList);

            checkGroupList.add(checkGroup);
        }
        // 完成套餐中的检查组信息
        setmeal.setCheckGroups(checkGroupList);*/
        return setmeal;
    }

    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(Integer setmealId) {
        List<Integer> checkGroupIds = setmealDao.findCheckgroupIdsBySetmealId(setmealId);
        return checkGroupIds;
    }

    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        // 1.删除套餐和检查组之间的关系
        Integer setmealId = setmeal.getId();
        setmealDao.deleteAssociation(setmealId);
        // 修改关系表中的数据
        if (checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.setSetmealAndCheckGroup(setmealId,checkgroupId);
            }
        }
        // 套餐中的图片名和原来的相比是否改变了
        // 改变了就说明用户点击了修改图片，就要将图片保存到【确定上传】的redis数据库
        String fileName = setmeal.getImg();
        System.out.println(fileName);
        System.out.println("================");
        System.out.println("修改方法执行");
        System.out.println("================");

        Jedis resource = jedisPool.getResource();
        if(fileName != null){
            resource.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES , fileName);
        }
        // 3.修改套餐信息
        setmealDao.edit(setmeal);
    }

    @Override
    public void delete(Integer id) {
        // 1.删除图片
        Setmeal setmeal = findById(id);
        String fileName = setmeal.getImg();
        if(fileName != null){
            QiniuUtils.deleteFileFromQiniu(fileName);
        }
        // 2.删除套餐和检查组的关系
        setmealDao.deleteAssociation(id);
        // 3.删除套餐表中的套餐数据
        setmealDao.delete(id);
    }

    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> setmealList =  setmealDao.findAll();
        return setmealList;
    }
}
