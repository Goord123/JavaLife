package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngineEvolve;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ecossistema implements IGameEngineEvolve, Serializable {

    Set<IElemento> elementos = new HashSet<>();

    public Ecossistema() {
    }

    public void addElemento(IElemento elemento) {
        elementos.add(elemento);
    }

    public void removeElemento(IElemento elemento) {
        elementos.remove(elemento);
    }

    public Set<IElemento> getElementos() {
        return elementos;
    }

    public IElemento getElementoByIdAndType(int id, IElemento elemento) {
        for (IElemento e : elementos) {
            if (e.getId() == id && e.getElemento() == elemento.getElemento()) {
                return e;
            }
        }
        return null;
    }

    public Set<IElemento> getElementosByElemento(ElementoEnum elementoEnum) {
        Set<IElemento> elementosByElemento = new HashSet<>();
        for (IElemento e : elementos) {
            if (e.getElemento() == elementoEnum) {
                elementosByElemento.add(e);
            }
        }
        return elementosByElemento;
    }

    public List<IElemento> getElementosByArea(Area area) {
        List<IElemento> elementosByArea = new ArrayList<>();
        for (IElemento e : elementos) {
            if (e.getArea().upperLeft() <= area.upperLeft() &&
                    e.getArea().upperRight() >= area.upperRight() &&
                    e.getArea().lowerLeft() <= area.lowerLeft() &&
                    e.getArea().lowerRight() >= area.lowerRight()) {
                elementosByArea.add(e);
            }
        }
        return elementosByArea;
    }

}
