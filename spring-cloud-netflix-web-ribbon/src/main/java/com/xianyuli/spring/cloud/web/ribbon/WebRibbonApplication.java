package com.xianyuli.spring.cloud.web.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


//因为项目引入了eureka-client,这里可以不写@EnableEurekaClient
@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication
public class WebRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebRibbonApplication.class, args);
    }

}
