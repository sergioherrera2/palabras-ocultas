package edu.uclm.esi.games.model;

import java.util.UUID;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.uclm.esi.games.web.Manager;


@Entity(name="matchs")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="game")
public abstract class Match {
	@Id
	protected String idMatch;
	@ManyToOne @JoinColumn(name = "player_a") 
	protected AbstractPlayer playerA;
	@ManyToOne @JoinColumn(name = "player_b")
	protected AbstractPlayer playerB;
	@Transient
	protected Integer currentPlayer;
	@ManyToOne
	protected AbstractPlayer winner;
	@ManyToOne @JoinColumn(name = "id_board") 
	protected Board board;
	private boolean draw;
	@JsonIgnore @Transient 
	private Game game;
	
	public Match(Game game) {
		this.game=game;
		this.idMatch=UUID.randomUUID().toString();
		this.currentPlayer=-1;
	}
	
	public String getIdMatch() {
		return idMatch;
	}

	public void addPlayer(AbstractPlayer player) {
		if (this.playerA==null)
			this.playerA=player;
		else 
			this.playerB=player;
		player.setCurrentMatch(this);
	}
	
	public AbstractPlayer getPlayerA() {
		return playerA;
	}

	public AbstractPlayer getPlayerB() {
		return playerB;
	}

	public Board getBoard() {
		return board;
	}
	
	public AbstractPlayer getWinner() {
		return winner;
	}
	
	public String getWinnerName() {
		return winner==null ? null : winner.getUserName();
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public String getCurrentPlayerUserName() {
		if (!isComplete())
			return null;
		return currentPlayer==0 ? this.playerA.getUserName() : this.playerB.getUserName();
	}
	
	public Game getGame() {
		return game;
	}
	
	public String getGameName() {
		return game.getName();
	}

	public Match move(AbstractPlayer player, Integer[] coordinates) throws Exception {
		if (!tieneElTurno(player))
			throw new Exception("No tienes el turno");
		if (this.board.end())
			throw new Exception("La partida ha terminado");
		this.board.move(player, coordinates);
		this.winner=this.board.getWinner();
		if (winner!=null) {
			this.playerA.setCurrentMatch(null);
			this.playerB.setCurrentMatch(null);
			Manager.get().removeInPlayMatch(this.getIdMatch());
		} else if (this.board.draw()) {
			this.playerA.setCurrentMatch(null);
			this.playerB.setCurrentMatch(null);
			Manager.get().removeInPlayMatch(this.getIdMatch());
			this.draw=true;
		}
		this.currentPlayer=(this.currentPlayer+1) % 2;
		return this;
	}
	
	protected abstract boolean tieneElTurno(AbstractPlayer player);

	public abstract void calculateFirstPlayer();

	public void setWinner(AbstractPlayer player) {
		this.winner=player;
	}

	public boolean isComplete() {
		return this.playerA!=null && this.playerB!=null;
	}

	public void setBoard(Board board) {
		this.board=board;
	}

	public boolean getDraw() {
		return this.draw;
	}
}
