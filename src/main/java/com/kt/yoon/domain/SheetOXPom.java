package com.kt.yoon.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class SheetOXPom {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sheet_ox_id")
    private Long id;

    private String content;

}
