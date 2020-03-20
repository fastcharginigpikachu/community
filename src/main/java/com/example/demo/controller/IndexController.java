package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller//识别成bean去管理，识别为“可以接收前端请求的controller”
public class IndexController {
    @GetMapping("/")//
    public String Hello() {
        return "index";//执行到这里，去寻找resource/templates/index.html
    }
}
