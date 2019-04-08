package edu.uclm.esi.games.web;

import java.util.Enumeration;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import edu.uclm.esi.games.dao.AbstractPlayerRepository;
import edu.uclm.esi.games.dao.BoardRepositoryCustom;
import edu.uclm.esi.games.dao.PlayerRepository;
import edu.uclm.esi.games.dao.TokenRepository;
import edu.uclm.esi.games.kuar.KuarGame;
import edu.uclm.esi.games.model.AbstractPlayer;
import edu.uclm.esi.games.model.Board;
import edu.uclm.esi.games.model.Game;
import edu.uclm.esi.games.model.Match;
import edu.uclm.esi.games.model.Player;
import edu.uclm.esi.games.model.SimplePlayer;
import edu.uclm.esi.games.model.Token;
import edu.uclm.esi.games.ppt.PPTGame;
import edu.uclm.esi.games.tictactoe.TictactoeGame;
import edu.uclm.esi.games.ws.WSServer;
import edu.uclm.esi.words.WordsGame;

@Component
public class Manager {
	private ConcurrentHashMap<Integer, Game> games;
	private ConcurrentHashMap<String, AbstractPlayer> players;
	protected ConcurrentHashMap<String, Match> inPlayMatches;
	
	@Autowired
	private AbstractPlayerRepository abstractplayersRepo;
	@Autowired
	private PlayerRepository playersRepo;
	@Autowired 
	private TokenRepository tokensRepo;
	@Autowired
	private BoardRepositoryCustom boardsRepo;
	
	private Manager() {
		this.inPlayMatches=new ConcurrentHashMap<>();
		games=new ConcurrentHashMap<>();
		Game s3x3=new KuarGame(3);
		games.put(1, s3x3);
		Game s4x4=new KuarGame(4);
		games.put(2, s4x4);
		Game s5x5=new KuarGame(5);
		games.put(3, s5x5);
		

		Game tictactoe=new TictactoeGame();
		games.put(10, tictactoe);
		Game ppt=new PPTGame();
		games.put(11, ppt);
		
		Game words=new WordsGame();
		games.put(20,words);

		this.players=new ConcurrentHashMap<>();
	}
	
	private static class ManagerHolder {
		static Manager singleton=new Manager();
	}
	
	@Bean
	public static Manager get() {
		return ManagerHolder.singleton;
	}

	public Match joinGame(AbstractPlayer player, int idGame) throws Exception {
		Game game=this.games.get(idGame);
		Match match = game.getMatch(player);
		return match;
	}
	
	public Match joinGame(AbstractPlayer player, String gameName) throws Exception {
		Enumeration<Game> games=this.games.elements();
		while (games.hasMoreElements()) {
			Game game=games.nextElement();
			if (game.getName().equals(gameName)) {
				Match match = game.getMatch(player);
				if (match.isComplete())
					WSServer.startMatch(match);
				return match;
			}
		}
		return null;
	}

	public JSONObject getGames() {
		JSONArray jsa=new JSONArray();
		Enumeration<Game> eGames = games.elements();
		while (eGames.hasMoreElements())
			jsa.put(eGames.nextElement().getName());
		return new JSONObject().put("games", jsa);
	}

	public Match move(String idMatch, AbstractPlayer player, JSONArray coordinates) throws Exception {
		Integer[] iC=new Integer[coordinates.length()];
		for (int i=0; i<iC.length; i++)
			iC[i]=coordinates.getInt(i);
		Match match=this.inPlayMatches.get(idMatch);
		match.move(player, iC);
		return match;
	}

	public JSONObject logout(AbstractPlayer player) {
		JSONObject jso=new JSONObject();
		this.userLeaves(player.getUserName());
		return jso;
	}

	public void addPlayer(AbstractPlayer player) {
		this.players.put(player.getUserName(), player);
	}

	public AbstractPlayer getPlayer(String userName) {
		return this.players.get(userName);
	}

	public void remove(String userName) {
		this.players.remove(userName);
	}
	
	public AbstractPlayer login(String userName, String pwd) throws Exception {
		if (userName.length()==0 || pwd.length()==0)
			throw new Exception("Credenciales inválidas");
		AbstractPlayer player=playersRepo.findByUserNameAndPwd(userName, pwd);
		if (player==null)
			throw new Exception("Credenciales inválidas");
		
		AbstractPlayer yaConectado=this.players.get(userName);
		if (yaConectado!=null) 
			return yaConectado;
		this.players.put(userName, player);
		return player;
	}

	public AbstractPlayer login(String userName) throws Exception {
		AbstractPlayer player=abstractplayersRepo.findByUserName(userName);
		if (player==null)
			throw new Exception("Credenciales inválidas");
		this.players.put(userName, player);
		return player;
	}
	
