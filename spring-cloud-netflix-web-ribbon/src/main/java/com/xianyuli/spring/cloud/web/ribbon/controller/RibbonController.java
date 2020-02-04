package com.xianyuli.spring.cloud.web.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RibbonController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("say/{message}")
    public String sayHi(@PathVariable("message") String message) {
        return restTemplate.getForObject("http://spring-cloud-netflix-service-admin/hi?message=" + message, String.class);
    }

}
