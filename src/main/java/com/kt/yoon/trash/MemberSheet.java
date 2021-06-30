package com.kt.yoon.trash;

import com.kt.yoon.domain.type.RequestStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

//@Entity
//@Getter
public class MemberSheet {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "member_sheet_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sheet_id")
//    private Sheet sheet;
//
//    private String response;
//
//    @Column(length = 30)
//    private String randomToken;
//
//    private LocalDateTime responseDate;
//
//    private LocalDateTime modifiedDate;
//
//    @Enumerated(value = EnumType.STRING)
//    private RequestStatus requestStatus;
//
//    //==생성 메소드==//
//    public MemberSheet() {
//    }
//
//    public MemberSheet(Member member, Sheet sheet, String randomToken){
//        this.member=member;
//        this.sheet=sheet;
//        this.randomToken=randomToken;
//        this.requestStatus=RequestStatus.NO;
//    }
//
//    //==비즈니스 로직 ==//
//    public void saveResponse(String response){
//        this.response = response;
//        if(this.requestStatus==RequestStatus.NO) {
//            this.responseDate = LocalDateTime.now().plusHours(9);
//            this.requestStatus = RequestStatus.YES;
//        }else{
//            this.modifiedDate = LocalDateTime.now().plusHours(9);
//        }
//    }
}
