package com.kt.yoon.controller;

import com.kt.yoon.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {

    public final MailService mailService;

    @RequestMapping("/send")
    @ResponseBody
    public String sendMail(){
        mailService.sendMail();
        return "send";
    }

    //todo 한명에게 메일 보내기 ; sheetId,userMail

    //todo 전체에게 메일 보내기 ; sheetId

    //todo 미응답자에게 메일 보내기 ; sheetId

    //todo 비밀번호 찾기 메일 보내기 ; userMail
}
