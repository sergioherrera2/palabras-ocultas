package edu.uclm.esi.games.model;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Player extends AbstractPlayer {
	@JsonIgnore
	private String email;
	@JsonIgnore
	private String pwd;
	
	public Player() {
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
