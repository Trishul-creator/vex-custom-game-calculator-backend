package com.gurumurthy.vex_custom_game.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gurumurthy.vex_custom_game.model.Score;
import com.gurumurthy.vex_custom_game.model.TeamInfo;


@Repository
public interface TeamInfoRepository extends JpaRepository<TeamInfo, UUID> {

	Optional<TeamInfo> findByTeamName(String teamName);
	
	

	

}