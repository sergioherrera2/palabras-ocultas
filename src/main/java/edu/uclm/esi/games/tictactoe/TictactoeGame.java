package edu.uclm.esi.games.tictactoe;

import edu.uclm.esi.games.model.Game;
import edu.uclm.esi.games.model.Match;

public class TictactoeGame extends Game {

	public TictactoeGame() {
		super();
	}

	@Override
	public String getName() {
		return "tictactoe";
	}

	@Override
	protected Match createMatch() {
		return new TictactoeMatch(this);
	}

}
