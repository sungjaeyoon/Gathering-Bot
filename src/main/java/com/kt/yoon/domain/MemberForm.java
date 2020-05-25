package com.kt.yoon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "이름은 필수 입니다")
    private String name;

    @NotEmpty(message = "직책은 필수 입니다")
    private String position;

    @NotEmpty(message = "이메일은 필수 입니다")
    private String email;

    @NotEmpty(message = "팀 이름은 필수 입니다")
    private String teamName;
}
