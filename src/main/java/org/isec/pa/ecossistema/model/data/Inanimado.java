package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.io.Serial;

public non-sealed class Inanimado extends ElementoBase implements IElemento {
    @Serial
    private static final long serialVersionUID = 1L;
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

    public void setArea(Area area) {
        this.area = area;
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
}
