package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngine;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngineEvolve;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Ecossistema implements IGameEngineEvolve, Serializable {

    private ElementoEnum elementType;

    private ElementoBase element;

    Set<IElemento> elementos = new HashSet<>();

    public Ecossistema() {
        this.elementType = ElementoEnum.INANIMADO;
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

    public ElementoBase getCurrentFigure() {
        return this.element;
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

    public void setElementos(Set<IElemento> elementos) {
        this.elementos = elementos;
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        for (IElemento elemento : elementos) {
            elemento.evolve(gameEngine, currentTime);
        }
    }

//    public void createElement(double x, double y){
//        this.element = this.elementType.createFigure();
//        this.element.setP1(x, y);
//        this.element.setP2(x, y);
//        //this.element.setRGB(this.r, this.g, this.b);
//    }
}
