package com.xianyuli.spring.cloud.admin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceAdminController {

    @Value("${server.port}")
    private String port;

    @GetMapping("hi")
    public String hi(@RequestParam(value = "message") String message) {
        return String.format("Hi，you message is %s ，port : %s", message, port);
    }

}
