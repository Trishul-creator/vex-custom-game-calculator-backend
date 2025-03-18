package com.gurumurthy.vex_custom_game.controller;

import com.gurumurthy.vex_custom_game.model.Score;
import com.gurumurthy.vex_custom_game.model.TeamInfo;
import com.gurumurthy.vex_custom_game.service.TeamInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@CrossOrigin("https://www.vex-custom-game.publicvm.com")
@RestController
@RequestMapping("/api/teams")
public class TeamInfoController {

    private final TeamInfoService teamInfoService;

    public TeamInfoController(TeamInfoService teamInfoService) {
        this.teamInfoService = teamInfoService;
        // YAAAAAAAa
        //BOOOOOOOOOOo
    }

    // Add a new TeamInfo
    @PostMapping
    public ResponseEntity<TeamInfo> createTeam(@RequestBody TeamInfo teamInfo) {
        TeamInfo createdTeamInfo = teamInfoService.addTeamInfo(teamInfo);
        return new ResponseEntity<>(createdTeamInfo, HttpStatus.CREATED);
    }

    // Update an existing TeamInfo
    @PutMapping("/{teamId}")
    public ResponseEntity<TeamInfo> updateTeam(@PathVariable UUID teamId, 
                                               @RequestBody TeamInfo updatedTeamInfo) {
        TeamInfo savedUpdatedTeamInfo = teamInfoService.updateTeamInfo(teamId, updatedTeamInfo);
        return new ResponseEntity<>(savedUpdatedTeamInfo, HttpStatus.OK);
    }

    // Get all teams
    @GetMapping
    public ResponseEntity<List<TeamInfo>> getAllTeams() {
        List<TeamInfo> teamInfos = teamInfoService.getAllTeams();
        return new ResponseEntity<>(teamInfos, HttpStatus.OK);
    }

    // Get TeamInfo by teamName
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamInfo> getTeamByName(@PathVariable UUID teamId) {
        TeamInfo teamInfo = teamInfoService.getTeamInfoById(teamId);
        return new ResponseEntity<>(teamInfo, HttpStatus.OK);
    }

    // Get scores for a specific team
    @GetMapping("/{teamId}/scores")
    public ResponseEntity<List<Score>> getTeamScores(@PathVariable UUID teamId) {
        List<Score> scores = teamInfoService.getTeamScores(teamId);
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    // Get score for a specific round of a team
    @GetMapping("/scores/{scoreId}")
    public ResponseEntity<Score> getScoreForRound(@PathVariable UUID scoreId) {
        Score score = teamInfoService.getRoundScore(scoreId);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    // Add a score for a specific team
    @PostMapping("/{team1}/{team2}/scores")
    public ResponseEntity<List<Score>> addTeamScore(@RequestBody Score score, @PathVariable String team1, @PathVariable String team2) throws CloneNotSupportedException {
        List<Score> addedScores = teamInfoService.addTeamScore(score, team1, team2);
        return new ResponseEntity<>(addedScores, HttpStatus.CREATED);
    }

    // Update score for a specific round and team
    @PutMapping("/scores/{scoreId}")
    public ResponseEntity<Score> updateTeamScore(@PathVariable UUID scoreId, 
                                                 @RequestBody Score updatedScore) {
        Score updatedTeamScore = teamInfoService.updateTeamScore(scoreId, updatedScore);
        return new ResponseEntity<>(updatedTeamScore, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamById(@PathVariable UUID id) {
    	teamInfoService.deleteTeamInfoById(id);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    

    
    
    @DeleteMapping("/{teamId}/scores/{scoreId}")
    public ResponseEntity<Void> deleteTeamScore(@PathVariable UUID teamId, 
            @PathVariable UUID scoreId) {
    	teamInfoService.deleteScore(teamId, scoreId);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
    

    // Delete all scores for a team
    @DeleteMapping("/{teamName}/scores")
    public ResponseEntity<Void> deleteAllScoresForTeam(@PathVariable String teamName) {
        teamInfoService.deleteAllScoresInTeam(teamName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
