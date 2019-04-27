package edu.uclm.esi.games.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="words")
public class Word {
	@Id 
	private int id;
	private String word;
	
	public Word(){
		
	}
	public Word(String word){
		this.word=word;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	
	

}
