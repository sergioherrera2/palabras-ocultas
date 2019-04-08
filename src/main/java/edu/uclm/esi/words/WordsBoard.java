package edu.uclm.esi.words;

import edu.uclm.esi.games.model.AbstractPlayer;
import edu.uclm.esi.games.model.Board;

public class WordsBoard extends Board {

	public WordsBoard(WordsMatch wordsMatch) {
		this.match = wordsMatch;
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
		return "Albacete, Cadiz, Cuenca";
	}

	@Override
	public boolean draw() {
		return false;
	}

}
