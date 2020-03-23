package com.kt.yoon.domain;


import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Sheet {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sheet_id")
    private Long id;

    @OneToMany(mappedBy = "sheetId")
    private List<MemberSheet> memberSheetList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private SheetOXPom sheetOXPom;

    private String sheetTitle;

    private LocalDateTime createdDate;

    private LocalDateTime finishedDate;

    private LocalDateTime repeatDate;

    @Enumerated(value = EnumType.STRING)
    private SheetStatus sheetStatus;

    @Enumerated(value = EnumType.STRING)
    private SheetType sheetType;

}
