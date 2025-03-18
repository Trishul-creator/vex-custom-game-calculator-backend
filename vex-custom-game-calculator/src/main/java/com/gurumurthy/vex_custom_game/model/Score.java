package com.gurumurthy.vex_custom_game.model;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "score")
public class Score implements Cloneable{

	@Id
	@GeneratedValue
	private UUID id;

	@Column(name = "red_blocks_scored")
	private int redBlocksScored; // Red blocks scored by the team
	@Column(name = "green_blocks_scored")
	private int greenBlocksScored; // Green blocks scored by the team
	@Column(name = "purple_blocks_scored")
	private int purpleBlocksScored; // Purple blocks scored by the team

	@Column(name = "total_score")
	private int totalScore; // Stores the total score calculated based on blocks

	@ManyToOne
	@JoinColumn(name = "team_info_id") // Foreign key column for TeamInfo
	private TeamInfo teamInfo; // The team to which this score belongs

	@Column(name = "round")
	private int round; // Tracks the round number of the score update
	
	@Column(name = "parking_status")
	private String parkingStatus;

	// Default constructor
	public Score() {
	}

	// Constructor with teamInfo and block scores
	public Score(int redBlocksScored, int greenBlocksScored, int purpleBlocksScored, int round, String parkingStatus) {
		this.redBlocksScored = redBlocksScored;
		this.greenBlocksScored = greenBlocksScored;
		this.purpleBlocksScored = purpleBlocksScored;
		this.round = round;
		this.parkingStatus = parkingStatus;
		
		calculateTotalScore();
		
		
	}

	public String getParkingStatus() {
		return parkingStatus;
	}

	public void setParkingStatus(String parkingStatus) {
		this.parkingStatus = parkingStatus;
		calculateTotalScore();
	}

	// Getter and setter methods
	public UUID getId() {
		return id;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getRedBlocksScored() {
		return redBlocksScored;
	}

	public void setRedBlocksScored(int redBlocksScored) {
		this.redBlocksScored = redBlocksScored;
		calculateTotalScore(); // Recalculate total score when blocks change

	}

	public int getGreenBlocksScored() {
		return greenBlocksScored;
	}

	public void setGreenBlocksScored(int greenBlocksScored) {
		this.greenBlocksScored = greenBlocksScored;
		calculateTotalScore(); // Recalculate total score when blocks change

	}

	public int getPurpleBlocksScored() {
		return purpleBlocksScored;
	}

	public void setPurpleBlocksScored(int purpleBlocksScored) {
		this.purpleBlocksScored = purpleBlocksScored;
		calculateTotalScore(); // Recalculate total score when blocks change

	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;

	}

	public TeamInfo getTeamInfo() {
		return teamInfo;
	}

	public void setTeamInfo(TeamInfo teamInfo) {
		this.teamInfo = teamInfo;

	}

	// Method to calculate total score based on blocks scored
	private void calculateTotalScore() {
		this.totalScore = (redBlocksScored * 5) + (greenBlocksScored * 2) + (purpleBlocksScored * 3);
		
		if(parkingStatus == null) {
			return;
		}
		
		if(parkingStatus.equals("Fully Parked")) {
			this.totalScore += 10;
		} else if (parkingStatus.equals("Partially Parked")) {
			this.totalScore += 5;
		} else if (parkingStatus.equals("Not Parked")) {
			this.totalScore += 0;
		}
		
	}
	
	@Override
    public Score clone() throws CloneNotSupportedException {
        return (Score) super.clone();
    }

}
