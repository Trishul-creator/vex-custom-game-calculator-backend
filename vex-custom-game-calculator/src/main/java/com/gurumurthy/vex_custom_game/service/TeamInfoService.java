package com.gurumurthy.vex_custom_game.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gurumurthy.vex_custom_game.model.Score;
import com.gurumurthy.vex_custom_game.model.TeamInfo;
import com.gurumurthy.vex_custom_game.repository.TeamInfoRepository;

@Service
public class TeamInfoService {

	private final ScoreService scoreService;

	private final TeamInfoRepository teamInfoRepository;

	public TeamInfoService(TeamInfoRepository teamInfoRepository, ScoreService scoreService) {
		this.scoreService = scoreService;
		this.teamInfoRepository = teamInfoRepository;
	}

	// Method to add a new TeamInfo
	public TeamInfo addTeamInfo(TeamInfo teamInfo) {
		return teamInfoRepository.save(teamInfo);
	}

	public TeamInfo updateTeamInfo(UUID existingTeamId, TeamInfo updatedTeamInfo) {
		// Retrieve the existing TeamInfo by teamId
		TeamInfo existingTeamInfo = teamInfoRepository.findById(existingTeamId)
				.orElseThrow(() -> new RuntimeException("Team not found with id: " + existingTeamId));

		// Update teamName (or other fields)
		if (updatedTeamInfo.getTeamName() != null) {
			existingTeamInfo.setTeamName(updatedTeamInfo.getTeamName());
		}

		return teamInfoRepository.save(existingTeamInfo);
	}

	public List<Score> getTeamScores(UUID teamId) {
		TeamInfo teamInfo = teamInfoRepository.findById(teamId)
				.orElseThrow(() -> new RuntimeException("Team not found with id: " + teamId));
		List<Score> scores = scoreService.getScoreByTeamInfo(teamInfo);
		
		Collections.sort(scores, new Comparator<Score>() {
			   public int compare(Score b1, Score b2) {
				   if(b1.getRound() > b2.getRound()) {
					   return -1;
				   } else if(b1.getRound() < b2.getRound()) {
					   return 1;
				   } else {
					   return 0;
				   }
				   
			   }
			});

		return scores;

	}

	public Score getRoundScore(UUID id) {
		return scoreService.getScoreById(id);

	}
	//public TeamInfo getTeamInfoByScore(UUID scoreId) {
		//TeamInfo team = teamInfoRepository.findByScores_Id(scoreId)
				//.orElseThrow(() -> new RuntimeException("...."));
		//return team;
	//}
	public void deleteScore(UUID teamId, UUID scoreId) {
		TeamInfo teamInfo = teamInfoRepository.findById(teamId)
				.orElseThrow(() -> new RuntimeException("Team not found with id: " + teamId));
		
		scoreService.deleteScore(scoreId, teamInfo);
		
		updateAverageScore(teamInfo);
	}

	public Score updateTeamScore(UUID id,  Score updatedScore) {
		
		Score savedUpdatedScore = scoreService.updateScore(id, updatedScore);
		

		
		updateAverageScore(savedUpdatedScore.getTeamInfo());

		return savedUpdatedScore;

	}
	
	public TeamInfo getTeamInfoById(UUID id) {
		TeamInfo teamInfo = teamInfoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Team not found with this id: " + id));
		return teamInfo;
	}

	public List<TeamInfo> getAllTeams() {
		List<TeamInfo> teamInfos = teamInfoRepository.findAll();
			   
			  Collections.sort(teamInfos, new Comparator<TeamInfo>() {
				   public int compare(TeamInfo b1, TeamInfo b2) {
					   if(b1.getAverageScore() > b2.getAverageScore()) {
						   return -1;
					   } else if(b1.getAverageScore() < b2.getAverageScore()) {
						   return 1;
					   } else {
						   return 0;
					   }
					   
				   }
				});

		return teamInfos;
	}

	public List<Score> addTeamScore(Score score, String teamName1, String teamName2) throws CloneNotSupportedException {
		Score score1 = score.clone();
		Score score2 = score.clone();
		TeamInfo teamInfo1 = teamInfoRepository.findByTeamName(teamName1)
				.orElseThrow(() -> new RuntimeException("Team not found with this name: " + teamName1));
		
		TeamInfo teamInfo2 = teamInfoRepository.findByTeamName(teamName2)
				.orElseThrow(() -> new RuntimeException("Team not found with this name: " + teamName2));
		Score addedScore1 = scoreService.addScore(score1, teamInfo1);
		Score addedScore2 = scoreService.addScore(score2, teamInfo2);
		
		List<Score> scores = new ArrayList<Score>();
		
		scores.add(addedScore1);
		scores.add(addedScore2);
		
		
		
		
		
		updateAverageScore(teamInfo1);
		updateAverageScore(teamInfo2);
		
		return scores;
	}

	public void deleteTeamInfo(String teamName) {
		TeamInfo teamInfo = teamInfoRepository.findByTeamName(teamName)
				.orElseThrow(() -> new RuntimeException("There is no team with such name."));
		scoreService.deleteScoresByTeamInfo(teamInfo);
		
		teamInfoRepository.delete(teamInfo);
	}
	
	public void deleteTeamInfoById(UUID id) {
		TeamInfo teamInfo = teamInfoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("There is no team with such id."));
		scoreService.deleteScoresByTeamInfo(teamInfo);
		teamInfoRepository.delete(teamInfo);
	}
	
	public void deleteAllScoresInTeam(String teamName) {
		TeamInfo teamInfo = teamInfoRepository.findByTeamName(teamName)
				.orElseThrow(() -> new RuntimeException("There is no team with such name."));
		
		scoreService.deleteScoresByTeamInfo(teamInfo);
		
		TeamInfo refreshedTeamInfo = teamInfoRepository.findByTeamName(teamName)
				.orElseThrow(() -> new RuntimeException("There is no team with such name."));
		
		
		updateAverageScore(refreshedTeamInfo);
		
	}
	
	
	
	public void updateAverageScore(TeamInfo teamInfo) {
		
		List<Score> scores = scoreService.getScoreByTeamInfo(teamInfo);
		
		if(scores.size() == 0) {
			teamInfo.setAverageScore(0);
			teamInfoRepository.save(teamInfo);
			return;
		}
		int totalScore = 0;
		for (Score score : scores) {
			totalScore += score.getTotalScore(); // Sum up the total scores from all scores
		}

		float averageScore = (float) totalScore / scores.size();
		
		
		
		
		
		teamInfo.setAverageScore(averageScore);
		
		teamInfoRepository.save(teamInfo);
		
	
		
		

	}
	
	

}
