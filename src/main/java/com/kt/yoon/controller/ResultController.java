package com.kt.yoon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ResultController {

    @RequestMapping(path = "/result")
    public String userResult(){
        return "result";
    }
}
