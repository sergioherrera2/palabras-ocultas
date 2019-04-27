package edu.uclm.esi.words;

import java.util.Random;

import edu.uclm.esi.games.kuar.KuarBoard;
import edu.uclm.esi.games.model.AbstractPlayer;
import edu.uclm.esi.games.model.Board;
import edu.uclm.esi.games.model.Game;
import edu.uclm.esi.games.model.Match;

public class WordsMatch extends Match {
	// private WordsBoard boardA, boardB;

	public WordsMatch(Game game) {
		super(game);
		this.board = new WordsBoard(this);
	}

//	@Override
//	public void setBoard(Board board) {
//		super.setBoard(board);
//		WordsBoard theBoard = (WordsBoard) board;
//		this.boardA = new WordsBoard(theBoard.get_id(), theBoard.getRows(), theBoard.getContent());
//		this.boardB = new WordsBoard(theBoard.get_id(), theBoard.getRows(), theBoard.getContent());
//		this.boardA.setMatch(this);
//		this.boardB.setMatch(this);
//	}

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
