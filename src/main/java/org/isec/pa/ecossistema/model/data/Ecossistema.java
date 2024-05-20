package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngineEvolve;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Ecossistema implements IGameEngineEvolve, Serializable {

    Set<IElemento> elementos = new HashSet<>();

    public Ecossistema() { }

    public Set<IElemento> getElementos() {
        return elementos;
    }

    public void setElementos(Set<IElemento> elementos) {
        this.elementos = elementos;
    }

}