	public AbstractPlayer register(String email, String userName, String pwd1, String pwd2) throws Exception {
		if (!pwd1.equals(pwd2))
			throw new Exception("Error: las contraseñas no coinciden");
		Player player=new Player();
		player.setEmail(email);
		player.setUserName(userName);
		player.setPwd(pwd1);
		abstractplayersRepo.save(player);
		return player;
	}

	public Token requestToken(String userName) throws Exception {
		Token token=new Token(userName);
		tokensRepo.save(token);
		return token;
	}

	public AbstractPlayer resetPwd(String userName, String pwd, int idToken) throws Exception {
		Token token=tokensRepo.findByUserNameAndId(userName, idToken);
		if (token==null)
			throw new Exception("Invalid token");
		AbstractPlayer player=abstractplayersRepo.updatePwd(userName, pwd);
		return player;
	}

	public AbstractPlayer simpleRegister(String userName) throws Exception {
		AbstractPlayer player=new SimplePlayer();
		player.setUserName(userName);
		abstractplayersRepo.save(player);
		return player;
	}

	public Board getRandomBoard(int idGame, boolean testingMode) throws Exception {
		Game game=this.games.get(idGame);
		//Board board=boardsRepo.getRandomBoard(game.getName(), testingMode);
		return null;
	}

	public void boardFinished(int idGame, String userName, int seconds, int movements, int level, UUID idBoard) throws Exception {
		Board board=boardsRepo.findById(idBoard.toString()).get();
		board.increaseTimesPlayed();
		if (seconds<board.getBestTime()) 
			board.setBestTime(seconds);
		if (movements<board.getBestMovements()) 
			board.setBestMovements(movements);
		boardsRepo.save(board);
	}

	public void updateSimpleUser(String oldName, String newName) throws Exception {
		abstractplayersRepo.changeUserName(oldName, newName);
	}

	public void addInPlayMatch(Match match) {
		inPlayMatches.put(match.getIdMatch(), match);
	}

	public void removeInPlayMatch(String id) {
		inPlayMatches.remove(id);
	}

	public ConcurrentHashMap<String, AbstractPlayer> getPlayers() {
		return players;
	}
	
	public ConcurrentHashMap<String, Match> getInPlayMatches() {
		return inPlayMatches;
	}
	
	public Vector<Match> getPendingMatches() {
		Vector<Match> matches=new Vector<>();
		Enumeration<Integer> keys = this.games.keys();
		Integer key;
		while (keys.hasMoreElements()) {
			key=keys.nextElement();
			Game game=this.games.get(key);
			Enumeration<Match> gameMatches = game.getPendingMatches().elements();
			while (gameMatches.hasMoreElements())
				matches.add(gameMatches.nextElement());
		}
		return matches;
	}

	public AbstractPlayer leaveMatch(String idMatch, String userName) throws Exception {
		Match match=getInPlayMatches().get(idMatch);
		removeInPlayMatch(idMatch);
		
		AbstractPlayer winner, looser;
		if (match.getPlayerA().getUserName().equals(userName)) {
			winner=match.getPlayerB();
			looser=match.getPlayerA();
		} else {
			winner=match.getPlayerA();
			looser=match.getPlayerB();
		}
		winner.increaseVictoriesDefeatsWithdrawals(1, 0, 0);
		looser.increaseVictoriesDefeatsWithdrawals(0, 1, 1);
		abstractplayersRepo.save(winner);
		abstractplayersRepo.save(looser);
		winner.setCurrentMatch(null);
		looser.setCurrentMatch(null);
		return winner;
	}

	public AbstractPlayer[] victory(String idMatch, String userName) throws Exception {
		Match match=getInPlayMatches().get(idMatch);
		AbstractPlayer[] wl=new AbstractPlayer[2];
		removeInPlayMatch(idMatch);
		
		AbstractPlayer winner, looser;
		if (match.getPlayerA().getUserName().equals(userName)) {
			winner=match.getPlayerA();
			looser=match.getPlayerB();
		} else {
			winner=match.getPlayerB();
			looser=match.getPlayerA();
		}
		winner.increaseVictoriesDefeatsWithdrawals(1, 0, 0);
		looser.increaseVictoriesDefeatsWithdrawals(0, 1, 0);
		wl[0]=winner;
		wl[1]=looser;
		return wl;
	}

	public void userLeaves(String userName) {
		this.players.remove(userName);
	}

	public void leaveWaitingArea(String userName, int idGame, String idMatch) {
		Game game=this.games.get(idGame);
		game.removePendingMatch(idMatch);
		AbstractPlayer player = this.players.get(userName);
		player.setCurrentMatch(null);
	}

	public void leaveWaitingArea(String userName, String gameName, String idMatch) {
		Enumeration<Game> eGames = this.games.elements();
		while (eGames.hasMoreElements()) {
			Game game=eGames.nextElement();
			if (game.getName().equals(gameName)) {
				game.removePendingMatch(idMatch);
				AbstractPlayer player = this.players.get(userName);
				player.setCurrentMatch(null);
				break;
			}
		}
	}
}
