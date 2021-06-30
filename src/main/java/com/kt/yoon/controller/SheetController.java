package com.kt.yoon.controller;

import com.kt.yoon.domain.JsonResponse;
import com.kt.yoon.domain.Response;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.User;
import com.kt.yoon.domain.form.SheetForm;
import com.kt.yoon.domain.type.ShareType;
import com.kt.yoon.service.ResponseService;
import com.kt.yoon.service.SheetService;
import com.kt.yoon.service.UserService;
import com.kt.yoon.utils.CheckAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Api(tags = {"3.Sheet"})
@ResponseBody
@Slf4j
public class SheetController {

    private final SheetService sheetService;
    private final UserService userService;
    private final ResponseService responseService;

    @ApiOperation(value = "Sheet 추가")
    @PostMapping("/api/sheets/new")
    public JsonResponse addNewSheet(@Valid @RequestBody SheetForm sheetForm) throws Exception {
        sheetService.createSheet(sheetForm);
        return new JsonResponse(200, "success");
    }


    @ApiOperation(value = "사용자 Sheet 조회")
    @GetMapping("/api/sheets/list/{userId}")
    public JsonResponse getSheetsByMember(@PathVariable String userId,
                                          @RequestParam("index") int start,
                                          @RequestParam("display") int display,
                                          @RequestParam(value = "word", defaultValue = "", required = false) String word,
                                          @RequestParam(value = "type", defaultValue = "", required = false) String type,
                                          Authentication authentication) throws Exception {
        User requestUser = userService.findUserById(Long.parseLong(userId));
        CheckAuth.checkUserCanAccess(requestUser.getEmail(), authentication);
        Page<Sheet> sheetList = sheetService.getAllSheetsByUser(requestUser, start, display, word, type);
        return JsonResponse.sheetList(200, sheetList);
    }

    @ApiOperation(value = "Sheet 삭제")
    @GetMapping("/api/sheets/remove/{sheetId}")
    public JsonResponse removeSheet(@PathVariable String sheetId, Authentication authentication) throws Exception {
        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
        CheckAuth.checkUserCanAccess(sheet.getEmail(), authentication);
        sheetService.removeSheet(Long.parseLong(sheetId));
        return new JsonResponse(200, "success");
    }

    @ApiOperation(value = "Sheet id 값으로 조회")
    @GetMapping("/api/sheets/{sheetId}/{token}")
    public JsonResponse getSheetDetail(@PathVariable String sheetId, @PathVariable String token, Authentication authentication) throws Exception {
        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
        List<Response> responseList = responseService.findAllResponseBySheet(sheet);

        if (!sheet.getToken().equals(token)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        if (sheet.getShareType() == ShareType.PRIVATE) {
            CheckAuth.checkUserCanAccess(sheet.getEmail(), authentication);
        } else if (sheet.getShareType() == ShareType.PUBLIC) {
            String requestUserEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
            boolean isSheetUser = false;
            for (Response response : responseList) {
                if (response.getEmail().equals(requestUserEmail)) {
                    isSheetUser = true;
                    break;
                }
            }
            if (!isSheetUser) {
                throw new AccessDeniedException("권한이 없습니다.");
            }
        }

        return JsonResponse.sheetDetail(200, responseList, sheet);
    }

    @ApiOperation(value = "Sheet 시작")
    @GetMapping("/api/sheets/start/{sheetId}")
    public JsonResponse startSheet(@PathVariable String sheetId, Authentication authentication) throws Exception {
        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
        CheckAuth.checkUserCanAccess(sheet.getEmail(), authentication);
        sheetService.startSheet(Long.parseLong(sheetId));
        return new JsonResponse(200, "success");
    }

    @ApiOperation(value = "Sheet 종료")
    @GetMapping("/api/sheets/end/{sheetId}")
    public JsonResponse endSheet(@PathVariable String sheetId, Authentication authentication) throws Exception {
        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));
        CheckAuth.checkUserCanAccess(sheet.getEmail(), authentication);
        sheetService.endSheet(Long.parseLong(sheetId));
        return new JsonResponse(200, "success");
    }
}
