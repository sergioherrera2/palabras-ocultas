package edu.uclm.esi.games.tictactoe;

import java.util.Random;

import edu.uclm.esi.games.model.Game;
import edu.uclm.esi.games.model.Match;
import edu.uclm.esi.games.model.AbstractPlayer;

public class TictactoeMatch extends Match {
	public TictactoeMatch(Game game) {
		super(game);
		this.board=new TictactoeBoard(this);
	}

	@Override
	public void calculateFirstPlayer() {
		boolean dado=new Random().nextBoolean();
		this.currentPlayer=dado ? 0 : 1;
		this.currentPlayer=0;   // puesto a propósito con fines de desarrollo y de test para que empiece el primer jugador
	}

	@Override
	protected boolean tieneElTurno(AbstractPlayer player) {
		return (this.getCurrentPlayer()==0 && player==this.playerA) || (this.getCurrentPlayer()==1 && player==playerB);
	}
}
