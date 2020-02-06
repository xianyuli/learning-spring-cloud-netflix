# Learning-Spring-Cloud-Netflix
#### 1.概述
> 2018 年 12 月 12 日，Netflix 宣布 Spring Cloud Netflix 系列技术栈进入维护模式。  

虽然`Spring-Cloud-Netflix`已经进入维护模式不再更新功能，但学习`Spring-Cloud-Netflix`还是可以帮助开发者很好的理解微服务的概念，有助于开发者思维的转换，是一个很好的微服务入门框架。  
让我们简单的来学一下`SpringCloud Netflix`：  
在微服务架构中，需要几个基础的服务治理组件，包括配置管理，注册与发现，智能路由，服务消费，负载均衡，熔断器等；其对应`Spring-Cloud-Netflix`中的组件与技术:  

| 组件 | Spring-Cloud-Netflix |
| :------ | :------ |
| 配置管理   | Spring-Cloud-Config  |
| 注册与发现 | Spring-Cloud-Eureka  |
| 智能路由   | Spring-Cloud-Zuul    |
| 服务消费   | Spring-Cloud-Feign   |
| 负载均衡   | Spring-Cloud-Ribbon  |
| 熔断器     | Spring-Cloud-Hystrix |


#### 2.架构
一个简单的微服务系统如下图：  
![](https://github.com/xianyuli/learning-spring-cloud-netflix/blob/master/screenshots/springcloudnetflix.png)  
这是一个简单的流程，一般我们通过`Spring Cloud`构建的是分布式应用。首先客户端请求Nginx服务器，Nginx会负载到合适的`Zuul`网关服务，网关会根据访问的路径去“服务列表”中找到对应的服务。

一个服务通常是分布式部署多个服务实例，由`Ribbon`负载到合适的服务实例IP上。而服务与服务之间的内部通信都是通过`feign`远程调用来实现的，其原理也是查询“服务列表”，由`ribbon`来实现负载均衡。

以上的“服务列表”都是有`Eureka`来维护的，因为是一个分布式的应用，所以有一个配置中心来统一管理所有服务的配置文件。  

#### 3. 构建
Spring Cloud应用是以Spring Boot为基石构建的，主要步骤就是引入依赖，编写配置和添加注解。
- **Spring Cloud Eureka**  

引入依赖：
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```
配置文件bootstrap.yml
```
server:
  port: 8761

spring:
  application:
    name: spring-cloud-netflix-eureka
eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defulat-zone: http://${eureka.instance.hostname}:${server.port}/eureka/
```
启动类添加注解
```
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }

}
```
验证：访问 [http://localhost:8761](http://localhost:8761)
- **Spring Cloud Config**

引入依赖：
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-netflix-eureka-client</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```
配置文件bootstrap.yml
```
server:
  port: 8888
spring:
  application:
    name: spring-cloud-config
  cloud:
    config:
      label: master
      server:
        git:
          uri: https://github.com/xianyuli/learning-spring-cloud-netflix.git
          search-paths: repo
#注册到eureka          
eureka:
  client:
    service-url:
      default-zone: http://localhost:8761/eureka/
```
启动类添加注解
```
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }
}
```
验证：  访问 [http://localhost:8888/netflix-service-admin/dev/master](http://localhost:8888/netflix-service-admin/dev/master)  
说明：`server.port`是默认的`8888`，一般默认就行了。但是如果我们将端口号改成`8880`，其他服务虽然配置的`spring.config.uri`也是`8880`端口但在启动仍会报错，需要将配置文件名改成`bootstrap.yml`。其原因是：
> Spring Cloud有一个“引导上下文”的概念，这是主应用程序上下文（Application Context）的父上下文。引导上下文负责从配置服务器加载配置属性，以及解密外部配置文件中的属性。和主应用程序加载application.* (yml或properties)中的属性不同，引导上下文加载bootstrap.* 中的属性。配置在bootstrap.* 中的属性有更高的优先级，因此默认情况下它们不能被本地配置覆盖。  

 bootstrap.yml默认是不能被本地配置覆盖，但是我们有时候想自定义一些配置比如端口号，这时需要在相应的`config client`远程配置文件中添加：
 ```
 spring：
   cloud:
    config:
      allow-override: true
      override-none: true
 ```

- **Spring Cloud Zuul**  

引入依赖：
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```
配置文件bootstrap.yml
```
server:
  port: 80
spring:
  application:
    name: spring-cloud-netflix-zuul
  #配置项目本地配置优先于configserver配置
  cloud:
    config:
      allow-override: true
      override-none: true
zuul:
  routes:
    admin:
      path: /admin/**
      serviceId: spring-cloud-netflix-service-admin
    web-feign:
      path: /feign/**
      serviceId: spring-cloud-netflix-web-feign
    web-ribbon:
      path: /ribbon/**
      serviceId: spring-cloud-netflix-web-ribbon

eureka:
  client:
    service-url:
      default-zone: http://localhost:8761/eureka/
```
启动类添加注解
```
@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
```
此处的`bootstrap.yml`为本地配置，实际上因为运用了Spring Config Server，我们会用远程拉取的方式引入配置。
- **apps**

其他应用的搭建直接看源代码即可，大同小异。

微服务启动类的`@EnableEurekaClient`注解其实可以不添加，因为pom.xml中引入了`spring-cloud-netflix-eureka-client`,并且配置文件中配置了注册中心的地址。该服务在启动时会自动注册到`Eureka`。 