package com.kt.yoon.controller;

import com.kt.yoon.domain.JsonResponse;
import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.MemberSheet;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.form.ResponseForm;
import com.kt.yoon.service.MemberService;
import com.kt.yoon.service.MemberSheetService;
import com.kt.yoon.service.SheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@Api(tags = {"3.Response"})
@Controller
@RequiredArgsConstructor
@ResponseBody
public class ResponseController {

    private final SheetService sheetService;
    private final MemberService memberService;
    private final MemberSheetService memberSheetService;

    @ApiOperation(value = "응답 화면 데이터 조회")
    @GetMapping("/response/{sheetId}/{userId}/{randomToken}")
    public JsonResponse getSheetById(@PathVariable String sheetId, @PathVariable String userId, @PathVariable String randomToken) throws Exception{
        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
        Member member = memberService.findById(Long.parseLong(userId));
        MemberSheet memberSheet = memberSheetService.findMemberSheet(Long.parseLong(userId), Long.parseLong(sheetId));
        if(!memberSheet.getRandomToken().equals(randomToken)){
            throw new AccessDeniedException("권한이 없습니다.");
        }

        return new JsonResponse(200, "success", sheet, member);
    }

    @ApiOperation(value = "응답 화면 데이터 저장")
    @PostMapping("/response/result")
    public JsonResponse saveResponse(@RequestBody @Valid ResponseForm responseForm) throws Exception{
        MemberSheet memberSheet = memberSheetService.findMemberSheet(Long.parseLong(responseForm.getMemberId()), Long.parseLong(responseForm.getSheetId()));
        if(!memberSheet.getRandomToken().equals(responseForm.getRandomToken())){
            throw new AccessDeniedException("권한이 없습니다.");
        }
        memberSheetService.updateMemberSheet(Long.parseLong(responseForm.getSheetId()), Long.parseLong(responseForm.getMemberId()), responseForm.getResponse());
        return new JsonResponse(200,"success");
    }
}
