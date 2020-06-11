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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void addSheet(@Valid @RequestBody SheetForm sheetForm, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Member createdMember = memberService.findById(Long.parseLong(sheetForm.getCreatedMemberId()));
        List<Member> memberList = new ArrayList<>();

        for (Member member : sheetForm.getMemberList()) {
            memberList.add(memberService.findById(member.getId()));
        }

        sheetService.save(Sheet.createSheet(createdMember, sheetForm, memberList));
    }

    @ApiOperation(value = "사용자 sheet 조회", notes = "id 값의 Sheet를 조회한다.")
    @GetMapping("/sheets/users/{userId}")
    @ResponseBody
    public List<HashMap<String, Object>> getSheetListByUserId(@PathVariable String userId) {
        List<Sheet> sheetList = sheetService.getSheetByUserId(Long.parseLong(userId));
        List<HashMap<String, Object>> sheetListMap = new ArrayList<>();
        for (Sheet sheet : sheetList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", sheet.getId());
            map.put("title", sheet.getTitle());
            map.put("content", sheet.getContent());
            map.put("question", sheet.getQuestion());
            map.put("createdDate", sheet.getCreatedDate());
            map.put("finishedDate", sheet.getFinishedDate());
            map.put("sheetStatus", sheet.getSheetStatus());
            sheetListMap.add(map);
        }
        return sheetListMap;
    }

    @ApiOperation(value = "sheet id 값으로 조회", notes = "id값으로조회")
    @GetMapping("/sheets/{sheetId}")
    @ResponseBody
    public List<HashMap<String, Object>> getSheetDetail(@PathVariable String sheetId) {
        //todo 요청한 유저가 해당 권한이 있는지



        List<HashMap<String, Object>> sheetDetail = new ArrayList<>();

        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
        List<MemberSheet> sheetDetailList = sheetService.getSheetDetail(sheet.getId());

        HashMap<String, Object> sheetContent = new HashMap<>();

        sheetContent.put("content",sheet.getContent());
        sheetContent.put("title",sheet.getTitle());
        sheetContent.put("question",sheet.getQuestion());
        sheetContent.put("example",sheet.getExample());
        sheetContent.put("colNum",sheet.getColNum());
        sheetContent.put("createdDate",sheet.getCreatedDate());
        sheetContent.put("status",sheet.getSheetStatus());
        sheetDetail.add(sheetContent);

        for (MemberSheet memberSheet : sheetDetailList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", memberSheet.getId());
            map.put("modifiedDate", memberSheet.getModifiedDate());
            map.put("requestStatus", memberSheet.getRequestStatus());
            map.put("response", memberSheet.getResponse());
            map.put("responseDate", memberSheet.getResponseDate());
            map.put("email", memberSheet.getMember().getEmail());
            map.put("name", memberSheet.getMember().getName());
            map.put("position", memberSheet.getMember().getPosition());
            map.put("teamName", memberSheet.getMember().getTeamName());
            sheetDetail.add(map);
        }

        return sheetDetail;

    }

    @ApiOperation(value = "sheet 시작하기", notes = "sheet id 값으로 wait->proceeding")
    @GetMapping("/sheets/start/{sheetId}")
    @ResponseBody
    public String startSheet(@PathVariable String sheetId){
        return "";
    }

    @ApiOperation(value = "sheet 끝내기", notes = "sheet id 값으로 proceeding -> FISHISHED")
    @GetMapping("/sheets/end/{sheetId}")
    @ResponseBody
    public String endSheet(@PathVariable String sheetId){
        return "";
    }
}
