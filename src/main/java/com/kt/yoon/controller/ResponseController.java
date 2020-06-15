package com.kt.yoon.controller;

import com.kt.yoon.domain.MemberSheet;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.exception.JsonErrorResponse;
import com.kt.yoon.service.SheetService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ResponseController {

    private final SheetService sheetService;

    @GetMapping("/response/{sheetId}")
    public JSONObject getSheetById(@PathVariable String sheetId) {
        JSONObject jsonObject = new JSONObject();
        try {
            Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
            jsonObject.put("title", sheet.getTitle());
            jsonObject.put("content", sheet.getContent());
            jsonObject.put("question", sheet.getQuestion());
            jsonObject.put("example", sheet.getExample());
            jsonObject.put("status", 200);
        } catch (Exception e) {
            return new JsonErrorResponse(500, "서버 에러").getJsonObject();
        }
        return jsonObject;
    }

    @PostMapping("/response/{sheetId}/{userId}")
    public String saveResponse(@PathVariable("sheetId") String sheetId, @PathVariable("userId") String userId, String response) {
        return "success";
    }
}
