package com.kt.yoon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String memberName;

    private String email;

    private String password;

    private String position;

    private String teamName;


    //== 생성 메소드==//
    public Member() {
    }

    public Member(String name, String position, String email, String teamName, String password) {
        this.memberName = name;
        this.position = position;
        this.email = email;
        this.teamName = teamName;
        this.password=password;
    }

    public static Member createMember(String name, String position, String email, String teamName, String password) {
        return new Member(name, position, email, teamName, password);
    }

}
