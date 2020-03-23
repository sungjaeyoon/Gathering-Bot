package com.kt.yoon.controller;

import com.kt.yoon.domain.Member;
import com.kt.yoon.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String addMember(){
        Member member1 = Member.createMember("윤성재","사원","sungjae.yoon@kt.com","인증플랫폼","");
        memberService.save(member1);
        return "index";
    }

}
