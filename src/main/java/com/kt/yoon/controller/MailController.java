package com.kt.yoon.controller;

import com.kt.yoon.domain.JsonResponse;
import com.kt.yoon.domain.Response;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.form.MailForm;
import com.kt.yoon.domain.type.MailType;
import com.kt.yoon.domain.type.RequestStatus;
import com.kt.yoon.exception.DoNotSendEmailException;
import com.kt.yoon.service.MailService;
import com.kt.yoon.service.ResponseService;
import com.kt.yoon.service.SheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Api(tags = {"5.Mail"})
@Controller
@RequiredArgsConstructor
@ResponseBody
@Slf4j
public class MailController {

    public final MailService mailService;
    private final SheetService sheetService;
    private final ResponseService responseService;

    @ApiOperation(value = "시트 유저 모두에게 메일 보내기")
    @GetMapping("/api/send/{sheetId}")
    public void sendMailAll(@PathVariable String sheetId) throws Exception {
        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));

        if (sheet.getLastSendMailDate() != null) {
            throw new DoNotSendEmailException();
        }

        List<MailForm> mailFormList = new ArrayList<>();
        List<Response> responseList = responseService.findAllResponseBySheet(sheet);
        for (Response response : responseList) {
            mailFormList.add(new MailForm(response.getToken(), response.getEmail(), response.getUserName(), response.getId()));
        }

        mailService.sendMail(sheet, mailFormList, MailType.NORMAL, "");
        sheetService.setSheetLastSendMailDate(sheet);
    }

    @ApiOperation(value = "미응답자에게 메일 보내기")
    @GetMapping("/api/send/none/{sheetId}")
    public JsonResponse sendMailNoneResponse(@PathVariable String sheetId) throws Exception {
        Sheet sheet = sheetService.getSheetById(Long.parseLong(sheetId));

        List<MailForm> mailFormList = new ArrayList<>();
        List<Response> responseList = responseService.findAllResponseBySheet(sheet);

        for (Response response : responseList) {
            if (response.getRequestStatus() == RequestStatus.NO) {
                if (LocalDateTime.now().isAfter(response.getLastSendMailDate().plusMinutes(10))) {
                    mailFormList.add(new MailForm(response.getToken(), response.getEmail(), response.getUserName(), response.getId()));
                    responseService.setLastSendMailDate(response);
                }
            }
        }
        mailService.sendMail(sheet, mailFormList, MailType.RESEND, "");
        return new JsonResponse(200, "success");
    }

    @ApiOperation(value = "시트 유저 한명에게 메일 보내기")
    @GetMapping("/api/send/one/{responseId}")
    public JsonResponse sendMailUser(@PathVariable String responseId) throws Exception {
        Response response = responseService.findOne(responseId);
        Sheet sheet = response.getSheet();

        if (!LocalDateTime.now().isAfter(response.getLastSendMailDate().plusMinutes(10))) {
            throw new DoNotSendEmailException();
        }

        List<MailForm> mailFormList = new ArrayList<>();
        mailFormList.add(new MailForm(response.getToken(), response.getEmail(), response.getUserName(), response.getId()));
        mailService.sendMail(sheet, mailFormList, MailType.RESEND, "");
        responseService.setLastSendMailDate(response);
        return new JsonResponse(200, "success");
    }

    @ApiOperation(value = "시트 유저 수정요청 메일 보내기")
    @GetMapping("/api/send/modify/{responseId}")
    public JsonResponse modifyMailUser(@PathVariable String responseId, @RequestParam("message") String message) throws Exception {
        Response response = responseService.findOne(responseId);
        Sheet sheet = response.getSheet();

        if (!LocalDateTime.now().isAfter(response.getLastSendMailDate().plusMinutes(10))) {
            throw new DoNotSendEmailException();
        }

        List<MailForm> mailFormList = new ArrayList<>();
        mailFormList.add(new MailForm(response.getToken(), response.getEmail(), response.getUserName(), response.getId()));
        mailService.sendMail(sheet, mailFormList, MailType.MODIFIED, message);
        responseService.setLastSendMailDate(response);
        return new JsonResponse(200, "success");
    }
}
