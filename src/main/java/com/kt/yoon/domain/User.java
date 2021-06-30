package com.kt.yoon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kt.yoon.domain.form.UserForm;
import com.kt.yoon.domain.form.UserUpdateForm;
import com.kt.yoon.domain.type.UserStatus;
import lombok.Getter;
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
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 300)
    private String password;

    @Column(nullable = false, length = 30)
    private String position;

    @JsonIgnore
    @Column
    private int failCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User() {
    }

    public User(UserForm userForm, Team team) {
        this.name = userForm.getName();
        this.email = userForm.getEmail();
        this.password = userForm.getPassword();
        this.position = userForm.getPosition();
        this.team = team;
        this.failCount = 0;
        this.userStatus = UserStatus.ACTIVE;
    }

    public static User createUser(UserForm userForm, Team team) {
        return new User(userForm, team);
    }


    //== 비지니스 로직 ==//
    public void addFailCount() {
        this.failCount++;
    }

    public void initFailCount() {
        this.failCount = 0;
    }

    public boolean isFailCountOver() {
        if (this.failCount == 3) {
            return true;
        }
        return false;
    }

    public void blockUser() {
        this.userStatus = UserStatus.BLOCK;
        this.failCount = 0;
    }

    public void activeUser() {
        this.userStatus = UserStatus.ACTIVE;
    }

    public boolean isUserBlocked() {
        if (this.userStatus == UserStatus.BLOCK) {
            return true;
        }
        return false;
    }

    public boolean matchPassword(String password) {
        if (this.password.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateUser(UserUpdateForm userUpdateForm, Team team) {
        this.email = userUpdateForm.getEmail();
        if (!userUpdateForm.getName().equals("NULL")) {
            this.name = userUpdateForm.getName();
        }
        if (!userUpdateForm.getPassword().equals("NULL")) {
            this.password = userUpdateForm.getPassword();
        }
        if (!userUpdateForm.getPosition().equals("NULL")) {
            this.position = userUpdateForm.getPosition();
        }
        if(team!=null){
            this.team = team;
        }
        if (!userUpdateForm.getUserStatus().equals("NULL")) {

            if (userUpdateForm.getUserStatus().equals("ACTIVE")) {
                this.userStatus = UserStatus.ACTIVE;
            } else {
                this.userStatus = UserStatus.BLOCK;
            }
        }
    }
}
