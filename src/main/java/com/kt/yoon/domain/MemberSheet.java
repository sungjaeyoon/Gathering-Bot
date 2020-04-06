package com.kt.yoon.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class MemberSheet {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_sheet_id")
    private Long id;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sheet_id")
    private Sheet sheetId;

    private String requestContent;

    private LocalDateTime requestDate;

    private LocalDateTime modifiedDate;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus sheetStatus;

}
