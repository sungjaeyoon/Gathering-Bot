package com.kt.yoon.controller;

import com.kt.yoon.config.JwtTokenProvider;
import com.kt.yoon.domain.JsonResponse;
import com.kt.yoon.domain.User;
import com.kt.yoon.domain.form.LoginForm;
import com.kt.yoon.domain.form.PasswordUserForm;
import com.kt.yoon.domain.form.UserForm;
import com.kt.yoon.service.UserService;
import com.kt.yoon.utils.CheckAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Api(tags = {"1.User"})
@Controller
@RequiredArgsConstructor
@ResponseBody
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "회원 가입 API")
    @PostMapping("/api/signup")
    public JsonResponse signUpMember(@RequestBody @Valid UserForm userForm) throws Exception {
        userService.signUpUser(userForm);
        log.info("New User Signup - User Email: " + userForm.getEmail());
        return new JsonResponse(200, "success");
    }

    @ApiOperation(value = "여러명 회원가입 API")
    @PostMapping("/api/signup/list")
    public JsonResponse signUpMemberList(@RequestBody @Valid UserForm[] userForms) throws Exception {
        for (UserForm userForm : userForms) {
            userService.signUpUser(userForm);
            log.info("New User Signup - User Email: " + userForm.getEmail());
        }
        return new JsonResponse(200, "success");
    }

    @ApiOperation(value = "로그인 API")
    @PostMapping("/api/login")
    public JsonResponse loginMember(@RequestBody @Valid LoginForm loginForm) throws Exception {
        User user = userService.login(loginForm);
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        log.info("User Login - User Email : " + loginForm.getEmail());
        return JsonResponse.userInfo(200, user, token);
    }

    @ApiOperation(value = "회원 정보 변경 API")
    @PostMapping("/api/users/update")
    public JsonResponse updateMemberInfo(@RequestBody @Valid PasswordUserForm passwordUserForm, Authentication authentication) throws Exception {
        CheckAuth.checkUserCanAccess(passwordUserForm.getEmail(), authentication);
        userService.changeUserPassword(passwordUserForm);
        log.info("User change Password - User Email: " + passwordUserForm.getEmail());
        return new JsonResponse(200, "success");
    }
}
