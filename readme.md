## 健康管理项目

### 1)项目概述



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



