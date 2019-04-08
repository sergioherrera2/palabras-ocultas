package edu.uclm.esi.games.ws;

import org.springframework.web.socket.WebSocketSession;

import edu.uclm.esi.games.model.AbstractPlayer;

public class WSSession {
	private WebSocketSession session;
	private AbstractPlayer player;

	public WSSession(WebSocketSession session, AbstractPlayer player) {
		this.session=session;
		this.player=player;
	}

	public WebSocketSession getSession() {
		return this.session;
	}
	
	public String getId() {
		return this.session.getId();
	}

	public void setPlayer(AbstractPlayer player) {
		this.player=player;
	}

	public AbstractPlayer getPlayer() {
		return this.player;
	}
}