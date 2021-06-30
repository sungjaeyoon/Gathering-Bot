package com.kt.yoon.domain;

import com.kt.yoon.domain.form.ResponseUserForm;
import com.kt.yoon.domain.type.RequestStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "response_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String userName;

    @Column(nullable = false, length = 30)
    private String position;

    @Column(nullable = false, length = 30)
    private String teamName;

    @Column(nullable = false, length = 30)
    private String email;

    @Lob
    private String response;

    @Column(length = 30)
    private String token;

    private LocalDateTime responseDate;

    private LocalDateTime modifiedDate;

    private LocalDateTime lastSendMailDate;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sheet_id")
    private Sheet sheet;

    public Response() {
    }

    public Response(ResponseUserForm responseUserForm, Sheet sheet, String token) {
        this.userName = responseUserForm.getUserName();
        this.position = responseUserForm.getPosition();
        this.teamName = responseUserForm.getTeamName();
        this.email = responseUserForm.getEmail();
        this.sheet = sheet;
        this.token = token;
        this.requestStatus = RequestStatus.NO;
    }

    public Response(String userName, String position, String teamName, String email, String response, String token, Sheet sheet) {
        this.userName = userName;
        this.position = position;
        this.teamName = teamName;
        this.email = email;
        this.response = response;
        this.token = token;
        this.responseDate = LocalDateTime.now();
        this.modifiedDate = null;
        this.lastSendMailDate = null;
        this.requestStatus = RequestStatus.YES;
        this.sheet = sheet;
    }

    public static Response createResponse(ResponseUserForm responseUserForm, Sheet sheet, String token) {
        return new Response(responseUserForm, sheet, token);
    }

    public static Response addResponse(String userName, String position, String teamName, String email, String response, String token, Sheet sheet) {
        return new Response(userName, position, teamName, email, response, token, sheet);
    }

    //==비즈니스 로직==//
    public void saveResponse(String response) {
        if (this.requestStatus == RequestStatus.NO) {
            this.responseDate = LocalDateTime.now().plusHours(9);
            this.requestStatus = RequestStatus.YES;
        } else {
            this.modifiedDate = LocalDateTime.now().plusHours(9);
        }
        this.response = response;
    }

    public void setLastSendMailDate(LocalDateTime time) {
        this.lastSendMailDate = time;
    }
}
