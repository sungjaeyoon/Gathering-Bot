package com.kt.yoon.controller;

import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.form.ResponseForm;
import com.kt.yoon.service.MemberService;
import com.kt.yoon.service.MemberSheetService;
import com.kt.yoon.service.SheetService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@CrossOrigin
public class ResponseController {

    private final SheetService sheetService;
    private final MemberService memberService;
    private final MemberSheetService memberSheetService;

    @GetMapping("/response/{sheetId}/{userId}")
    @ResponseBody
    public JSONObject getSheetById(@PathVariable String sheetId, @PathVariable String userId) {
        JSONObject jsonObject = new JSONObject();

        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
        Member member = memberService.findById(Long.parseLong(userId));
//            if(!sheet.getMemberSheetList().contains(member)){
//                jsonObject.put("status",400);
//                jsonObject.put("message","Bad Request");
//                return jsonObject;
//            }
        jsonObject.put("memberId", member.getId());
        jsonObject.put("name", member.getName());
        jsonObject.put("position", member.getPosition());
        jsonObject.put("teamName", member.getTeamName());
        jsonObject.put("content", sheet.getContent());
        jsonObject.put("question", sheet.getQuestion());
        jsonObject.put("example", sheet.getExample());
        jsonObject.put("status", 200);

        return jsonObject;
    }

    @PostMapping("/response/result")
    @ResponseBody
    public JSONObject saveResponse(@RequestBody @Valid ResponseForm responseForm) {
        JSONObject jsonObject = new JSONObject();

        memberSheetService.updateMemberSheet(Long.parseLong(responseForm.getSheetId()), Long.parseLong(responseForm.getMemberId()), responseForm.getResponse());

        return jsonObject;
    }
}
