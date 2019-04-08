package edu.uclm.esi.words;

import java.util.Random;

import edu.uclm.esi.games.model.AbstractPlayer;
import edu.uclm.esi.games.model.Game;
import edu.uclm.esi.games.model.Match;

public class WordsMatch extends Match {

	public WordsMatch(Game game) {
		super(game);
		this.board = new WordsBoard(this);
	}

	@Override
	protected boolean tieneElTurno(AbstractPlayer player) {
		return (this.getCurrentPlayer() == 0 && player == this.playerA)
				|| (this.getCurrentPlayer() == 1 && player == playerB);
	}

	@Override
	public void calculateFirstPlayer() {
		boolean dado = new Random().nextBoolean();
		this.currentPlayer = dado ? 0 : 1;
	}

}
