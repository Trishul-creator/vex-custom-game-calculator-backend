package com.gurumurthy.vex_custom_game.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gurumurthy.vex_custom_game.model.Score;
import com.gurumurthy.vex_custom_game.model.TeamInfo;



@Repository
public interface ScoreRepository extends JpaRepository<Score, UUID> {

	Optional<Score> findByTotalScore(int totalScore);

	Optional<List<Score>> findAllByRound(int round);

	Optional<List<Score>> findAllByTeamInfo(TeamInfo info);

	Optional<Score> findByRoundAndTeamInfo(int round, TeamInfo teamInfo);

	
	void deleteByRoundAndTeamInfo(int round, TeamInfo teamInfo);

	void deleteAllByTeamInfo(TeamInfo teamInfo);

	void deleteByIdAndTeamInfo(UUID round, TeamInfo teamInfo);

	Optional<Score> findByIdAndTeamInfo(UUID id, TeamInfo teamInfo);


	
	

}
