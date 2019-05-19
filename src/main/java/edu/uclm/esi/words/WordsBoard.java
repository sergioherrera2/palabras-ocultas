package edu.uclm.esi.words;

import edu.uclm.esi.games.model.AbstractPlayer;
import edu.uclm.esi.games.model.Board;

public class WordsBoard extends Board {
	private String[] words;
	private String playerName;

	public String getPlayer() {
		return playerName;
	}

	public void setPlayer(String player) {
		this.playerName = player;
	}

	public WordsBoard(WordsMatch wordsMatch) {
		super(wordsMatch);
		this.words = new String[] { "Albacete", "Cadiz", "Cuenca", "Barcelona", "Madrid", "Ciudad Real", "Burgos",
				"Valencia", "Tenerife" };

	}

	@Override
	public void move(AbstractPlayer player, Integer[] coordinates) throws Exception {
		if (coordinates[0] == 9) {
			this.match.setWinner(player);
		}
	}

	@Override
	public AbstractPlayer getWinner() {
		return this.match.getWinner();
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
