package com.kt.yoon.controller;


import com.kt.yoon.domain.JsonResponse;
import com.kt.yoon.domain.User;
import com.kt.yoon.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.util.List;

@Api(tags = {"2.Team"})
@Controller
@RequiredArgsConstructor
@ResponseBody
@Slf4j
public class TeamController {

    private final TeamService teamService;

    @ApiOperation(value = "팀 멤버 조회")
    @GetMapping("/api/teams/{teamId}")
    public JsonResponse getTeamUser(@PathVariable String teamId) throws Exception {
        List<User> users = teamService.getTeamUser(teamId);
        String teamName = teamService.getTeamName(teamId);
        return JsonResponse.teamInfo(200,users, teamName);
    }

    @ApiOperation(value = "팀 추가")
    @GetMapping("/api/teams/insert")
    public JsonResponse insertTeam(@RequestParam("teamName") String teamName, @RequestParam("teamId")String teamId) throws Exception {
        teamService.insertTeam(teamName, teamId);
        return new JsonResponse(200,"success");
    }

}
