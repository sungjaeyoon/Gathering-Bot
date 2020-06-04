package com.kt.yoon.domain.form;

import com.kt.yoon.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class SheetForm {

    @NotEmpty(message = "이메일은 필수 입니다.")
    private String createdMemberEmail;

    @NotEmpty(message = "제목은 필수 입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수 입니다.")
    private String content;

    @NotEmpty(message = "응답 항목은 필수 입니다.")
    private String question;

    @NotEmpty(message = "응답 개수는 필수 입니다.")
    private String colNum;

    @NotEmpty(message = "멤버는 필수 입니다.")
    private List<String> requestEmailList;

}
