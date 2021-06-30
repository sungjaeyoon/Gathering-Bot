package com.kt.yoon.domain.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailForm {
    private String token;
    private String email;
    private String userName;
    private Long responseId;

    public MailForm(String token, String email, String userName, Long responseId) {
        this.token = token;
        this.email = email;
        this.userName = userName;
        this.responseId = responseId;
    }
}
