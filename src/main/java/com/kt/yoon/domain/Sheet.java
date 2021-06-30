package com.kt.yoon.domain;


import com.kt.yoon.domain.form.SheetForm;
import com.kt.yoon.domain.type.ShareType;
import com.kt.yoon.domain.type.SheetStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Getter
public class Sheet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sheet_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String userName;

    @Column(nullable = false, length = 30)
    private String position;

    @Column(nullable = false, length = 30)
    private String teamName;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(length = 100)
    private String title;

    @Lob
    private String content;

    @Lob
    private String question;

    @Lob
    private String example;

    private String token;

    private int colNum;

    private LocalDateTime createdDate;

    private LocalDateTime finishedDate;

    private LocalDateTime lastSendMailDate;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private SheetStatus sheetStatus;

    @Column(length = 10)
    @Enumerated(value = EnumType.STRING)
    private ShareType shareType;

    //== 생성 메소드==//
    public Sheet() {
    }

    public Sheet(SheetForm sheetForm, User user) {
        this.userName = user.getName();
        this.position = user.getPosition();
        this.teamName = user.getTeam().getTeamName();
        this.email = user.getEmail();

        this.title = sheetForm.getTitle();
        this.content = sheetForm.getContent();
        this.question = sheetForm.getQuestion();
        this.example = sheetForm.getExample();
        this.colNum = Integer.parseInt(sheetForm.getColNum());
        this.finishedDate = LocalDateTime.parse(sheetForm.getFinishedDate());

        String shareType = sheetForm.getShareType();

        if (shareType.equals("private")) {
            this.shareType = ShareType.PRIVATE;
        } else if (shareType.equals("public")) {
            this.shareType = ShareType.PUBLIC;
        } else if (shareType.equals("link")) {
            this.shareType = ShareType.LINK;
        } else {
            this.shareType = ShareType.PRIVATE;
        }

        this.token = generateRandomToken();
        this.createdDate = LocalDateTime.now().plusHours(9);
        this.lastSendMailDate = null;
        this.sheetStatus = SheetStatus.WAIT;
    }

    public static Sheet createSheet(SheetForm sheetForm, User user) {
        return new Sheet(sheetForm, user);
    }

    public static String generateRandomToken() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    //== 비지니스 로직 ==//
    public void startSheet() {
        this.sheetStatus = SheetStatus.PROCEEDING;
    }

    public void endSheet() {
        this.sheetStatus = SheetStatus.FINISHED;
    }

    public boolean isSheetProceed() {
        if (this.sheetStatus == SheetStatus.PROCEEDING) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSheetWait() {
        if (this.sheetStatus == SheetStatus.WAIT) {
            return true;
        } else {
            return false;
        }
    }

    public void setLastSendMailDate(LocalDateTime time) {
        this.lastSendMailDate = time;
    }

}
