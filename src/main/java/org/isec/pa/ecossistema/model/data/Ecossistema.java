package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.model.fsm.GameEngine.GameEngine;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngine;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngineEvolve;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static org.isec.pa.ecossistema.model.EcossistemaManager.PROP_ELEMENT;

public class Ecossistema implements IGameEngineEvolve, Serializable {
    private final IGameEngine gameEngine;
    Set<IElemento> elementos = new HashSet<>();
    private transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    public Ecossistema() {
        this.gameEngine = new GameEngine();
        this.gameEngine.start(1000);
        this.gameEngine.registerClient(this);
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        Set<IElemento> elementsToRemove = new HashSet<>();
        Set<IElemento> elementsToAdd = new HashSet<>();

        synchronized (elementos) {
            for (IElemento elemento : elementos) {
                if (elemento instanceof Fauna && ((Fauna) elemento).getForca() <= 0) {
                    elementsToRemove.add(elemento);
                    gameEngine.unregisterClient((IGameEngineEvolve) elemento);
                } else if (elemento instanceof Flora && ((Flora) elemento).getForca() <= 0) {
                    System.out.println("morreu FLORA");
                    elementsToRemove.add(elemento);
                    gameEngine.unregisterClient((IGameEngineEvolve) elemento);
                } else if (elemento instanceof Fauna && ((Fauna) elemento).checkIfCanReproduce()) {
                    System.out.println("reproduzir ANIMAL");
                    Area areaForNewFauna = ((Fauna) elemento).getReproductionArea();
                    Fauna newFauna = new Fauna(((Fauna) elemento).getEcossistemaManager());
                    newFauna.setArea(areaForNewFauna);
                    elementsToAdd.add(newFauna);
                } else if (elemento instanceof Flora && ((Flora) elemento).reproduce()) {
                    System.out.println("entrei");
                    Area areaForNewFlora = ((Flora) elemento).getAdjacentArea();
                    System.out.println("areaForNewFlora: " + areaForNewFlora);
                    if (areaForNewFlora != null) {
                        Flora newFlora = new Flora(((Flora) elemento).getEcossistemaManager());
                        newFlora.setArea(areaForNewFlora);
                        elementsToAdd.add(newFlora);
                        System.out.println("areaForNewFlora nao é null");
                    }
                }
            }
        }

        synchronized (elementos) {
            elementos.removeAll(elementsToRemove);
            elementos.addAll(elementsToAdd);
            for (IElemento newElement : elementsToAdd) {
                if (newElement instanceof IGameEngineEvolve) {
                    gameEngine.registerClient((IGameEngineEvolve) newElement);
                    System.out.println("registado");
                }
            }
        }

        pcs.firePropertyChange(PROP_ELEMENT, null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void addElemento(IElemento elemento) {
        synchronized (elementos) {
            elementos.add(elemento);
        }
        if (elemento instanceof IGameEngineEvolve) {
            gameEngine.registerClient((IGameEngineEvolve) elemento);
        }
    }

    public void removeElemento(IElemento elemento) {
        synchronized (elementos) {
            elementos.remove(elemento);
        }
    }

    public Set<IElemento> getElementos() {
        synchronized (elementos) {
            return new HashSet<>(elementos);
        }
    }

    public IElemento getElementoByIdAndType(int id, IElemento elemento) {
        synchronized (elementos) {
            for (IElemento e : elementos) {
                if (e.getId() == id && e.getElemento() == elemento.getElemento()) {
                    return e;
                }
            }
            return null;
        }
    }

    public Set<IElemento> getElementosByElemento(ElementoEnum elementoEnum) {
        Set<IElemento> elementosByElemento = new HashSet<>();
        synchronized (elementos) {
            for (IElemento e : elementos) {
                if (e.getElemento() == elementoEnum) {
                    elementosByElemento.add(e);
                }
            }
        }
        return elementosByElemento;
    }

    public void setElementos(Set<IElemento> elementos) {
        synchronized (this.elementos) {
            this.elementos = elementos;
        }
    }

    public void unregisterClient(IGameEngineEvolve client) {
        gameEngine.unregisterClient(client);
    }




//    public void createElement(double x, double y){
//        this.element = this.elementType.createFigure();
//        this.element.setP1(x, y);
//        this.element.setP2(x, y);
//        //this.element.setRGB(this.r, this.g, this.b);
//    }
}
