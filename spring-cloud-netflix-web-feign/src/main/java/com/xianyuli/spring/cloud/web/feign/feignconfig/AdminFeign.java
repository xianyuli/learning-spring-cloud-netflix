package com.xianyuli.spring.cloud.web.feign.feignconfig;

import com.xianyuli.spring.cloud.web.feign.feignconfig.hystrix.AdminFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "spring-cloud-netflix-service-admin",fallback = AdminFeignHystrix.class)
public interface AdminFeign {

    @GetMapping("hi")
    String hi(@RequestParam(value = "message") String message);

}
