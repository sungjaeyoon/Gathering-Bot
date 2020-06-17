package com.kt.yoon.controller;

import com.kt.yoon.config.JwtTokenProvider;
import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.form.MemberForm;
import com.kt.yoon.exception.GlobalException;
import com.kt.yoon.exception.InvalidEmailException;
import com.kt.yoon.exception.InvalidPasswordException;
import com.kt.yoon.exception.JsonErrorResponse;
import com.kt.yoon.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(tags = {"1.User"})
@Controller
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "회원 등록", notes = "회원을 추가한다.")
    @PostMapping("/signup")
    @ResponseBody
    public JSONObject addMember(@RequestBody @Valid MemberForm memberForm) throws Exception {

        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
        memberService.save(Member.createMember(memberForm));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        jsonObject.put("message", "success");

        return jsonObject;
    }

    @ApiOperation(value = "로그인", notes = "로그인 API")
    @PostMapping("/login")
    @ResponseBody
    public JSONObject login(@RequestBody Map<String, String> user) throws Exception {
        JSONObject jsonObject = new JSONObject();

        Member member = memberService.login(user);
        jsonObject.put("status", 200);
        jsonObject.put("id", member.getId());
        jsonObject.put("email", member.getEmail());
        jsonObject.put("username", member.getName());
        jsonObject.put("teamName", member.getTeamName());
        jsonObject.put("position", member.getPosition());
        jsonObject.put("token", jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
        log.info("로그인  성공");

        return jsonObject;
    }

    @ApiOperation(value = "회원 목록", notes = "모든 회원을 리스트를 반환 한다.")
    @GetMapping(value = "/users")
    @ResponseBody
    public JSONObject memberList() throws Exception {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        List<Member> memberList = memberService.findMembers();
        for (Member member : memberList) {
            JSONObject jsonMember = new JSONObject();
            jsonMember.put("id", member.getId());
            jsonMember.put("name", member.getName());
            jsonMember.put("email", member.getEmail());
            jsonMember.put("position", member.getPosition());
            jsonMember.put("teamName", member.getTeamName());
            jsonArray.add(jsonMember);
        }
        jsonObject.put("users", jsonArray);
        jsonObject.put("status", 200);

        return jsonObject;
    }

    @ApiOperation(value = "email 중복 체크", notes = "이메일을 중복체크한다.")
    @GetMapping(value = "/check/{email}")
    @ResponseBody
    public boolean checkDuplicateEmail(@PathVariable String email) {
        try {
            memberService.findMemberByEmail(email);
            return false;
        } catch (EntityNotFoundException e) {
            return true;
        }
    }


}
