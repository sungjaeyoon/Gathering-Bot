package com.kt.yoon.controller;

import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.service.MailService;
import com.kt.yoon.service.MemberService;
import com.kt.yoon.service.SheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class MailController {

    public final MailService mailService;
    private final SheetService sheetService;
    private final MemberService memberService;

    //todo 전체에게 메일 보내기 ; sheetId
    @GetMapping("/send/{sheetId}")
    @ResponseBody
    public void sendMailAll(@PathVariable String sheetId){
        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
        List<Member> list = new ArrayList<>();
        Member member= memberService.findMemberByEmail("dbstjdwo1000@naver.com");
        list.add(member);
        mailService.sendMail(list,sheet);
    }

    //todo 한명에게 메일 보내기 ; sheetId,userMail
    @RequestMapping("/send")
    public void sendMailUser(String userId, String sheetId){
        
    }

    //todo 미응답자에게 메일 보내기 ; sheetId

}
