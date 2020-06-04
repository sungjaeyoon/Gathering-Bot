package com.kt.yoon.controller;

import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.form.MemberForm;
import com.kt.yoon.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
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

    @ApiOperation(value = "회원 등록", notes = "회원을 추가한다.")
    @PostMapping("/signup")
    @ResponseBody
    public void addMember(@Valid @RequestBody MemberForm memberForm, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        memberService.save(Member.createMember(memberForm));
    }

    @ApiOperation(value = "회원 목록", notes = "모든 회원을 리스트를 반환 한다.")
    @CrossOrigin
    @GetMapping(value = "/users")
    @ResponseBody
    public List<HashMap<String, Object>> memberList() {
        List<Member> memberList = memberService.findMembers();

        List<HashMap<String, Object>> memberListMap = new ArrayList<>();
        int index = 1;
        for (Member member : memberList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("index", index++);
            map.put("name", member.getMemberName());
            map.put("email", member.getEmail());
            map.put("position", member.getPosition());
            map.put("teamName", member.getTeamName());
            memberListMap.add(map);
        }

        return memberListMap;
    }

}
