package edu.uclm.esi.games.model;

import javax.persistence.Entity;

@Entity
public class SimplePlayer extends AbstractPlayer {
	public SimplePlayer() {
	}
		
	public Match move(Integer[] coordinates) throws Exception {
		return this.currentMatch.move(this, coordinates);
	}
}
