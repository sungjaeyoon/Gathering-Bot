package com.kt.yoon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/create")
public class CreateController {

    @RequestMapping(path = "/ox")
    public String addOxForm(){
        return "create-ox";
    }

    @RequestMapping(path = "/table")
    public String addTableForm(){
        return "create-table";
    }

}
