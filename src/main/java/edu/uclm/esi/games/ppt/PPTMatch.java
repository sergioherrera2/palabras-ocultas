package edu.uclm.esi.games.ppt;

import edu.uclm.esi.games.model.AbstractPlayer;
import edu.uclm.esi.games.model.Game;
import edu.uclm.esi.games.model.Match;

public class PPTMatch extends Match {
	
	public PPTMatch(Game game) {
		super(game);
		this.board=new PPTBoard(this);
	}

	@Override
	public void calculateFirstPlayer() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean tieneElTurno(AbstractPlayer player) {
		return true;
	}

}
