package com.kt.yoon.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ResponseRemoveForm {

    @NotEmpty(message = "id는 필수 입니다.")
    private String sheetId;

    @NotEmpty(message = "id는 필수 입니다.")
    private String responseId;

    @NotEmpty(message = "토큰은 필수 입니다.")
    private String token;
}
