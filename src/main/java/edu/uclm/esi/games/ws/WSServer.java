package edu.uclm.esi.games.ws;

import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uclm.esi.games.model.AbstractPlayer;
import edu.uclm.esi.games.model.Board;
import edu.uclm.esi.games.model.Match;
import edu.uclm.esi.games.web.Manager;

@Component
public class WSServer extends TextWebSocketHandler {
	private static Sessions sessions=new Sessions();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		System.out.println(session.getId());
		try {
			Map<String, Object> map = session.getAttributes();
			AbstractPlayer player=(AbstractPlayer) map.get("player");
			WSSession wsSession=new WSSession(session, player);
			sessions.add(wsSession);
			Sessions session_prueba=sessions;
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage msg) { 
		try {
			JSONObject jso=new JSONObject(msg.getPayload());
			String type=jso.getString("type");
			
			switch (type) {
				case "SimpleLogin" :
					simpleLogin(session, jso);
					break;
				case "ClassicLogin" :
					classicLogin(session, jso);
					break;
				case "Logout" :
					logout(session, jso);
					break;
				case "SimpleRegister" :
					simpleRegister(session, jso);
					simpleLogin(session, jso);
					break;
				case "ClassicRegister" :
					classicRegister(session, jso);
					break;
				case "DeletetUser" :
					break;
				case "UpdateSimpleUser" :
					updateSimpleUser(session, jso);
					break;
				case "Movement" :
					move(session, jso);
					break;
				case "Victory" :
					victory(session, jso);
					break;
				case "LeaveMatch" :
					leaveMatch(session, jso);
					break;
				case "LeaveWaitingArea" :
					leaveWaitingArea(session, jso);
					break;
				case "GetRandomBoard" :
					getRandomBoard(session, jso);
					break;
				case "JoinGame":
					joinGame(session, jso);
					break;
				case "BoardFinished" :
					boardFinished(session, jso);
					break;
			}
		} catch (Exception e) {
			send(session, e);
		}
	}

	private void classicRegister(WebSocketSession session, JSONObject jso) throws Exception {
		String userName=jso.getString("userName");
		String email=jso.getString("email");
		String pwd1=jso.getString("pwd1");
		String pwd2=jso.getString("pwd2");
		Manager.get().register(email, userName, pwd1, pwd2);
		send(session, new JSONObject().put("type", "RegisterResponse").put("name", userName));
	}

	private void leaveWaitingArea(WebSocketSession session, JSONObject jso) {
		String userName=jso.getString("userName");
		int idGame=jso.getInt("idGame");
		String idMatch=jso.getString("idMatch");
		Manager.get().leaveWaitingArea(userName, idGame, idMatch);		
	}

	private void victory(WebSocketSession session, JSONObject jso) throws Exception {
		String idMatch=jso.getString("idMatch");
		String userName=jso.getString("userName");
		
		AbstractPlayer[] wl=Manager.get().victory(idMatch, userName);

		JSONObject js=new JSONObject();
		js.put("type", "YouWin").put("name", wl[1].getUserName());
		WSSession wsSession=WSServer.sessions.findByUserName(wl[0].getUserName());
		send(wsSession.getSession(), js);
		
		js.put("type", "YouLost").put("name", wl[0].getUserName());
		wsSession=WSServer.sessions.findByUserName(wl[1].getUserName());
		send(wsSession.getSession(), js);
	}

	private void updateSimpleUser(WebSocketSession session, JSONObject jso) throws Exception {
		String oldName=jso.getString("oldName");
		String newName=jso.getString("newName");
		Manager.get().updateSimpleUser(oldName, newName);
	}

	private void boardFinished(WebSocketSession session, JSONObject jso) throws Exception {
		int idGame=jso.getInt("idGame");
		String userName=jso.optString("userName");
		if (userName.length()==0)
			userName="Anonymous";
		int seconds=jso.getInt("seconds");
		int movements=jso.getInt("movements");
		int level=jso.getInt("level");
		UUID idBoard=UUID.fromString(jso.getString("idBoard"));
		Manager.get().boardFinished(idGame, userName, seconds, movements, level, idBoard);
	}

	private void getRandomBoard(WebSocketSession session, JSONObject jso) throws Exception {
		int idGame=jso.getInt("idGame");
		boolean testingMode=jso.optBoolean("testingMode");
		Board board=Manager.get().getRandomBoard(idGame, testingMode);
		ObjectMapper mapper = new ObjectMapper();
		jso=new JSONObject(mapper.writeValueAsString(board));
		jso.put("type", board.getClass().getSimpleName());
		send(session, jso);
	}

	private void simpleRegister(WebSocketSession session, JSONObject jso) throws Exception {
		Manager.get().simpleRegister(jso.getString("userName"));
	}

	private void joinGame(WebSocketSession session, JSONObject jso) throws Exception {
		String userName=jso.getString("userName");
		String gameName=jso.optString("gameName");		
		int idGame=jso.optInt("idGame");
		AbstractPlayer player=Manager.get().getPlayer(userName);
		if (player==null)
			throw new Exception("You need to be registered");
		
		Match match;
		if (gameName.length()>0) {
			match=Manager.get().joinGame(player, gameName);
		} else 
			match = Manager.get().joinGame(player, idGame);
		if (match.isComplete()) {
			WSServer.startMatch(match);
			return;
		}
		JSONObject jsoRespuesta=new JSONObject();
		jsoRespuesta.put("type", "WaitingMatch");
		jsoRespuesta.put("idMatch", match.getIdMatch());
		send(session, jsoRespuesta);
	}

