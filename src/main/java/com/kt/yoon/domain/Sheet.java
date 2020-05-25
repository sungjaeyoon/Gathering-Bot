package com.kt.yoon.domain;


import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private String title;

    private String content;

    private String question;

    private int colNum;

    private LocalDateTime createdDate;

    private LocalDateTime finishedDate;

    private LocalDateTime repeatDate;

    @Enumerated(value = EnumType.STRING)
    private SheetStatus sheetStatus;

    @Enumerated(value = EnumType.STRING)
    private SheetType sheetType;

    //== 생성 메소드==//
    public Sheet() {
    }

    public Sheet(Member createdMember, String title, String sheetContent, String tableContent, int colNum, LocalDateTime finishedDate, LocalDateTime repeatDate, List<Member> memberList) {
        this.createdMember = createdMember;
        this.title = title;
        this.content = sheetContent;
        this.question = tableContent;
        this.colNum = colNum;
        this.finishedDate = finishedDate;
        this.repeatDate = repeatDate;

        for (Member m : memberList) {
            memberSheetList.add(new MemberSheet(m, this));
        }
        createdDate = LocalDateTime.now();

    }


}
