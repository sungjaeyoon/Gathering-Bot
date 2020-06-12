package com.kt.yoon.controller;

import com.kt.yoon.config.JwtTokenProvider;
import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.form.MemberForm;
import com.kt.yoon.exception.CommonException;
import com.kt.yoon.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(tags = {"1.User"})
@Controller
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "회원 등록", notes = "회원을 추가한다.")
    @PostMapping("/signup")
    @ResponseBody
    public JSONObject addMember(@Valid @RequestBody MemberForm memberForm, BindingResult bindingResult) {
        JSONObject jsonObject = new JSONObject();

        try {
            if (CommonException.bindResultException(bindingResult, jsonObject)) return jsonObject;

            //PasswordEncode & save
            memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
            memberService.save(Member.createMember(memberForm));
            jsonObject.put("status", 200);
            jsonObject.put("message", "success");
        } catch (IllegalStateException e) {
            //duplicate error
            jsonObject.put("status", 400);
            jsonObject.put("message", "이미 가입된 회원입니다.");
        } catch (Exception e) {
            // server error
            jsonObject.put("status", 500);
            jsonObject.put("message", "서버 에러");
        }
        return jsonObject;
    }

    @ApiOperation(value = "로그인", notes = "로그인 API")
    @PostMapping("/login")
    @ResponseBody
    public JSONObject login(@RequestBody Map<String, String> user) {
        JSONObject jsonObject = new JSONObject();
        try {
            Member member = memberService.login(user);
            jsonObject.put("status", 200);
            jsonObject.put("id", member.getId());
            jsonObject.put("email", member.getEmail());
            jsonObject.put("username", member.getName());
            jsonObject.put("teamName", member.getTeamName());
            jsonObject.put("position", member.getPosition());
            jsonObject.put("token", jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
        } catch (IllegalArgumentException e) {
            // failed login
            jsonObject.put("status", 400);
            jsonObject.put("message", e.getMessage());
        } catch (Exception e) {
            // server error
            jsonObject.put("status", 500);
            jsonObject.put("message", "서버 에러");
        }
        return jsonObject;
    }

    @ApiOperation(value = "회원 목록", notes = "모든 회원을 리스트를 반환 한다.")
    @GetMapping(value = "/users")
    @ResponseBody
    public JSONObject memberList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
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
        }catch (Exception e){
            jsonObject.put("status", 500);
            jsonObject.put("message","server error");
        }
        return jsonObject;
    }

    @ApiOperation(value = "email 중복 체크", notes = "이메일을 중복체크한다.")
    @GetMapping(value = "/check/{email}")
    @ResponseBody
    public boolean checkDuplicateEmail(@PathVariable String email) {
        try {
            memberService.findMemberByEmail(email);
            return false;
        } catch (IllegalStateException e) {
            return true;
        }
    }


}
