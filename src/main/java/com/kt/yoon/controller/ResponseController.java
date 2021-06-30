package com.kt.yoon.controller;

import com.kt.yoon.domain.JsonResponse;
import com.kt.yoon.domain.Response;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.form.ResponseForm;
import com.kt.yoon.domain.form.ResponseRemoveForm;
import com.kt.yoon.domain.form.ResponseSheetDto;
import com.kt.yoon.exception.AlreadyExitSheet;
import com.kt.yoon.service.ResponseService;
import com.kt.yoon.service.SheetService;
import com.kt.yoon.utils.CheckAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Api(tags = {"4.Response"})
@Controller
@RequiredArgsConstructor
@Slf4j
@ResponseBody
public class ResponseController {

    private final SheetService sheetService;
    private final ResponseService responseService;

    @ApiOperation(value = "응답리스트 조회")
    @GetMapping("/api/responses/users")
    public JsonResponse getUserResponse(@RequestParam("index") int index,@RequestParam("email") String email, Authentication authentication) throws Exception {
        CheckAuth.checkUserCanAccess(email,authentication);
        Page<ResponseSheetDto> userResponse = responseService.findUserResponse(index, email);
        return JsonResponse.UserResponseList(200,userResponse);
    }

    @ApiOperation(value = "응답 화면 데이터 조회")
    @GetMapping("/api/responses/{sheetId}/{responseId}/{randomToken}")
    public JsonResponse getSheetById(@PathVariable String sheetId, @PathVariable String responseId, @PathVariable String randomToken) throws Exception {
        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
        checkSheetIsProceed(sheet);

        List<Response> response = responseService.findById(responseId);

        if (response.size() > 0) {
            checkUserCanAccessSheet(response.get(0), randomToken);
            return JsonResponse.responseList(200, response, sheet);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @ApiOperation(value = "응답 화면 데이터 저장")
    @PostMapping("/api/responses/result/update")
    public JsonResponse saveResponse(@RequestBody @Valid ResponseForm responseForms) throws Exception {
        Sheet sheet = sheetService.getSheetById(Long.parseLong(responseForms.getSheetId()));
        checkSheetIsProceed(sheet);
        responseService.saveResponse(responseForms, sheet);
        return new JsonResponse(200, "success");
    }

    @ApiOperation(value = "응답 화면 데이터 삭제")
    @PostMapping("/api/responses/result/remove")
    public JsonResponse removeResponse(@RequestBody @Valid ResponseRemoveForm[] responseRemoveForm) throws Exception {
        Sheet sheet = sheetService.getSheetById(Long.parseLong(responseRemoveForm[0].getSheetId()));
        checkSheetIsProceed(sheet);
        responseService.removeResponse(responseRemoveForm);
        return new JsonResponse(200, "success");
    }

    public void checkSheetIsProceed(Sheet sheet) throws AlreadyExitSheet {
        if (!sheet.isSheetProceed()) {
            throw new AlreadyExitSheet("이미 종료된 시트 입니다.");
        }
    }

    public void checkUserCanAccessSheet(Response response, String randomToken) throws AccessDeniedException {
        if (!response.getToken().equals(randomToken)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }
}
