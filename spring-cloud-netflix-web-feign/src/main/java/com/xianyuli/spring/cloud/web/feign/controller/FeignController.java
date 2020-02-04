package com.xianyuli.spring.cloud.web.feign.controller;

import com.xianyuli.spring.cloud.web.feign.feignconfig.AdminFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    AdminFeign adminFeign;

    @GetMapping("say/{message}")
    public String say(@PathVariable("message") String message) {
        return adminFeign.hi(message);
    }

}
