package com.kt.yoon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MainController implements ErrorController {

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/error")
    public String redirectRoot() {
        return "index";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
