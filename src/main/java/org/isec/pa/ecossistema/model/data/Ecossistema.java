package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.model.fsm.GameEngine.GameEngine;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngine;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngineEvolve;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static org.isec.pa.ecossistema.model.EcossistemaManager.PROP_ELEMENT;

public class Ecossistema implements IGameEngineEvolve, Serializable {
    private IGameEngine gameEngine;
    Set<IElemento> elementos = new HashSet<>();
    private transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    public Ecossistema() {
        this.gameEngine = new GameEngine();
        this.gameEngine.start(1000);
        this.gameEngine.registerClient(this);
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        for (IElemento elemento : elementos) {
            elemento.evolve(gameEngine, currentTime);
        }
        pcs.firePropertyChange(PROP_ELEMENT, null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void addElemento(IElemento elemento) {
        elementos.add(elemento);
       // if (elemento instanceof IGameEngineEvolve) // inanimados nao passam neste check
       //     gameEngine.registerClient((IGameEngineEvolve) elemento);
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

    public void setElementos(Set<IElemento> elementos) {
        this.elementos = elementos;
    }



//    public void createElement(double x, double y){
//        this.element = this.elementType.createFigure();
//        this.element.setP1(x, y);
//        this.element.setP2(x, y);
//        //this.element.setRGB(this.r, this.g, this.b);
//    }
}
