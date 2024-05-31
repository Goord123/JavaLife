package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

public final class Inanimado extends ElementoBase implements IElemento {
    private static int lastId = 0;
    private final ElementoEnum elementoEnum = ElementoEnum.INANIMADO;
    private int id;
    private Area area;
    private boolean isBarreira;

    public Inanimado() {
        this.id = ++lastId;
        this.isBarreira = false;
    }

    // GETTERS E SETTERS
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public ElementoEnum getElemento() {
        return elementoEnum;
    }

    @Override
    public Area getArea() {
        return this.area;
    }

    public boolean isBarreira() {
        return isBarreira;
    }

    public void setBarreira(boolean barreira) {
        isBarreira = barreira;
    }

    @Override
    public void evolve() {

    }

    public void setArea(Area area) {
        this.area = area;
    }
}
