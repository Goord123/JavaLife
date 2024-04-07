package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.Elemento;

public class InanimadoData extends EcossistemaData implements IElemento{
    private static int lastId = 0;
    private final int id;
    private final Elemento elemento = Elemento.INANIMADO;

    public InanimadoData() {
        this.id = ++lastId;
    }

    // GETTERS E SETTERS
    @Override
    public int getId() {
        return id;
    }
    @Override
    public Elemento getElemento() {
        return elemento;
    }

}
