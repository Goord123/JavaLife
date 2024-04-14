package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.ElementoEnum;

public class Inanimado extends Ecossistema implements IElemento{
    private static int lastId = 0;
    private final int id;
    private final ElementoEnum elementoEnum = ElementoEnum.INANIMADO;

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

}
