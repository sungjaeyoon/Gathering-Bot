package com.kt.yoon.controller;

import com.kt.yoon.config.JwtTokenProvider;
import com.kt.yoon.domain.JsonResponse;
import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.form.LoginForm;
import com.kt.yoon.domain.form.MemberForm;
import com.kt.yoon.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Api(tags = {"1.User"})
@Controller
@RequiredArgsConstructor
@ResponseBody
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "회원 가입")
    @PostMapping("/signup")
    public JsonResponse addMember(@RequestBody @Valid MemberForm memberForm) {
        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
        memberService.save(Member.createMember(memberForm));
        return new JsonResponse(200, "success");
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public JsonResponse login(@RequestBody @Valid LoginForm loginForm) throws Exception {
        Member member = memberService.login(loginForm);
        return new JsonResponse(200, "success", member, jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
    }

    @ApiOperation(value = "전체 회원 목록 조회")
    @GetMapping(value = "/users")
    public JsonResponse memberList(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<Member> memberList = memberService.findMembers();
        return new JsonResponse(200, "success", memberList);
    }

    @ApiOperation(value = "email 중복 체크")
    @GetMapping(value = "/check/{email}")
    public boolean checkDuplicateEmail(@PathVariable String email) {
        try {
            memberService.findMemberByEmail(email);
            return false;
        } catch (EntityNotFoundException e) {
            return true;
        }
    }


}
