package com.gurumurthy.vex_custom_game.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TeamInfo{

	@Id
	@GeneratedValue
	private UUID id;

	@Column(name = "team_name")
	private String teamName; // Name of the team

	@Column(name = "average_score")
	private float averageScore; // Stores the calculated average score of the team

	// Default constructor
	public TeamInfo() {
	}

	// Constructor with teamName
	public TeamInfo(String teamName) {
		this.teamName = teamName;
	}

	// Getter and setter methods
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public float getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(float averageScore) {
		float roundedAverageScore = new BigDecimal(averageScore).setScale(2, RoundingMode.HALF_UP).floatValue();
		this.averageScore = roundedAverageScore;
	}
	
	
	
	
}
