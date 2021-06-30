package com.kt.yoon.domain.type;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //Common
    INVALID_INPUT_VALUE(400, "C001", "Input 오류입니다."),
    HANDLE_ACCESS_DENIED(401, "C002", "로그인이 필요합니다."),
    RESOURCE_ACCESS_DENIED(403, "C003", "권한이 없습니다."),
    RESOURCE_NOT_FOUND(400, "C004", "해당 자료가 없습니다."),
    NOT_READABLE(405, "C005", "읽을 수 없습니다."),
    ALREADY_EXIT_SHEET(405,"C006","이미 종료된 시트입니다."),

    // User
    EMAIL_DUPLICATION(400, "M001", "중복된 이메일입니다."),
    EMAIL_INPUT_INVALID(400, "M002", "가입되지 않은 이메일 입니다."),
    PASSWORD_INPUT_INVALID(400, "M003", "패스워드가 다릅니다."),
    BLOCKED_ACCOUNT(400, "M004", "비밀번호 오류로 잠긴 계정입니다."),
    BLOCK_ACCOUNT(400, "M005", "비밀번호 오류로 계정이 잠깁니다,"),
    NONE_EXIST_ID(400, "M006", "존재하지 않는 ID입니다."),

    // Team
    INVALID_TEAM_NAME(400, "T001", "존재하지 않는 팀이름입니다.");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
