package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngine;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngineEvolve;
import org.isec.pa.ecossistema.utils.ElementoEnum;
import org.isec.pa.ecossistema.utils.GameSaver;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Ecossistema implements IGameEngineEvolve, Serializable {

    private final String FILE_PATH = "ecossistema.csv";
    Set<IElemento> elementos = new HashSet<>();
    GameSaver gameSaver = new GameSaver();

    private int tickSpeed = 1000;// (milisegundos)

    public Ecossistema() {
    }

    public void save() throws IOException {
        Set<IElemento> elementosToBeSaved = elementos;
        gameSaver.saveToCSV(elementosToBeSaved, FILE_PATH);
    }

    public void load(EcossistemaManager manager) {
        try {
            Set<IElemento> elementosLoaded = gameSaver.loadFromCSV(manager, FILE_PATH);
            setElementos(elementosLoaded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
    }

    public void addElemento(IElemento elemento) {
        synchronized (elementos) {
            elementos.add(elemento);
        }
    }

    public void removeAllElementos() {
        synchronized (elementos) {
            elementos.clear();
        }
    }

    public void removeElemento(IElemento elemento) {
        synchronized (elementos) {
            elementos.remove(elemento);
        }
    }

    public void removeElementos(Set<IElemento> elementsToRemove) {
        synchronized (elementos) {
            elementos.removeAll(elementsToRemove);
        }
    }

    public void addElementos(Set<IElemento> elementsToAdd) {
        synchronized (elementos) {
            elementos.addAll(elementsToAdd);
        }
    }

    public Set<IElemento> getElementos() {
        synchronized (elementos) {
            return new HashSet<>(elementos);
        }
    }

    public void setElementos(Set<IElemento> elementos) {
        synchronized (this.elementos) {
            this.elementos = elementos;
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

    public void setForcaFlora(int id, double forca) {
        synchronized (this.elementos) {
            for (IElemento e : elementos) {
                if (e.getElemento() == ElementoEnum.FLORA && e.getId() == id) {
                    Flora flora = (Flora) e;
                    flora.setForca(forca);
                }
            }
        }
    }

    public void setForcaEVelocidadeFauna(int id, double forca, int velocidade) {
        synchronized (this.elementos) {
            for (IElemento e : elementos) {
                if (e.getElemento() == ElementoEnum.FAUNA && e.getId() == id) {
                    Fauna fauna = (Fauna) e;
                    fauna.setForca(forca);
                    fauna.setVelocity(velocidade);
                }
            }
        }
    }


//    public void createElement(double x, double y){
//        this.element = this.elementType.createFigure();
//        this.element.setP1(x, y);
//        this.element.setP2(x, y);
//        //this.element.setRGB(this.r, this.g, this.b);
//    }
}
