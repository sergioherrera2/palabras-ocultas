<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@sepage import="edu.uclm.esi.jsoner.JSONer"%>
<%@ page import="org.json.*, edu.uclm.esi.web.Manager, 
		edu.uclm.esi.games.model.*" %>

<%
String p=request.getParameter("p");
JSONObject resultado=new JSONObject();
try {
	if (!request.getMethod().equals("GET"))
		throw new Exception("Método no soportado");
	JSONObject jso=new JSONObject(p);
	
	if (!jso.getString("type").equals("Login")) {
		resultado.put("type", "error");
		resultado.put("message", "Mensaje inesperado");
	} else {
		String userName=jso.getString("userName");
		String pwd=jso.getString("pwd");
		AbstractPlayer player=Manager.get().login(userName, pwd);
		session.setAttribute("player", player);
		JSONObject jsoRespuesta=JSONer.toJSON(player);
		resultado.put("resultado", jsoRespuesta);
		resultado.put("type", "OK");
	}
}
catch (Exception e) {
	resultado.put("type", "error");
	resultado.put("message", e.getMessage());
}
%>

<%= resultado.toString() %>
