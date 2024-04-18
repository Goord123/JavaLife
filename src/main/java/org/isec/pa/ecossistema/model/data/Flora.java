package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.Coords;
import org.isec.pa.ecossistema.utils.ElementoEnum;

public class Flora extends Ecossistema implements IElemento {

    private static int lastId = 0; // Static variable to keep track of the last ID used
    private final int id;
    private final ElementoEnum elementoEnum = ElementoEnum.FLORA;
    private double hp = 50;
    private int size;
    private int timesReproduced = 0;
    private Coords coords;

    public Flora(int size) {
        this.id = ++lastId;
        this.size = size;
        this.coords = new Coords(0,0);
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
    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
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
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTimesReproduced() {
        return timesReproduced;
    }

    // METODOS

    public void reproduce() {
        if (this.hp >= 90 && this.timesReproduced < 2) {
            this.timesReproduced++;
            this.hp = 60;
        }
    }

    public void isBeingSteppedOn() {
        // ir ao ecoSistemaData e ver se há fauna a pisar
        // se sim, diminuir hp
        // temos que ver como é que vai funcionar o sistema de relogio da aplicacao
    }
}
