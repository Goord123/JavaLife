package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.Coords;
import org.isec.pa.ecossistema.utils.ElementoEnum;

public class Inanimado extends Ecossistema implements IElemento{
    private static int lastId = 0;
    private final int id;
    private final ElementoEnum elementoEnum = ElementoEnum.INANIMADO;
    private Coords coords;

    public Inanimado() {
        this.id = ++lastId;
        coords = new Coords(0,0);
    }

    // GETTERS E SETTERS
    @Override
    public int getId() {
        return id;
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
    public ElementoEnum getElemento() {
        return elementoEnum;
    }

    @Override
    public Field getField() {
        return super.getField();
    }
}
