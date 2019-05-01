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
	}

	private WordsBoard descolocar() {
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
}
