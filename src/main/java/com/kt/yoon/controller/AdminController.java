package com.kt.yoon.controller;

import com.kt.yoon.domain.JsonResponse;
import com.kt.yoon.domain.form.UserForm;
import com.kt.yoon.domain.form.UserUpdateForm;
import com.kt.yoon.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@Api(tags = {"ADMIN"})
@Controller
@RequiredArgsConstructor
@ResponseBody
@Slf4j
public class AdminController {

    private final UserService userService;
//    private final MemberService memberService;
//    private final SheetService sheetService;
//    private final MemberSheetService memberSheetService;
//
//
//    @ApiOperation(value = "유저 전체 조회")
//    @GetMapping("/admin/users")
//    public JsonResponse findAllMembers(){
//        return null;
//    }
//
    @ApiOperation(value = "유저 정보 변경")
    @PostMapping("/api/admin/users")
    public JsonResponse updateMemberInfo(@RequestBody @Valid UserUpdateForm userUpdateForm) throws Exception{

        if(!userUpdateForm.getAdminString().equals("yoonsungjae")){
            throw new AccessDeniedException("권한 없음");
        }
        userService.changeUserInfo(userUpdateForm);
        return new JsonResponse(200,"success");
    }
//
//    @ApiOperation(value = "유저 삭제")
//    @GetMapping("/admin/users/remove/{userId}")
//    public JsonResponse removeMember(@PathVariable String userId){
//        return null;
//    }
//
//    @ApiOperation(value = "전체 시트 조회")
//    @GetMapping("/admin/sheets")
//    public JsonResponse findAllSheets(){
//        return null;
//    }
//
//    @ApiOperation(value = "시트 상세 조회")
//    @GetMapping("/admin/sheets/{sheetId}")
//    public JsonResponse findSheetDetail(@PathVariable String sheetId){
//        return null;
//    }
//
//    @ApiOperation(value = "답변 전체 조회")
//    @GetMapping("/admin/memberSheets")
//    public JsonResponse findAllMemberSheets(){
//        return null;
//    }
}
