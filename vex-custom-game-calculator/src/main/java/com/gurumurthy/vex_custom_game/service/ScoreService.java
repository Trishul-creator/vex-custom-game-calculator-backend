package com.gurumurthy.vex_custom_game.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gurumurthy.vex_custom_game.model.Score;
import com.gurumurthy.vex_custom_game.model.TeamInfo;
import com.gurumurthy.vex_custom_game.repository.ScoreRepository;
import com.gurumurthy.vex_custom_game.repository.TeamInfoRepository;

import jakarta.transaction.Transactional;

@Service
public class ScoreService {

	private final ScoreRepository scoreRepository;
	

	public ScoreService(ScoreRepository scoreRepository, TeamInfoRepository teamInfoRepository) {
		this.scoreRepository = scoreRepository;
	}

	public Score addScore(Score score, TeamInfo teamInfo) {

		// Set the TeamInfo to the Score
		score.setTeamInfo(teamInfo);
		Score saveScore = scoreRepository.save(score);

		// Save the Score to the database
		return saveScore;// Persist the score
	}

	// Update the score based on the Score ID
	public Score updateScore(UUID id,  Score updatedScore) {
		// Fetch the existing Score from the database
		Score existingScore = scoreRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Score not found with id: " + id));

		// Update the fields of the existing score

		existingScore.setRedBlocksScored(updatedScore.getRedBlocksScored());

		existingScore.setGreenBlocksScored(updatedScore.getGreenBlocksScored());

		existingScore.setPurpleBlocksScored(updatedScore.getPurpleBlocksScored());
		
		existingScore.setParkingStatus(updatedScore.getParkingStatus());

		// If the team info is included in the update, update it as well (Optional)
		if(updatedScore.getTeamInfo() != null) {
			existingScore.setTeamInfo(updatedScore.getTeamInfo());
		}
		
		Score savedExistingScore = scoreRepository.save(existingScore);

		// Save and return the updated score
		return savedExistingScore;
	}

	// Delete score by ID
	@Transactional
	public void deleteScore(UUID round, TeamInfo teamInfo) {
		scoreRepository.deleteByIdAndTeamInfo(round, teamInfo);
	}

	@Transactional
	public void deleteScoresByTeamInfo(TeamInfo teamInfo) {
		scoreRepository.deleteAllByTeamInfo(teamInfo);

	}

	// Get score by ID
	public Score getScoreById(UUID scoreId) {
		// Fetch the Score by ID
		return scoreRepository.findById(scoreId)
				.orElseThrow(() -> new RuntimeException("Score not found with id: " + scoreId));
	}

	public Score getScoreByTotalScore(int totalScore) {
		return scoreRepository.findByTotalScore(totalScore)
				.orElseThrow(() -> new RuntimeException("Score not found with score: " + totalScore));
	}

	public List<Score> getScoreByRound(int round) {
		return scoreRepository.findAllByRound(round)
				.orElseThrow(() -> new RuntimeException("Score not found with round number: " + round));
	}

	public List<Score> getScoreByTeamInfo(TeamInfo info) {
		return scoreRepository.findAllByTeamInfo(info)
				.orElseThrow(() -> new RuntimeException("No scores found assosciated with this team"));

	}

	public Score getSpecificScoreByIdAndTeamName(UUID id, TeamInfo teamInfo) {
		return scoreRepository.findByIdAndTeamInfo(id, teamInfo).orElseThrow(
				() -> new RuntimeException("Score not found with either this team, or This round does not exist"));
	}

}
