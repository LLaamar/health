package com.itheima.service.impl.utils;

/*
import com.itheima.pojo.Setmeal;
import com.itheima.service.impl.SetmealServiceImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
*/

/**
 * 用于生成静态页面的工具类
 * @author LLaamar
 * @date 2020/9/13 14:34
 */
/*@Component*/
public class TemplateUtils {

/*

    static SetmealServiceImpl service;
    // 注入FreeMaker
    @Autowired
    static FreeMarkerConfigurer freeMarkerConfigurer;
    // 注入静态资源输出路径
    @Value("${out_put_path}")
    static String outPutPath;

    */
/**
     * 生成静态页面
     *//*

    public static void generateMobileStaticHtml() {

        List<Setmeal> setmealList = service.findAll();

        //生成套餐列表静态页面
        generateMobileSetmealListHtml(setmealList);
        //生成套餐详情静态页面（多个）
        generateMobileSetmealDetailHtml(setmealList);
    }

    */
/**
     * 生成套餐详情的静态页面
     * @param setmealList
     *//*

    public static void generateMobileSetmealDetailHtml(List<Setmeal> setmealList) {
        for (Setmeal setmeal : setmealList) {
            Map<String, Object> dataMap = new HashMap<String, Object>();

            dataMap.put("setmeal", service.findById(setmeal.getId()));

            generateHtml("mobile_setmeal_detail.ftl",
                    "setmeal_detail_"+setmeal.getId()+".html",
                    dataMap);
        }
    }

    */
/**
     * 生成套餐列表的静态资源
     * @param setmealList
     *//*

    public static void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("setmealList", setmealList);
        generateHtml("mobile_setmeal.ftl","mobile_setmeal.html",dataMap);
    }

    */
/**
     * 生成静态页面
     * @param templateName 使用的模板文件名
     * @param htmlPageName 生成的静态页面名
     * @param dataMap 数据
     *//*

    public static void generateHtml(String templateName, String htmlPageName, Map<String, Object> dataMap){
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

*/

}
