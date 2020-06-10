package com.kt.yoon.domain;

import com.kt.yoon.domain.form.MemberForm;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@ToString
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 300)
    private String password;

    private String position;

    private String teamName;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    //== 생성 메소드==//

    public Member() {
    }

    public Member(String name, String email, String password, String position, String teamName) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.position = position;
        this.teamName = teamName;
    }

    public static Member createMember(MemberForm memberForm) {
        return new Member(memberForm.getName(), memberForm.getEmail(), memberForm.getPassword(), memberForm.getPosition(), memberForm.getTeamName());
    }

}
