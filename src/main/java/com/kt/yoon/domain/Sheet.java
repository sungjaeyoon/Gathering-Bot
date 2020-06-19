package com.kt.yoon.domain;


import com.kt.yoon.domain.form.SheetForm;
import com.kt.yoon.domain.type.SheetStatus;
import com.kt.yoon.domain.type.SheetType;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter
public class Sheet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sheet_id")
    private Long id;

    @OneToMany(mappedBy = "sheet")
    private List<MemberSheet> memberSheetList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member createdMember;

    @Column(length = 100)
    private String title;

    @Column(length = 2000)
    private String content;

    @Column(length = 2000)
    private String question;

    @Column(length = 2000)
    private String example;

    private int colNum;

    private LocalDateTime createdDate;

    private LocalDateTime finishedDate;

    private LocalDateTime repeatDate;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private SheetStatus sheetStatus;

    @Column(length = 10)
    @Enumerated(value = EnumType.STRING)
    private SheetType sheetType;

    //== 생성 메소드==//
    public Sheet() {
    }

    public Sheet(Member createdMember, String title, String content, String question, int colNum, String finishedDateString, String example, LocalDateTime repeatDate, List<Member> memberList) {
        this.createdMember = createdMember;
        this.title = title;
        this.content = content;
        this.question = question;
        this.colNum = colNum;
        this.example = example;

        LocalDateTime finishedDate = LocalDateTime.parse(finishedDateString);
        this.finishedDate = finishedDate;
        this.repeatDate = repeatDate;

        for (Member m : memberList) {
            memberSheetList.add(new MemberSheet(m, this,generateRandomToken()));
        }

        createdDate = LocalDateTime.now();
        sheetStatus = SheetStatus.WAIT;
        sheetType = SheetType.TABLE;
    }

    public static Sheet createSheet(Member createdMember, SheetForm sheetForm, List<Member> memberList) {
        return new Sheet(createdMember, sheetForm.getTitle(), sheetForm.getContent(), sheetForm.getQuestion(), Integer.parseInt(sheetForm.getColNum()), sheetForm.getFinishedDate(), sheetForm.getExample(), null, memberList);
    }

    public String generateRandomToken() {
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

}
