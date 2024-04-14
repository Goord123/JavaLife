package org.isec.pa.ecossistema.utils;

public class Coords {
    private int x;
    private int y;


    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void update(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
}
