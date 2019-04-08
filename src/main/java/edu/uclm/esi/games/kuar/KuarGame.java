package edu.uclm.esi.games.kuar;

import java.util.UUID;

import edu.uclm.esi.games.model.Board;
import edu.uclm.esi.games.model.Game;
import edu.uclm.esi.games.model.Match;

public class KuarGame extends Game {
	private int rows;

	public KuarGame(int rows) {
		this.rows=rows;
	}

	@Override
	public String getName() {
		return "Kuar " + rows + "x" + rows;
	}

	@Override
	protected Match createMatch() {
		Board board=getRandomBoard(false);
		Match match = new KuarMatch(this);
		match.setBoard(board);
		return match;
	}
	
	public Board getRandomBoard(boolean testingMode) {
		UUID id=UUID.fromString("5c20a4ec4034f8ea0c53dc96");		
	    String contenido="0,0,5,7,4,1,3,6,2";
	    
	    KuarBoard board=new KuarBoard(id, 3, contenido);
		return board;
	}
}
