package com.kt.yoon.controller;

import com.kt.yoon.domain.JsonResponse;
import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.MemberSheet;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.form.SheetForm;
import com.kt.yoon.service.MemberService;
import com.kt.yoon.service.SheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Api(tags = {"2.Sheet"})
@ResponseBody
public class SheetController {

    private final SheetService sheetService;
    private final MemberService memberService;

    @ApiOperation(value = "Sheet 추가")
    @PostMapping("/sheets/new")
    public JsonResponse addSheet(@Valid @RequestBody SheetForm sheetForm) {
        Member createdMember = memberService.findById(Long.parseLong(sheetForm.getCreatedMemberId()));
        List<Member> memberList = new ArrayList<>();
        for (Member member : sheetForm.getMemberList()) {
            memberList.add(memberService.findById(member.getId()));
        }
        sheetService.save(Sheet.createSheet(createdMember, sheetForm, memberList));
        return new JsonResponse(200, "success");
    }

    @ApiOperation(value = "사용자 Sheet 조회")
    @GetMapping("/sheets/users/{userId}")
    public JsonResponse getSheetListByUserId(@PathVariable String userId, @RequestParam("offset") int offset, @RequestParam("limit") int limit, @RequestParam("type") String type, Authentication authentication) {
        checkUserCanAccess(userId, authentication);
        List<Sheet> sheetList = sheetService.getSheetByUserId(Long.parseLong(userId), offset, limit, type);
        return new JsonResponse(200, "success", sheetList, userId);
    }

    @ApiOperation(value = "Sheet id 값으로 조회")
    @GetMapping("/sheets/{sheetId}")
    public JSONObject getSheetDetail(@PathVariable String sheetId, Authentication authentication) {
        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
        Member member = sheet.getCreatedMember();
        checkUserCanAccess("" + member.getId(), authentication);
        List<MemberSheet> sheetDetailList = sheetService.getSheetDetail(sheet.getId());
        return new JsonResponse(200, "success", sheet, sheetDetailList);
    }

    @ApiOperation(value = "Sheet 시작")
    @GetMapping("/sheets/start/{sheetId}")
    public JsonResponse startSheet(@PathVariable String sheetId, Authentication authentication) {
        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
        Member member = sheet.getCreatedMember();
        checkUserCanAccess("" + member.getId(), authentication);
        sheetService.startSheet(Long.parseLong(sheetId));
        return new JsonResponse(200, "success");
    }

    @ApiOperation(value = "Sheet 종료")
    @GetMapping("/sheets/end/{sheetId}")
    public JsonResponse endSheet(@PathVariable String sheetId, Authentication authentication) {
        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
        Member member = sheet.getCreatedMember();
        checkUserCanAccess("" + member.getId(), authentication);
        sheetService.endSheet(Long.parseLong(sheetId));
        return new JsonResponse(200, "success");
    }

    public boolean checkUserCanAccess(String userId, Authentication authentication) {
        //시트 권한 확인
        String requestUserEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        Member requestMember = memberService.findById(Long.parseLong(userId));
        if (!requestMember.getEmail().equals(requestUserEmail)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        return true;
    }
}
