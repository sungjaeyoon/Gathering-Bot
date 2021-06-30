package com.kt.yoon.domain;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String teamName;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Team parent;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private List<Team> children = new ArrayList<Team>();

    public Team(){}

    public Team(Long id, String teamName, Team parent, List<Team> children) {
        this.id = id;
        this.teamName = teamName;
        this.parent = parent;
        this.children = children;
    }
}
