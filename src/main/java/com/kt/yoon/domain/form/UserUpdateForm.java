package com.kt.yoon.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
public class UserUpdateForm {

    @NotEmpty(message = "이름은 필수 입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입니다.")
    private String email;

    private String password;

    @NotEmpty(message = "직책은 필수 입니다")
    private String position;

    @NotEmpty(message = "팀 이름은 필수 입니다")
    private String teamName;

    @NotEmpty(message = "상태는 필수 입니다")
    private String userStatus;

    @NotEmpty(message = "상태는 필수 입니다")
    private String adminString;

}
