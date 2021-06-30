package com.kt.yoon.service;

import com.kt.yoon.domain.Team;
import com.kt.yoon.domain.User;
import com.kt.yoon.exception.InvalidIdException;
import com.kt.yoon.repository.TeamRepository;
import com.kt.yoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public List<User> getTeamUser(String teamId) {
        return userRepository.findByTeamId(Long.parseLong(teamId));
    }

    public String getTeamName(String teamId) throws Exception {
        Team team = teamRepository.findById(Long.parseLong(teamId)).orElseThrow(() -> new InvalidIdException());
        return team.getTeamName();
    }

    public void insertTeam(String teamName, String teamId){
        Team team = new Team(Long.parseLong(teamId), teamName, null,null);
        teamRepository.save(team);
    }

}
