package edu.uclm.esi.games.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity(name="abstractplayer")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="player_type")
public abstract class AbstractPlayer {
	@Id
	protected String userName;
	 @Transient
	 @JsonIgnore
	protected Match currentMatch;
	protected int victories;
	protected int defeats;
	protected int withdrawals;
	@JsonIgnore @Transient
	protected HttpSession httpSession;
		
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setCurrentMatch(Match match) {
		this.currentMatch=match;
	}
	
	public Match getCurrentMatch() {
		return currentMatch;
	}
	
	public Match move(Integer[] coordinates) throws Exception {
		return this.currentMatch.move(this, coordinates);
	}

	public void increaseVictoriesDefeatsWithdrawals(int victories, int defeats, int withdrawals) throws Exception {
		this.victories+=victories;
		this.defeats+=defeats;
		this.withdrawals+=withdrawals;
	}

	public int getVictories() {
		return victories;
	}
	
	public int getDefeats() {
		return defeats;
	}
	
	public int getWithdrawals() {
		return withdrawals;
	}

	public void invalidateSession() {
		this.httpSession.invalidate();
	}
	
	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
}
