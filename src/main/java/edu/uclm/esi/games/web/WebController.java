package edu.uclm.esi.games.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uclm.esi.games.model.AbstractPlayer;
import edu.uclm.esi.games.model.Match;

@RestController
public class WebController {
	@GetMapping(value = "/getGames", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@CrossOrigin(origins = "*", allowCredentials = "true")
	public String getGames() {
		JSONObject jsoRespuesta = Manager.get().getGames();
		JSONObject resultado = new JSONObject();
		resultado.put("resultado", jsoRespuesta);
		resultado.put("type", "OK");
		return resultado.toString();
	}
	@CrossOrigin(origins = "*", allowCredentials = "true")
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String login(HttpSession session, @RequestBody Map<String, String> jso) throws Exception {
		String userName = jso.get("userName");
		System.out.println(userName);
		String pwd = jso.get("pwd");
		AbstractPlayer player = Manager.get().login(userName, pwd);
		session.setAttribute("player", player);
		player.setHttpSession(session);
		System.out.println(session);

		JSONObject resultado = new JSONObject();
		resultado.put("type", "OK");
		resultado.put("resultado", new JSONObject(new ObjectMapper().writeValueAsString(player)));
		return resultado.toString();
	}
	@CrossOrigin(origins = "*", allowCredentials = "true")
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> register(HttpSession session, @RequestBody Map<String, String> jso) throws Exception {
		String email = jso.get("email");
		String userName = jso.get("userName");
		String pwd1 = jso.get("pwd1");
		String pwd2 = jso.get("pwd2");
		AbstractPlayer player = Manager.get().register(email, userName, pwd1, pwd2);

		Map<String, Object> resultado = new HashMap<String, Object>();
		resultado.put("type", "OK");
		resultado.put("resultado", new ObjectMapper().writeValueAsString(player));
		return resultado;
	}

	@PostMapping(value = "/joinGame")
	@CrossOrigin(origins = "*", allowCredentials = "true")
	public Match joinGame(HttpSession session, String p) throws Exception {
		AbstractPlayer player = (AbstractPlayer) session.getAttribute("player");
		JSONObject jso = new JSONObject(p);
		String gameName = jso.getString("gameName");
		Match match = Manager.get().joinGame(player, gameName);
		return match;
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) throws Exception {
		AbstractPlayer player = (AbstractPlayer) session.getAttribute("player");
		JSONObject jsoRespuesta = Manager.get().logout(player);

		JSONObject resultado = new JSONObject();
		resultado.put("type", "OK");
		resultado.put("resultado", jsoRespuesta);
		return resultado.toString();
	}

	@ExceptionHandler(Exception.class)
	public Map<String, String> handleException(Exception ex) {
		Map<String, String> resultado = new HashMap<String, String>();
		resultado.put("type", "error");
		resultado.put("message", ex.getMessage());
		return resultado;
	}
}
