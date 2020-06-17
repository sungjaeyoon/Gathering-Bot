package com.kt.yoon.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Api(tags = {"2.Sheet"})
@CrossOrigin
public class SheetController {

    private final SheetService sheetService;
    private final MemberService memberService;

    @ApiOperation(value = "Sheet 추가", notes = "Sheet를 추가한다.")
    @PostMapping("/sheets/new")
    @ResponseBody
    public JSONObject addSheet(@Valid @RequestBody SheetForm sheetForm) {
        JSONObject jsonObject = new JSONObject();

        Member createdMember = memberService.findById(Long.parseLong(sheetForm.getCreatedMemberId()));

        List<Member> memberList = new ArrayList<>();
        for (Member member : sheetForm.getMemberList()) {
            memberList.add(memberService.findById(member.getId()));
        }

        sheetService.save(Sheet.createSheet(createdMember, sheetForm, memberList));

        jsonObject.put("status", 200);
        jsonObject.put("message", "success");

        return jsonObject;
    }

    @ApiOperation(value = "사용자 sheet 조회", notes = "id 값의 Sheet를 조회한다.")
    @GetMapping("/sheets/users/{userId}")
    @ResponseBody
    public JSONObject getSheetListByUserId(@PathVariable String userId, @RequestParam("offset") int offset, @RequestParam("limit") int limit, @RequestParam("type") String type) {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        List<Sheet> sheetList = sheetService.getSheetByUserId(Long.parseLong(userId), offset, limit, type);

        for (Sheet sheet : sheetList) {
            JSONObject jsonObjectSheet = new JSONObject();
            jsonObjectSheet.put("id", sheet.getId());
            jsonObjectSheet.put("title", sheet.getTitle());
            jsonObjectSheet.put("content", sheet.getContent());
            jsonObjectSheet.put("question", sheet.getQuestion());
            jsonObjectSheet.put("createdDate", sheet.getCreatedDate());
            jsonObjectSheet.put("finishedDate", sheet.getFinishedDate());
            jsonObjectSheet.put("sheetStatus", sheet.getSheetStatus());
            jsonArray.add(jsonObjectSheet);
        }
        jsonObject.put("sheets", jsonArray);
        jsonObject.put("status", 200);

        return jsonObject;
    }

    @ApiOperation(value = "sheet id 값으로 조회", notes = "id값으로조회")
    @GetMapping("/sheets/{sheetId}")
    @ResponseBody
    public JSONObject getSheetDetail(@PathVariable String sheetId) {
        //todo 요청한 유저가 해당 권한이 있는지

        JSONObject jsonObject = new JSONObject();

        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
        List<MemberSheet> sheetDetailList = sheetService.getSheetDetail(sheet.getId());
        JSONObject jsonObjectSheet = new JSONObject();

        jsonObjectSheet.put("content", sheet.getContent());
        jsonObjectSheet.put("title", sheet.getTitle());
        jsonObjectSheet.put("question", sheet.getQuestion());
        jsonObjectSheet.put("example", sheet.getExample());
        jsonObjectSheet.put("colNum", sheet.getColNum());
        jsonObjectSheet.put("createdDate", sheet.getCreatedDate());
        jsonObjectSheet.put("status", sheet.getSheetStatus());
        jsonObject.put("sheet", jsonObjectSheet);

        JSONArray jsonArray = new JSONArray();
        for (MemberSheet memberSheet : sheetDetailList) {
            JSONObject jsonObjectMemberSheet = new JSONObject();
            jsonObjectMemberSheet.put("id", memberSheet.getId());
            jsonObjectMemberSheet.put("modifiedDate", memberSheet.getModifiedDate());
            jsonObjectMemberSheet.put("requestStatus", memberSheet.getRequestStatus());
            jsonObjectMemberSheet.put("response", memberSheet.getResponse());
            jsonObjectMemberSheet.put("responseDate", memberSheet.getResponseDate());
            jsonObjectMemberSheet.put("email", memberSheet.getMember().getEmail());
            jsonObjectMemberSheet.put("name", memberSheet.getMember().getName());
            jsonObjectMemberSheet.put("position", memberSheet.getMember().getPosition());
            jsonObjectMemberSheet.put("teamName", memberSheet.getMember().getTeamName());
            jsonArray.add(jsonObjectMemberSheet);
        }
        jsonObject.put("memberSheet", jsonArray);
        jsonObject.put("status", 200);

        return jsonObject;

    }

    @ApiOperation(value = "sheet 시작하기", notes = "sheet id 값으로 wait->proceeding")
    @GetMapping("/sheets/start/{sheetId}")
    @ResponseBody
    public JSONObject startSheet(@PathVariable String sheetId) {
        //todo 요청 유저가 시트 주인 인지
        JSONObject jsonObject = new JSONObject();
        sheetService.startSheet(Long.parseLong(sheetId));
        jsonObject.put("status", 200);
        return jsonObject;
    }

    @ApiOperation(value = "sheet 끝내기", notes = "sheet id 값으로 proceeding -> FISHISHED")
    @GetMapping("/sheets/end/{sheetId}")
    @ResponseBody
    public JSONObject endSheet(@PathVariable String sheetId) {
        //todo 요청 유저가 시트 주인 인지
        JSONObject jsonObject = new JSONObject();
        sheetService.endSheet(Long.parseLong(sheetId));
        jsonObject.put("status", 200);
        return jsonObject;
    }
}
