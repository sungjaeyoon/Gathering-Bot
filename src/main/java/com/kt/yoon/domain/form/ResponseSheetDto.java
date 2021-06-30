package com.kt.yoon.domain.form;

import com.kt.yoon.domain.type.RequestStatus;
import com.kt.yoon.domain.type.ShareType;
import com.kt.yoon.domain.type.SheetStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseSheetDto {
    private Long responseId;
    private String responseToken;
    private String sheetToken;
    private RequestStatus requestStatus;
    private LocalDateTime responseDate;

    private Long sheetId;
    private String title;
    private SheetStatus sheetStatus;
    private ShareType shareType;

    public ResponseSheetDto(Long responseId, String responseToken, String sheetToken, RequestStatus requestStatus, LocalDateTime responseDate, Long sheetId, String title, SheetStatus sheetStatus, ShareType shareType) {
        this.responseId = responseId;
        this.responseToken = responseToken;
        this.sheetToken = sheetToken;
        this.requestStatus = requestStatus;
        this.responseDate = responseDate;
        this.sheetId = sheetId;
        this.title = title;
        this.sheetStatus = sheetStatus;
        this.shareType = shareType;
    }
}
