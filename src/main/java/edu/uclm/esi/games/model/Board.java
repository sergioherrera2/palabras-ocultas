package edu.uclm.esi.games.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity(name="board")
public abstract class Board {
	@Id
	protected UUID _id;
	protected int timesPlayed;
	protected int bestTime;
	protected int bestMovements;
	@JsonIgnore @Transient
	protected Match match;
	
	public Board() {
	}
	
	public Board(Match match) {
		this.match=match;
		this.bestTime=Integer.MAX_VALUE;
		this.bestMovements=Integer.MAX_VALUE;
	}

	public abstract void move(AbstractPlayer player, Integer[] coordinates) throws Exception;
	public abstract AbstractPlayer getWinner();
	public abstract boolean end();
	public abstract String getContent();

	public JSONObject toJSON() throws Exception {
		throw new Exception("toJSON not implemented in " + this.getClass().getSimpleName());
	}
	
	public UUID get_id() {
		return _id;
	}
	
	public void setMatch(Match match) {
		this.match = match;
	}

	public void increaseTimesPlayed() {
		this.timesPlayed++;
	}
	
	public int getBestMovements() {
		return bestMovements;
	}
	
	public void setBestMovements(int bestMovements) {
		this.bestMovements = bestMovements;
	}
	
	public int getBestTime() {
		return bestTime;
	}
	
	public void setBestTime(int bestTime) {
		this.bestTime = bestTime;
	}

	public abstract boolean draw();
}