	private void simpleLogin(WebSocketSession session, JSONObject jso) throws Exception {
		String userName=jso.getString("userName");
		AbstractPlayer player=Manager.get().login(userName);
		sessions.update(session, player);
		send(session, new JSONObject().put("type", "LoginResponse").put("name", userName));
	}
	
	private void classicLogin(WebSocketSession session, JSONObject jso) throws Exception {
		String userName=jso.optString("userName");
		String pwd=jso.optString("pwd");
		AbstractPlayer player=Manager.get().login(userName, pwd);
		sessions.update(session, player);
		send(session, new JSONObject().put("type", "LoginResponse").put("name", userName));
	}

	private void move(WebSocketSession session, JSONObject jso) {
		String idMatch=jso.optString("idMatch");
		JSONArray coordinates=jso.getJSONArray("coordinates");
		try {
			WSSession wsSession=sessions.find(session);
			AbstractPlayer player=(AbstractPlayer) wsSession.getPlayer();
			Match match;
			if (idMatch.length()==0) {
				match=player.getCurrentMatch();
				Manager.get().move(match.getIdMatch(), player, coordinates);
			} else
				match=Manager.get().move(idMatch, player, coordinates);

			JSONObject jsoMovement=new JSONObject();
			jsoMovement.put("type", "Movement");
			jsoMovement.put("coordinates", coordinates);
			jsoMovement.put("mover", player.getUserName());
			if (match.getWinner()!=null)
				jsoMovement.put("winnerName", match.getWinner().getUserName());
			jsoMovement.put("currentPlayerUserName", match.getCurrentPlayerUserName());
			jsoMovement.put("draw", match.getDraw());
			
			AbstractPlayer opponent=match.getPlayerA()==player ? match.getPlayerB() : match.getPlayerA();
			WSSession wsOpponentSession = sessions.findByUserName(opponent.getUserName());
			send(wsOpponentSession.getSession(), jsoMovement);
			send(wsSession.getSession(), jsoMovement);
		}
		catch (Exception e) {
			send(session, e);
		}
	}

	public static void startMatch(Match match) {
		AbstractPlayer playerA=match.getPlayerA();
		AbstractPlayer playerB=match.getPlayerB();
		WSSession sessionA=sessions.findByUserName(playerA.getUserName());
		WSSession sessionB=sessions.findByUserName(playerB.getUserName());
		try {
			JSONObject jso=new JSONObject(new ObjectMapper().writeValueAsString(match));
			jso.put("type", "Match");
			send(sessionA.getSession(), jso);
			send(sessionB.getSession(), jso);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void logout(WebSocketSession session, JSONObject jso) {
		String userName=jso.optString("userName");
		if (userName!=null) {
			Manager.get().userLeaves(userName);
		}
		//close(session);
	}
	
	private void leaveMatch(WebSocketSession session, JSONObject jso) throws Exception {
		String idMatch=jso.getString("idMatch");
		String userName=jso.getString("userName");
		
		AbstractPlayer winner=Manager.get().leaveMatch(idMatch, userName);
		
		JSONObject jsWinner=new JSONObject();
		jsWinner.put("type", "OpponentLeft").put("name", userName);
		WSSession wsSession=WSServer.sessions.findByUserName(winner.getUserName());
		send(wsSession.getSession(), jsWinner);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		try {
			session.close();
		} catch (Throwable e) {
		}
		WSSession wsLooserSession = sessions.remove(session);
		if (wsLooserSession!=null && wsLooserSession.getPlayer()!=null) {
			AbstractPlayer looser=wsLooserSession.getPlayer();
			Match match=looser.getCurrentMatch();
			if (match!=null) {
				if (match.isComplete()) {
					AbstractPlayer winner;
					try {
						winner = Manager.get().leaveMatch(match.getIdMatch(), looser.getUserName());
						JSONObject jsWinner=new JSONObject();
						jsWinner.put("type", "OpponentLeft").put("name", looser.getUserName());
						WSSession wsSession=WSServer.sessions.findByUserName(winner.getUserName());
						send(wsSession.getSession(), jsWinner);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Manager.get().leaveWaitingArea(looser.getUserName(), looser.getCurrentMatch().getGameName(), looser.getCurrentMatch().getIdMatch());	
				}
			}
		}
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable t) throws Exception {
		System.out.println(session.getId() + "-> " + t.getMessage());
	}
	
	public static synchronized void send(WebSocketSession session, JSONObject message) {
		try {
			synchronized (session) {
				if (session.isOpen())
					session.sendMessage(new TextMessage(message.toString()));
			}
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static synchronized void send(WebSocketSession session, String message) {
		if (message==null)
			message="Problema en el servidor";
		try {
			synchronized (session) {
				if (session.isOpen())
					session.sendMessage(new TextMessage(message.toString()));
			}
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static synchronized void send(WebSocketSession session, Exception e) {
		JSONObject jso=new JSONObject();
		jso.put("type", "Error");
		jso.put("message", e.getMessage());
		try {
			synchronized (session) {
				if (session.isOpen())
					session.sendMessage(new TextMessage(jso.toString()));
			}
		}
		catch (Exception se) {
			System.err.println(se.getMessage());
		}
	}

	public static Sessions getSessions() {
		return sessions;
	}
}
