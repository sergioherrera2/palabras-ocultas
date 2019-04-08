package edu.uclm.esi.games.ppt;

import edu.uclm.esi.games.model.AbstractPlayer;
import edu.uclm.esi.games.model.Board;

public class PPTBoard extends Board {
	private final static Integer PIEDRA = 0;
	private final static Integer PAPEL = 1;
	private final static Integer TIJERA = 2;
	private Integer[] tiradas0, tiradas1;
	
	public PPTBoard(PPTMatch pptMatch) {
		super(pptMatch);
		this.tiradas0 = new Integer[] { -1, -1, -1};
		this.tiradas1 = new Integer[] { -1, -1, -1};
	}

	@Override
	public void move(AbstractPlayer player, Integer[] coordinates) throws Exception {
		if (this.match.getCurrentPlayer()==0) {
			rellenar(tiradas0, coordinates[0]);
		} else {
			rellenar(tiradas1, coordinates[0]);
		}
	}

	private int rellenar(Integer[] tiradas, int valor) {
		for (int i=0; i<tiradas.length; i++)
			if (tiradas[i]==-1) {
				tiradas[i]=valor;
				return i;
			}
		return -1;
	}

	@Override
	public AbstractPlayer getWinner() {
		for (int i=0; i<tiradas0.length; i++)
			if (tiradas0[i]==-1 || tiradas1[i]==-1)
				return null;
		
		return gana(tiradas0, tiradas1);
	}

	private AbstractPlayer gana(Integer[] a, Integer[] b) {
		int victoriasA=0, victoriasB=0;
		for (int i=0; i<a.length; i++) {
			if (gana(a[i], b[i]))
				victoriasA++;
			else
				victoriasB++;
		}
		return victoriasA>victoriasB ? this.match.getPlayerA() : this.match.getPlayerB();
	}

	private boolean gana(int a, int b) {
		if (a==PIEDRA && b==TIJERA)
			return true;
		if (a==TIJERA && b==PAPEL)
			return true;
		if (a==PAPEL && b==PIEDRA)
			return true;
		return false;
	}

	public Integer[] getTiradas0() {
		return tiradas0;
	}
	
	public Integer[] getTiradas1() {
		return tiradas1;
	}

	@Override
	public boolean end() {
		if (this.getWinner()!=null)
			return true;
		for (int i=0; i<tiradas0.length; i++)
			if (tiradas0[i]==-1 || tiradas1[i]==-1)
				return false;
		return true;
	}

	@Override
	public String getContent() {
		String r="tiradas0: ";
		for (int i=0; i<tiradas0.length; i++)
			r+=tiradas0[i] + " - ";
		r="\ntiradas1: ";
		for (int i=0; i<tiradas1.length; i++)
			r+=tiradas1[i] + " - ";
		return r;
	}

	@Override
	public boolean draw() {
		// TODO Auto-generated method stub
		return false;
	}
}
