package edu.uclm.esi.games.kuar;

import java.util.UUID;

import org.json.JSONObject;

import edu.uclm.esi.games.model.AbstractPlayer;
import edu.uclm.esi.games.model.Board;
import edu.uclm.esi.games.model.Match;

public class KuarBoard extends Board {
	private int rows;
	private String content;
	
	public KuarBoard() {
		super();
	}

	public KuarBoard(Match match) {
		super(match);
	}

	public KuarBoard(UUID idBoard, int rows, String contenido) {
		this(null);
		this._id=idBoard;
		this.rows=rows;
		this.content=contenido;
	}

	@Override
	public void move(AbstractPlayer player, Integer[] coordinates) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AbstractPlayer getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean end() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	@Override
	public JSONObject toJSON() throws Exception {
		JSONObject jso=new JSONObject();
		jso.put("idBoard", this._id.toString()).put("type", this.getClass().getSimpleName()).put("rows", this.rows).put("content", this.getContent());
		return jso;
	}

	public void setContent(String content) {
		this.content=content;
	}

	public int getRows() {
		return this.rows;
	}

	@Override
	public boolean draw() {
		return false;
	}
}
