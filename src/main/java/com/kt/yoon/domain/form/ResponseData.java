package com.kt.yoon.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ResponseData {
    @NotEmpty(message = "id는 필수 입니다.")
    private String responseId;

    @NotEmpty(message = "응답은 필수 입니다.")
    private String response;
}
