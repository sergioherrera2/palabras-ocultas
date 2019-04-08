package edu.uclm.esi.games.model;

import java.util.concurrent.ConcurrentHashMap;

import edu.uclm.esi.games.web.Manager;

public abstract class Game {
	protected ConcurrentHashMap<String, Match> pendingMatches;
	
	public Game() {
		this.pendingMatches=new ConcurrentHashMap<>();
	}

	public abstract String getName();

	public Match getMatch(AbstractPlayer player) {
		Match match;
		if (this.pendingMatches.size()==0) {
			match=createMatch();
			match.addPlayer(player);
			pendingMatches.put(match.getIdMatch(), match);
		} else {
			match=this.pendingMatches.elements().nextElement();
			match.addPlayer(player);
			if (match.isComplete()) {
				match=this.pendingMatches.remove(match.getIdMatch());
				Manager.get().addInPlayMatch(match);
				match.calculateFirstPlayer();
			}
		}
		return match;
	}
	
	public ConcurrentHashMap<String, Match> getPendingMatches() {
		return pendingMatches;
	}

	protected abstract Match createMatch();
	
	public void removePendingMatch(String idMatch) {
		this.pendingMatches.remove(idMatch);
	}
}
