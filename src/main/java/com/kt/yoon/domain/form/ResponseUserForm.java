package com.kt.yoon.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class ResponseUserForm {

    @Column(nullable = false, length = 30)
    private String userName;

    @Column(nullable = false, length = 30)
    private String position;

    @Column(nullable = false, length = 30)
    private String teamName;

    @Column(nullable = false, length = 30)
    private String email;
}
