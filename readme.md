## 健康管理项目

### 1)项目概述

*一个练手的小小项目*

### 2)所用技术栈

- 前端技术栈

  ```markdown
  1.HTML5
  2.bootstrap
  3.ElementUI
  4.Vue.js
  5.ajax
  ```

- 分布式架构及权限校验

  ```markdown
  1.zookeeper
  2.dubbo
  3.Spring-MVC
  4.Spring-Security
  ```

- 分布式版本控制及报表

  ```markdown
  1.Git
  2.Apache POI(报表)
  3.Echarts(图形报表)
  ```

- 持久层技术栈

  ```markdown
  1.MyBatis
  2.MySQL
  ```

  

- 第三方服务支持

  ```markdown
  1.阿里云通讯(短信服务)
  2.七牛云服务(存储服务)
  3.微信开发平台(公众号)
  ```

### 3)功能架构

- 健康管理后台
  - 会员管理
  - 预约管理
  - 健康评估
  - 健康干预
- 健康管理前台
  - 会员管理
  - 在线预约
  - 体检报告

===================**dubbo**====================

- 服务层
  - 会员服务
  - 预约服务
  - 体检报告服务
  - 健康评估服务
  - 健康干预服务

==============================================

- 数据访问层
  - MySQL
### 4).搭建
- 拆分项目结构
````java
创建项目模块并且提前设置为子父类结构,方便后续做分布式和集群
1.health_parent(父工程)
2.health_common(公共的一些实体类数据)
3.health_interface(公共的一些接口)
4.health_backend(后台Controller层)
5.health_service_provider(Service层)    
````

### 5).业务逻辑



体检预约的业务逻辑:

```
发送验证码:
校验用户输入的手机号是否合规,合规之后发送ajax请求到后台通过工具类发送验证码

1.在前端页面校验数据,然后将数据发送到后台Controller层

Controller层中
1.取出【验证码】数据和【手机号】数据，用这两个数据,配合redis数据库校验用户输入的验证码,只有验证码校验通过,才能进行【体检预约】

2.用户的验证码校验通过后,将数据以【map】的形式发送到Service层,在Service层处理业务

3.Service层:
3.1 用户想要成功完成体检预约,必须要以下的条件满足
	- 用户想要预约的日期有体检服务
	- 预约的日期预约人数没有达到最大预约人数
3.2 满足了以上条件还要分情况讨论
-- 用户预约过
	如果用户预约过,那么这一次预约的时候用户不能预约同一个套餐
-- 没有预约过
	没有预约过,则可以进行预约,不出现异常则一定能预约成功
	
										------- 以上就是预约体检的简单业务逻辑



```





