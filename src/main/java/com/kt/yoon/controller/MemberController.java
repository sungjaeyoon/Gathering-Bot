package com.kt.yoon.controller;

import com.kt.yoon.domain.Member;
import com.kt.yoon.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String addMember(){
        Member member1 = Member.createMember("윤성재","사원","sungjae.yoon@kt.com","인증플랫폼","");
        memberService.save(member1);
        int list[] = new int[10];
        return "index";
    }
    
    @GetMapping("/list")
    public String memberList(){
        return "list";
    }

    @GetMapping("/list")
    public String memberList(){
        List<Member> memberList = memberService.findMembers();
        for(Member m:memberList){
            System.out.println(m.getMemberName()+" "+m.getEmail());
        }
        return "";
    }


}
