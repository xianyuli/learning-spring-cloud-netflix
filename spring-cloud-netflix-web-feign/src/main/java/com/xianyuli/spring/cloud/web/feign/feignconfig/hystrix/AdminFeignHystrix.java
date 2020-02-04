package com.xianyuli.spring.cloud.web.feign.feignconfig.hystrix;

import com.xianyuli.spring.cloud.web.feign.feignconfig.AdminFeign;
import org.springframework.stereotype.Component;

@Component
public class AdminFeignHystrix implements AdminFeign {
    @Override
    public String hi(String message) {
        return String.format("Hi，your message is %s，but request bad", message);
    }
}
