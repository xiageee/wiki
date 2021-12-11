package com.xiage.wiki.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

//    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World!";
    }
}
