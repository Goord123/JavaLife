package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.io.Serializable;

public non-sealed class Inanimado extends ElementoBase implements IElemento, Serializable {
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

    public void setArea(Area area) {
        this.area = area;
    }
}
