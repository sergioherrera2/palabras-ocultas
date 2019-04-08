package edu.uclm.esi.games.kuar;

public class Pair {
    public int row, col;

    @Override
    public boolean equals(Object o) {
        Pair p=(Pair) o;
        return (p.row==this.row && p.col==this.col);
    }
}