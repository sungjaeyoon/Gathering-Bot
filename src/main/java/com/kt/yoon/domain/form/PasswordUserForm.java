package com.kt.yoon.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PasswordUserForm {


    @NotEmpty(message = "id 값은 필수 입니다.")
    private String id;

    @NotEmpty(message = "이메일 값은 필수 입니다.")
    private String email;

    @NotEmpty(message = "패스워드 값은 필수 입니다.")
    private String currentPassword;

    @NotEmpty(message = "패스워드 값은 필수 입니다.")
    private String newPassword;

}
