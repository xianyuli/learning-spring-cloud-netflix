# Learning-Spring-Cloud-Netflix
#### 1.概述
> 2018 年 12 月 12 日，Netflix 宣布 Spring Cloud Netflix 系列技术栈进入维护模式。  

虽然`Spring-Cloud-Netflix`已经进入维护模式不再更新功能，但学习`Spring-Cloud-Netflix`还是可以帮助开发者很好的理解微服务的概念，有助于开发者思维的转换，是一个很好的微服务入门框架。  
让我们简单的来学一下`SpringCloud Netflix`：  
在微服务架构中，需要几个基础的服务治理组件，包括注册与发现，服务消费，负载均衡，熔断器，智能路由，配置管理等；其对应`Spring-Cloud-Netflix`中的组件与技术:  

| 组件 | Spring-Cloud-Netflix |
| :------ | :------ |
| 注册与发现 | Spring-Cloud-Eureka  |
| 服务消费   | Spring-Cloud-Feign   |
| 负载均衡   | Spring-Cloud-Ribbon  |
| 熔断器     | Spring-Cloud-Hystrix |
| 智能路由   | Spring-Cloud-Zuul    |
| 配置管理   | Spring-Cloud-Config  |
#### 2.架构及其原理
一个简单的微服务系统如下图：  
![](https://github.com/xianyuli/learning-spring-cloud-netflix/blob/master/screenshots/springcloudnetflix.png)