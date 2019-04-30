package edu.uclm.esi.words;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.uclm.esi.games.model.AbstractPlayer;
import edu.uclm.esi.games.model.Board;

public class WordsBoard extends Board {
	private String[] words;

	public WordsBoard(WordsMatch wordsMatch) {
		super(wordsMatch);
		this.words = new String[]{"Albacete", "Cadiz", "Cuenca", "Barcelona", "Madrid", "Ciudad Real", "Burgos", "Valencia", "Tenerife"};
		
	}

	@Override
	public void move(AbstractPlayer player, Integer[] coordinates) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public AbstractPlayer getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean end() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getContent() {
		return "Albacete, Cadiz, Cuenca, Barcelona, Madrid, Ciudad Real, Burgos, Valencia, Tenerife";
	}

	@Override
	public boolean draw() {
		return false;
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

}
