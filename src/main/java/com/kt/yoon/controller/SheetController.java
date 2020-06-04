package com.kt.yoon.controller;

import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.form.SheetForm;
import com.kt.yoon.service.MemberService;
import com.kt.yoon.service.SheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Api(tags = {"2.Sheet"})
public class SheetController {

    private final SheetService sheetService;
    private final MemberService memberService;

    @ApiOperation(value = "Sheet 추가", notes = "Sheet를 추가한다.")
    @PostMapping("/sheet/new")
    @ResponseBody
    public void addSheet(@Valid @RequestBody SheetForm sheetForm, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Member createMember = memberService.findMemberByEmail(sheetForm.getCreatedMemberEmail());
        List<Member> memberList = new ArrayList<>();

        for (String email : sheetForm.getRequestEmailList()) {
            memberList.add(memberService.findMemberByEmail(email));
        }

        sheetService.save(Sheet.createSheet(createMember, sheetForm, memberList));
    }
}
