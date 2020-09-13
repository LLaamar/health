package com.itheima.service.utils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
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

/**
 * 用于生成静态页面的工具类
 *
 * @author LLaamar
 * @date 2020/9/13 14:34
 */
@Component
public class TemplateUtils {

    @Reference
    static SetmealService service;

    static String outPutPath = "D:/DevelopFile/health/health_parent/health_mobile/src/main/webapp/pages";

    /**
     * 生成静态页面
     */
    public static void generateMobileStaticHtml() {

        List<Setmeal> setmealList = service.findAll();

        //生成套餐列表静态页面
        generateMobileSetmealListHtml(setmealList);
        //生成套餐详情静态页面（多个）
        generateMobileSetmealDetailHtml(setmealList);
    }

    /**
     * 生成套餐详情的静态页面
     * @param setmealList
     */
    public static void generateMobileSetmealDetailHtml(List<Setmeal> setmealList) {
        for (Setmeal setmeal : setmealList) {
            Map<String, Object> dataMap = new HashMap<>();

            dataMap.put("setmeal", service.findById(setmeal.getId()));

            generateHtml("mobile_setmeal_detail.ftl",
                    "setmeal_detail_" + setmeal.getId() + ".html",
                    dataMap);
        }
    }

    /**
     * 生成套餐列表的静态资源
     * @param setmealList
     */
    public static void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("setmealList", setmealList);
        generateHtml("mobile_setmeal.ftl", "mobile_setmeal.html", dataMap);
    }

    /**
     * 生成静态页面
     *
     * @param templateName 使用的模板文件名
     * @param htmlPageName 生成的静态页面名
     * @param dataMap      数据
     */
    public static void generateHtml(String templateName, String htmlPageName, Map<String, Object> dataMap) {

        Configuration configuration=new Configuration(Configuration.getVersion());
        Writer out = null;
        try{
            //2.设置模板所在的目录
            String path = "D:/DevelopFile/health/health_parent/health_service_provider/src/main/webapp/WEB-INF/ftl";
            configuration.setDirectoryForTemplateLoading(new File(path));

            //3.设置字符集
            configuration.setDefaultEncoding("utf-8");

            Template template = configuration.getTemplate(templateName);
            File file = new File(outPutPath + "/" + htmlPageName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            // 输出文件
            template.process(dataMap, out);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
