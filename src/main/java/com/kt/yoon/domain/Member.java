package com.kt.yoon.domain;

import com.kt.yoon.domain.form.MemberForm;
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

    public Member(String memberName, String email, String password, String position, String teamName) {
        this.memberName = memberName;
        this.email = email;
        this.password = password;
        this.position = position;
        this.teamName = teamName;
    }

    public static Member createMember(MemberForm memberForm) {
        return new Member(memberForm.getName(), memberForm.getEmail(), memberForm.getPassword(), memberForm.getPosition(), memberForm.getTeamName());
    }

}
