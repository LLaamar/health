package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.service.impl.utils.QiniuUtils;

import com.itheima.service.utils.TemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LLaamar
 * @date 2020/9/9 16:24
 */
@Service(interfaceClass = SetmealService.class) // 使用了事务之后要指定该类实现的接口
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;
    // 注入JedisPool
    @Autowired
    private JedisPool jedisPool;
    // 注入FreeMaker
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    // 注入静态资源输出路径
    @Value("${out_put_path}")
    private String outPutPath;

    /*
    以下的注入是在移动端加载套餐的详情页面时,通过循环的单表查询来封装数据
    后期改为在配置文件中使用映射ResultMap关系
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Autowired
    private CheckItemDao checkItemDao;
    */


    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {

        // 获取操作redis的Jedis对象
        Jedis resource = jedisPool.getResource();
        // 将确定要添加的图片名添加到redis数据库的set中
        // 注: setmeal.getImg()获取的是图片的存储路径
        //  this.imageUrl = "http://qgdch5zua.hd-bkt.clouddn.com/" + response.data;

        if (setmeal.getImg() != null){
            resource.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES , setmeal.getImg());
        }else{
            setmeal.setImg("4fec313402492addf899db4b6f29122a.jpeg");
        }

        setmealDao.add(setmeal);

        /*
        控制台调试用的
        System.out.println("__________获取确定存储的文件名____________");
        System.out.println(setmeal.getImg());
        System.out.println("__________获取确定存储的文件名____________");
        */
        if(checkgroupIds != null && checkgroupIds.length > 0){
            setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        }

        // 重新生成静态页面
        generateMobileStaticHtml();
    }

     /**
     * 生成静态页面
     */
    private void generateMobileStaticHtml() {
        List<Setmeal> setmealList = setmealDao.findAll();

        //生成套餐列表静态页面
        generateMobileSetmealListHtml(setmealList);
        //生成套餐详情静态页面（多个）
        generateMobileSetmealDetailHtml(setmealList);
    }

    private void generateMobileSetmealDetailHtml(List<Setmeal> setmealList) {
        for (Setmeal setmeal : setmealList) {
            Map<String, Object> dataMap = new HashMap<>();

            dataMap.put("setmeal", this.findById(setmeal.getId()));
            this.generateHtml("mobile_setmeal_detail.ftl",
                    "setmeal_detail_"+setmeal.getId()+".html",
                    dataMap);
        }
    }

    private void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("setmealList", setmealList);
        this.generateHtml("mobile_setmeal.ftl","m_setmeal.html",dataMap);
    }

        /**
         * 生成静态页面
         * @param templateName 使用的模板文件名
         * @param htmlPageName 生成的静态页面名
         * @param dataMap 数据
         */

    private void generateHtml(String templateName, String htmlPageName, Map<String, Object> dataMap){
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try{
            Template template = configuration.getTemplate(templateName);
            File file = new File(outPutPath + "/" + htmlPageName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            // 输出文件
            template.process(dataMap, out);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        }else{
            setmeal.setImg("4fec313402492addf899db4b6f29122a.jpeg");
        }

        // 3.修改套餐信息
        setmealDao.edit(setmeal);

        // 重新生成静态页面
        generateMobileStaticHtml();

        /*try {
            TemplateUtils.generateMobileStaticHtml();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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

        // 重新生成静态页面
        generateMobileStaticHtml();

    }

    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> setmealList =  setmealDao.findAll();
        return setmealList;
    }
}
