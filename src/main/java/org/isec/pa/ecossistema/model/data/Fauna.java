package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.Coords;
import org.isec.pa.ecossistema.utils.DirectionEnum;
import org.isec.pa.ecossistema.utils.ElementoEnum;

public class Fauna extends Ecossistema implements IElemento{

    private static int lastId = 0; // Static variable to keep track of the last ID used
    private final int id;
    private final ElementoEnum elementoEnum = ElementoEnum.FAUNA;
    private double hp = 50;
    private int velocity;
    private DirectionEnum direction;
    private Coords coords;
    private int size;
    private Coords target;
    private int timesReproduced = 0;
    private int segundosParaReproduzir;

    public Fauna() {
        this.id = ++lastId;
        this.velocity = 0;
        this.coords = new Coords(0,0);
        this.target = new Coords(0,0);
        this.segundosParaReproduzir = 0;
    }
    public Fauna(int velocity) {
        this.id = ++lastId;
        this.velocity = velocity;
        this.coords = new Coords(0,0);
        this.target = new Coords(0,0);
        this.segundosParaReproduzir = 0;
    }

    // GETTERS E SETTERS
    @Override
    public int getId() {
        return id;
    }
    @Override
    public ElementoEnum getElemento() {
        return elementoEnum;
    }
    @Override
    public Coords getCoords() {
        return coords;
    }
    @Override
    public void setCoords(Coords coords) {
        this.coords = coords;
    }
    @Override
    public Field getField() {
        return super.getField();
    }
    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
    }

    public Coords getTarget() {
        return target;
    }

    public void setTarget(Coords target) {
        this.target = target;
    }

    public int getTimesReproduced() {
        return timesReproduced;
    }

    public int getSegundosParaReproduzir() {
        return segundosParaReproduzir;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void incSegundosParaReproduzir() {
        this.segundosParaReproduzir++;
        if (this.segundosParaReproduzir == 10) {
            this.segundosParaReproduzir = 0;
            this.reproduce();
        }
    }


    // METODOS

    public void move() {
        // TODO
    }
    public void reproduce() {
        this.timesReproduced++;
        if (this.hp >= 25) {
            this.die();
        }
        else
            this.hp -= 25;
    }

    public void die() {
        // TODO
    }




}
