package com.kt.yoon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String memberName;

    private String position;

    private String email;

    private String teamName;

    private String hashCode;

    //== 생성 메소드==//
    public Member(){}
    public Member(String name,String position, String email, String teamName, String hashCode) {
        this.memberName=name;
        this.position=position;
        this.email=email;
        this.teamName=teamName;
        this.hashCode=hashCode;
    }

    public static Member createMember(String name, String position, String email, String teamName, String hashCode){
        return new Member(name, position, email, teamName, hashCode);
    }

}
