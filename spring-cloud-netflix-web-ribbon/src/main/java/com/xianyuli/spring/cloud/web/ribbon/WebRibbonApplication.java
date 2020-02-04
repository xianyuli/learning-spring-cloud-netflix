package com.xianyuli.spring.cloud.web.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient  //因为项目引入了eureka-client,这里可以不写@EnableEurekaClient
public class WebRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebRibbonApplication.class, args);
    }

}
