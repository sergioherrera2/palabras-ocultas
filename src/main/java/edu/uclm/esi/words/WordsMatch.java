package edu.uclm.esi.words;

import java.util.Random;

import edu.uclm.esi.games.model.AbstractPlayer;
import edu.uclm.esi.games.model.Game;
import edu.uclm.esi.games.model.Match;

public class WordsMatch extends Match {
	private WordsBoard boardA, boardB;

	public WordsMatch(Game game) {
		super(game);
		this.board = new WordsBoard(this);
		this.boardA = this.descolocar();
		this.boardB = this.descolocar();
		this.board = this.descolocar();
	}

	public WordsBoard descolocar() {
		WordsBoard result = new WordsBoard(this);
		for (int i = 0; i < 9; i++) {
			result.getWords()[i] = ((WordsBoard) this.board).getWords()[i];
		}

		Random dado = new Random();
		for (int i = 0; i < 90; i++) {
			int origen = dado.nextInt(9);
			int destino = dado.nextInt(9);
			String aux = result.getWords()[destino];
			result.getWords()[destino] = result.getWords()[origen];
			result.getWords()[origen] = aux;
		}
		return result;
	}

	@Override
	protected boolean tieneElTurno(AbstractPlayer player) {
		return true;
	}

	@Override
	public void calculateFirstPlayer() {
		boolean dado = new Random().nextBoolean();
		this.currentPlayer = dado ? 0 : 1;
	}

	public WordsBoard getBoardA() {
		return boardA;
	}

	public WordsBoard getBoardB() {
		return boardB;
	}

	public void setBoard(WordsBoard board) {
		for (int i = 0; i < board.getWords().length; i++) {
			((WordsBoard) this.board).getWords()[i] = board.getWords()[i];
		}
	}

	public void setBoardA(WordsBoard boardA) {
		for (int i = 0; i < boardA.getWords().length; i++) {
			this.boardA.getWords()[i] = boardA.getWords()[i];
		}
	}

	public void setBoardB(WordsBoard boardB) {
		for (int i = 0; i < boardB.getWords().length; i++) {
			this.boardB.getWords()[i] = boardB.getWords()[i];
		}
	}

	@Override
	public boolean isComplete() {
		if (this.playerA != null && this.playerB != null) {
			this.boardA.setPlayer(this.getPlayerA().getUserName());
			this.boardB.setPlayer(this.getPlayerB().getUserName());
		}

		return this.playerA != null && this.playerB != null;
	}
}
