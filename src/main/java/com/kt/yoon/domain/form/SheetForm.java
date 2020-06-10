package com.kt.yoon.domain.form;

import com.kt.yoon.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class SheetForm {

    @NotEmpty(message = "로그인은 필수 입니다.")
    private String createdMemberId;

    @NotEmpty(message = "제목은 필수 입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수 입니다.")
    private String content;

    @NotEmpty(message = "응답 항목은 필수 입니다.")
    private String question;

    @NotEmpty(message = "예시는 필수 입니다.")
    private String example;

    @NotEmpty(message = "응답 개수 오류")
    private String colNum;

    @NotEmpty(message = "완료 기한은 필수입니다.")
    private String finishedDate;

    @NotEmpty(message = "멤버는 필수 입니다.")
    private List<Member> memberList;

}
