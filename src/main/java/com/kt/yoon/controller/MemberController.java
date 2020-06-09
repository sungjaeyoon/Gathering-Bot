package com.kt.yoon.controller;

import com.kt.yoon.config.JwtTokenProvider;
import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.form.MemberForm;
import com.kt.yoon.repository.UserRepository;
import com.kt.yoon.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"1.User"})
@Controller
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {

    private final MemberService memberService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "회원 등록", notes = "회원을 추가한다.")
    @PostMapping("/signup")
    @ResponseBody
    public String addMember(@Valid @RequestBody MemberForm memberForm, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
        memberService.save(Member.createMember(memberForm));
        return "success";
    }

    @ApiOperation(value = "로그인", notes = "로그인 API")
    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody Map<String, String> user) {
        Member member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }

    @ApiOperation(value = "회원 목록", notes = "모든 회원을 리스트를 반환 한다.")
    @GetMapping(value = "/users")
    @ResponseBody
    public List<HashMap<String, Object>> memberList() {
        List<Member> memberList = memberService.findMembers();

        List<HashMap<String, Object>> memberListMap = new ArrayList<>();
        for (Member member : memberList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", member.getId());
            map.put("name", member.getMemberName());
            map.put("email", member.getEmail());
            map.put("position", member.getPosition());
            map.put("teamName", member.getTeamName());
            memberListMap.add(map);
        }
        return memberListMap;
    }


    @ApiOperation(value = "email 중복 체크", notes = "이메일을 중복체크한다.")
    @GetMapping(value = "/check/{email}")
    @ResponseBody
    public boolean checkDuplicateEmail(@PathVariable String email) {
        System.out.println(email);
        try{
            memberService.findMemberByEmail(email);
            return false;
        }catch (IllegalStateException e){
            return true;
        }
    }


}
