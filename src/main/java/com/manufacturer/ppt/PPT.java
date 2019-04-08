package com.manufacturer.ppt;

public class PPT {
	private String playerA, playerB;
	private final static int PIEDRA = 1;
	private final static int PAPEL = 2;
	private final static int TIJERA = 3;
	private int tiradaA, tiradaB;
	private boolean finished;
 
	public PPT(String playerA, String playerB) {
		this.playerA=playerA;
		this.playerB=playerB;
	}
	
	public String play(String player, int tirada) {
		if (player==playerA)
			tiradaA=tirada;
		else
			tiradaB=tirada;
		if (tirada!=0 && tiradaB!=0) {
			this.finished=true;
			if (tiradaA==PIEDRA) {
				if (tiradaB==PIEDRA)
					return null;
				if (tiradaB==PAPEL)
					return playerB;
				return playerA;
			}
			if (tiradaA==PAPEL) {
				if (tiradaB==PIEDRA)
					return playerA;
				if (tiradaB==PAPEL)
					return null;
				return playerB;
			}
			if (tiradaA==TIJERA) {
				if (tiradaB==PIEDRA)
					return playerB;
				if (tiradaB==PAPEL)
					return playerA;
				return null;
			}
		}
		return null;
	}
	
	public boolean isFinished() {
		return finished;
	}
}
