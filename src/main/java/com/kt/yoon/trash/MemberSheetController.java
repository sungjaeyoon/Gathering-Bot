package com.kt.yoon.trash;

import com.kt.yoon.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(tags = {"5.MemberSheet"})
@Controller
@RequiredArgsConstructor
@ResponseBody
@Slf4j
public class MemberSheetController {
    private final UserService memberService;

    //제출할 응답(memberId)

    //제출한 응답(memberId)

}
