package com.kt.yoon.domain.dto;

import com.kt.yoon.domain.type.RequestStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class MemberSheetResponse {
    private Long memberSheetId;
    private LocalDateTime modifiedDate;
    private RequestStatus requestStatus;
    private String response;
    private LocalDateTime responseDate;
    private Long memberId;
    private Long sheetId;
    private String email;
    private String name;
    private String position;
    private String teamName;
}
