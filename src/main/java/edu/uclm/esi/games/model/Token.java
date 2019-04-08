package edu.uclm.esi.games.model;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Token {
	@Id 
	private Integer id;
	private Long validTime;
	private String userName; 
	
	public Token() {
	}
	
	public Token(String userName) {
		this.userName=userName;
		this.id=Math.abs(new Random().nextInt());
		this.validTime=System.currentTimeMillis() + 10*60*1000;
	}
	
	public int getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}
	
	public long getValidTime() {
		return validTime;
	}
}
