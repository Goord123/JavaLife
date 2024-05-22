package org.isec.pa.ecossistema.model;

import org.isec.pa.ecossistema.model.data.Ecossistema;
import org.isec.pa.ecossistema.model.data.IElemento;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EcossistemaManager {

    Ecossistema ecossistema;
    public EcossistemaManager(Ecossistema ecossistema){
        this.ecossistema = ecossistema;
    }

    public void addElemento(IElemento elemento) {
        ecossistema.getElementos().add(elemento);
    }

    public void removeElemento(IElemento elemento) {
        ecossistema.getElementos().remove(elemento);
    }

    public Set<IElemento> getElementos() {
        return ecossistema.getElementos();
    }

    public IElemento getElementoByIdAndType(int id, IElemento elemento) {
        for (IElemento e : ecossistema.getElementos()) {
            if (e.getId() == id && e.getElemento() == elemento.getElemento()) {
                return e;
            }
        }
        return null;
    }

    public Set<IElemento> getElementosByElemento(ElementoEnum elementoEnum) {
        Set<IElemento> elementosByElemento = new HashSet<>();
        for (IElemento e : ecossistema.getElementos()) {
            if (e.getElemento() == elementoEnum) {
                elementosByElemento.add(e);
            }
        }
        return elementosByElemento;
    }

    public List<IElemento> getElementosByArea(Area area) {
        List<IElemento> elementosByArea = new ArrayList<>();
        for (IElemento e : ecossistema.getElementos()) {
            Area eArea = e.getArea();

            boolean intersects = !(eArea.x1() > area.x2() ||
                    eArea.x2() < area.x1() ||
                    eArea.y1() > area.y2() ||
                    eArea.y2() < area.y1());

            if (intersects) {
                elementosByArea.add(e);
            }
        }
        return elementosByArea;
    }



}
