package edu.uclm.esi.words;

import edu.uclm.esi.games.model.Game;
import edu.uclm.esi.games.model.Match;

public class WordsGame extends Game {

	@Override
	public String getName() {
		return "Hidden words";
	}

	@Override
	protected Match createMatch() {
		return new WordsMatch(this);
	}

}
