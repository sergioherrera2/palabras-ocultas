package edu.uclm.esi.games.kuar;

import edu.uclm.esi.games.model.AbstractPlayer;
import edu.uclm.esi.games.model.Board;
import edu.uclm.esi.games.model.Game;
import edu.uclm.esi.games.model.Match;

public class KuarMatch extends Match {
	private KuarBoard boardA, boardB;
	
	public KuarMatch(Game game) {
		super(game);
	}

	@Override
	protected boolean tieneElTurno(AbstractPlayer player) {
		return true;
	}
	
	@Override
	public void setBoard(Board board) {
		super.setBoard(board);
		KuarBoard theBoard=(KuarBoard) board;
		this.boardA=new KuarBoard(theBoard.get_id(), theBoard.getRows(), theBoard.getContent());
		this.boardB=new KuarBoard(theBoard.get_id(), theBoard.getRows(), theBoard.getContent());
		this.boardA.setMatch(this);
		this.boardB.setMatch(this);
	}

	@Override
	public void calculateFirstPlayer() {
		// TODO Auto-generated method stub
		
	}
}
