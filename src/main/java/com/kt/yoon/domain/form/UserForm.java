package com.kt.yoon.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
public class UserForm {
    @NotEmpty(message = "이름은 필수 입니다.")
    @Size(max = 30, message = "이름 길이는 30을 초과할 수 없습니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입니다.")
    @Size(max = 100, message = "이메일 길이는 100을 초과할 수 없습니다.")
    private String email;

    @NotEmpty(message = "패스워드 값은 필수 입니다.")
    private String password;

    @NotEmpty(message = "직책은 필수 입니다")
    private String position;

    @NotEmpty(message = "팀 이름은 필수 입니다")
    private String teamName;

}

