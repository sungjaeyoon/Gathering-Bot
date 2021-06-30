package com.kt.yoon.domain;

import com.kt.yoon.domain.type.MailType;
import com.kt.yoon.domain.type.ShareType;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class Mail {

    private String mailTitle;
    private String mailContent;
    private Long sheetId;
    private String sheetUserName;
    private String sheetUserPosition;
    private String finishedDate;
    private String sheetToken;
    private String modifiedMessage;
    private ShareType shareType;
    private MailType mailType;

    private String token;
    private String userEmail;
    private Long responseId;
    private String userName;

    public Mail() {
    }

    public static Mail createMail(Sheet sheet, MailType mailType) {
        Mail sendMail = new Mail();
        sendMail.setSheetId(sheet.getId());
        sendMail.setSheetUserName(sheet.getUserName());
        sendMail.setSheetUserPosition(sheet.getPosition());
        sendMail.setFinishedDate(sheet.getFinishedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        sendMail.setMailContent(sheet.getContent());
        sendMail.setShareType(sheet.getShareType());
        sendMail.setSheetToken(sheet.getToken());
        sendMail.setMailType(mailType);
        if (mailType == MailType.NORMAL) {
            sendMail.setMailNormalTitle(sheet.getTitle());
        } else if (mailType == MailType.RESEND) {
            sendMail.setMailResendTitle(sheet.getTitle());
        } else if (mailType == MailType.MODIFIED) {
            sendMail.setMailModifiedTitle(sheet.getTitle());
        } else {
            sendMail.setMailNormalTitle(sheet.getTitle());
        }
        return sendMail;
    }

    public String getMailContent() {
        String content = "<div style=\"font-family:'Malgun Gothic';\"><h4>Gathering-bot에서 발송한 메일입니다.</h4>";

        content += "- 취합자: " + this.sheetUserName + " " + this.sheetUserPosition + "<br>";
        if (this.mailType == MailType.MODIFIED) {
            content += "- 수정요청 : " + this.modifiedMessage + "<br>";
        }
        content += "- 완료 기한: " + this.finishedDate + "<br><br>";

        content += "====================본문====================";
        content += this.mailContent.replaceAll("\n", "<br>");
        content += "===========================================<br><br>";

        content += "<a href=\"" + "http://10.225.168.104:8080" + "/response/" + this.sheetId + "/" +this.responseId + "/" +this.token + "\"> 답변하기</a>";
        content += "를 눌러서 응답해주세요. <span style=\"color:red;\">링크는 사내망</span>에서만 동작합니다.<br><br>";

        if (this.shareType == ShareType.LINK) {
            content += "링크가 있는 사용자에게 공개된 시트입니다. <a href=\"" + "http://10.225.168.104:8080" + "/sheets/" + this.sheetId + "/" + this.sheetToken + "\">시트보기</a>를 눌러 다른 인원의 답변을 확인할 수 있습니다.<br>";
            content +="<span style=\"color:red;\">(Chrome 브라우저에서만 볼 수 있습니다.)</span><br>";
        } else if (this.shareType == ShareType.PUBLIC) {
            content += "취합인원에게 공개된 시트입니다. <a href=\"" + "http://10.225.168.104:8080" + "/sheets/" + this.sheetId + "/" + this.sheetToken + "\">시트보기</a>를 눌러 다른 인원의 답변을 확인할 수 있습니다.<br>";
            content +="<span style=\"color:red;\">(Chrome 브라우저에서만 볼 수 있습니다.)</span><br>";
        } else if (this.shareType == ShareType.PRIVATE) {
            content += "취합자에게만 공개된 시트입니다.<br>";
        } else {
            content += "취합자에게만 공개된 시트입니다.<br>";
        }

        content += "<br>" + this.userName + "님에게 발송된 메일입니다.</div>";

        return content;
    }

    public void setMailNormalTitle(String mailTitle) {
        this.mailTitle = "[Gathering-bot] " + mailTitle;
    }

    public void setMailResendTitle(String mailTitle) {
        this.mailTitle = "[Gathering-bot][재발송] " + mailTitle;
    }

    public void setMailModifiedTitle(String mailTitle) {
        this.mailTitle = "[Gathering-bot][수정요청] " + mailTitle;
    }

    public void setModifiedMessage(String message) {
        this.modifiedMessage = message;
    }

    ;
}
