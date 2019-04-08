package edu.uclm.esi.games.ppt;

import edu.uclm.esi.games.model.Game;
import edu.uclm.esi.games.model.Match;

public class PPTGame extends Game {
	
	public PPTGame() {
		super();
	}

	@Override
	public String getName() {
		return "PPT";
	}

	@Override
	protected Match createMatch() {
		return new PPTMatch(this);
	}

}
