package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngine;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

public class Inanimado extends Ecossistema implements IElemento{
    private static int lastId = 0;
    private final int id;
    private final ElementoEnum elementoEnum = ElementoEnum.INANIMADO;
    private Area area;

    public Inanimado() {
        this.id = ++lastId;
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
    public Area getArea() {
        return this.area;
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        // TODO dar só return ?
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
