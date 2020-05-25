package com.kt.yoon.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.MemberForm;
import com.kt.yoon.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Api(tags = {"1.User"})
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String addMemberTest(){
        



        return "main";
    }

    /**
     * user list 반환
     * */
    @ApiOperation(value = "회원 목록",notes = "모든 회원을 조회한다.")
    @CrossOrigin
    @GetMapping(value = "/users")
    @ResponseBody
    public List<HashMap<String,Object>> memberList(){
        List<Member> memberList = memberService.findMembers();

        List<HashMap<String,Object>> memberListMap= new ArrayList<>();
        int index=1;
        for(Member member:memberList){
            HashMap<String, Object> map = new HashMap<>();
            map.put("index",index++);
            map.put("name",member.getMemberName());
            map.put("email",member.getEmail());
            map.put("position",member.getPosition());
            map.put("teamName",member.getTeamName());
            memberListMap.add(map);
        }

        return memberListMap;
    }

    /**
     * add one user
     * */
    @ResponseBody
    @PostMapping(value = "/users")
    public String addMember(@Valid MemberForm form, BindingResult result){
        if(result.hasErrors()){
            return "error\n"+result.toString();
        }
        Member member = new Member(form.getName(), form.getPosition(),form.getEmail(),form.getTeamName(),"");
        memberService.save(member);
        return "add member "+member.getMemberName();
    }

//    @GetMapping("/test")
//    @ResponseBody
//    public HashMap<String, Object> postmanTest(@RequestBody HashMap<String, Object> map){
//        map.put("phone", "0000-0000");
//        return map;
//    }


}
