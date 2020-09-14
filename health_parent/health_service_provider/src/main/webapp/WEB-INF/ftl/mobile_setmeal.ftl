<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0,user-scalable=no,minimal-ui">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../img/asset-favico.ico">
    <title>预约</title>
    <link rel="stylesheet" href="../css/page-health-order.css" />
</head>
<body data-spy="scroll" data-target="#myNavbar" data-offset="150">
<div class="app" id="app">
    <!-- 页面头部 -->
    <div class="top-header">
        <span class="f-left"><i class="icon-back" onclick="history.go(-1)"></i></span>
        <span class="center">传智健康</span>
        <span class="f-right"><i class="icon-more"></i></span>
    </div>
    <!-- 页面内容 -->
    <div class="contentBox">
        <div class="list-column1">
            <ul class="list">
                <#--ftl不能传入空值,所以要做判断-->
                <#if setmealList ??>
                    <#list setmealList as setmeal>
                        <li class="list-item">
                            <a class="link-page" href="setmeal_detail_${setmeal.id}.html">
                                <img class="img-object f-left"
                                     src="http://qgdch5zua.hd-bkt.clouddn.com/${setmeal.img!}"
                                        <#--
                                                                         src="http://qgdch5zua.hd-bkt.clouddn.com/a5727336-15f2-4816-b8d6-ad7b610aa75a.jpg"
                                        -->
                                     alt="">
                                <div class="item-body">

                                    <h4 class="ellipsis item-title">${setmeal.name}</h4>
                                     <#if setmeal.remark ??>
                                         <#if setmeal.remark = "">
                                             <p class="ellipsis-more item-desc">暂无数据,请等待管理员添加信息</p>
                                         <#else>
                                             <p class="ellipsis-more item-desc">${setmeal.remark}</p>
                                         </#if>
                                     <#else>
                                     </#if>
                                    <p class="item-keywords">
                                    <span>
                                        <#if setmeal.sex ??>
                                            <#if setmeal.sex ="">
                                                暂无数据
                                            <#else>
                                                    <#if setmeal.sex == '0'>
                                                        性别不限
                                                    <#else>
                                                        <#if setmeal.sex == '1'>
                                                            男
                                                        <#else>
                                                            女
                                                        </#if>
                                                    </#if>
                                            </#if>
                                        <#else>
                                            暂无数据
                                        </#if>

                                    </span>
                                        <#if setmeal.age ??>
                                            <#if setmeal.age = "">
                                            <span>0-0</span>
                                                <#else>
                                                    <span>${setmeal.age}</span>
                                            </#if>
                                        <#else>
                                            <span>0-0</span>
                                        </#if>

                                    </p>
                                </div>
                            </a>
                        </li>
                    </#list>
                    <#else>
                        <p class="ellipsis-more item-desc">暂无数据</p>
                </#if>

            </ul>
        </div>
    </div>
</div>
<!-- 页面 css js -->
<script src="../plugins/vue/vue.js"></script>
<script src="../plugins/vue/axios-0.18.0.js"></script>
</body>