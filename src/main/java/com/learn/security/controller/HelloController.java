package com.learn.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(method = RequestMethod.GET, path = "/api/public/hello")
    public String publicHello(){
        return "Public Hello";
    }

    @GetMapping("/api/hello")
    public String apiHello(){
        return "secured hello";
    }

    @GetMapping("api/admin/hello")
    public String adminHello(){
        return "admin hello";
    }


}
