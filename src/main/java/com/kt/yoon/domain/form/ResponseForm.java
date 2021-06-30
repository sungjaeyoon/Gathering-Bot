package com.kt.yoon.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ResponseForm {

    @NotEmpty(message = "id는 필수 입니다.")
    private String firstResponseId;

    @NotEmpty(message = "id는 필수 입니다.")
    private String sheetId;

    @NotEmpty(message = "유저이름은 필수 입니다.")
    private String userName;

    @NotEmpty(message = "직책은 필수 입니다.")
    private String position;

    @NotEmpty(message = "팀이름은 필수 입니다.")
    private String teamName;

    @NotEmpty(message = "이메일은 필수 입니다.")
    private String email;

    private ResponseData[] responseData;

    @NotEmpty(message = "토큰값은 필수입니다..")
    private String token;
}

