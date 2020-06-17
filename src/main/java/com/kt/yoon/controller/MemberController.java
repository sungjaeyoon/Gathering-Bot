package com.kt.yoon.controller;

import com.kt.yoon.config.JwtTokenProvider;
import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.form.MemberForm;
import com.kt.yoon.exception.CommonException;
import com.kt.yoon.exception.JsonErrorResponse;
import com.kt.yoon.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public JSONObject addMember(@Valid @RequestBody MemberForm memberForm, BindingResult bindingResult) {
        log.info("회원가입 시작");
        JSONObject jsonObject = new JSONObject();

        try {
            //binding error
            if (CommonException.bindResultException(bindingResult, jsonObject)) return jsonObject;

            //PasswordEncode & save
            memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
            memberService.save(Member.createMember(memberForm));
            jsonObject.put("status", 200);
            jsonObject.put("message", "success");
            log.info("회원가입 완료");
        } catch (IllegalStateException e) {
            //duplicate error
            log.warn("회원가입 실패");
            return new JsonErrorResponse(400,"이미 가입된 회원입니다.").getJsonObject();
        } catch (Exception e) {
            // server error
            log.warn("회원가입 실패:"+ e.getMessage());
            return new JsonErrorResponse(404,"에러").getJsonObject();
        }
        return jsonObject;
    }

    @ApiOperation(value = "로그인", notes = "로그인 API")
    @PostMapping("/login")
    @ResponseBody
    public JSONObject login(@RequestBody Map<String, String> user) {
        log.info("로그인 시작: "+user.get("email"));
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
            log.info("로그인  성공");
        } catch (IllegalArgumentException e) {
            // failed login
            log.info("로그인 실패");
            return new JsonErrorResponse(400,e.getMessage()).getJsonObject();
        } catch (Exception e) {
            // server error
            log.info("로그인 실패");
            return new JsonErrorResponse(404,"에러").getJsonObject();
        }
        return jsonObject;
    }

    @ApiOperation(value = "회원 목록", notes = "모든 회원을 리스트를 반환 한다.")
    @GetMapping(value = "/users")
    @ResponseBody
    public JSONObject memberList() {
        log.info("회원 목록 조회 시작");
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
            // server error
            return new JsonErrorResponse(404,"에러").getJsonObject();
        }
        return jsonObject;
    }

    @ApiOperation(value = "email 중복 체크", notes = "이메일을 중복체크한다.")
    @GetMapping(value = "/check/{email}")
    @ResponseBody
    public boolean checkDuplicateEmail(@PathVariable String email) {
        log.info("이메일 중복체크 시작:"+email);
        try {
            memberService.findMemberByEmail(email);
            log.info("사용할 수 없는 이메일");
            return false;
        } catch (IllegalStateException e) {
            log.info("사용할 수 있는 이메일");
            return true;
        }
    }


}
