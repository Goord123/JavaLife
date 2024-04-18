package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngineEvolve;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.util.HashSet;
import java.util.Set;

public class Ecossistema implements IGameEngineEvolve {

    Set<IElemento> elementos = new HashSet<>();
    Field field;

    Ecossistema(){
        this.field = new Field(100, 100);
    }

    public Field getField(){
        return field;
    }
    public void setField(Field field){
        this.field = field;
    }

    public void addElemento(IElemento elemento){
        elementos.add(elemento);
    }

    public void removeElemento(IElemento elemento){
        elementos.remove(elemento);
    }

    public Set<IElemento> getElementos(){
        return elementos;
    }

    public IElemento getElementoById(int id){
        for(IElemento e : elementos){
            if(e.getId() == id){
                return e;
            }
        }
        return null;
    }

    public boolean removeElementoById(int id){
        for(IElemento e : elementos){
            if(e.getId() == id){
                int x = getElementoById(id).getCoords().getX();
                int y = getElementoById(id).getCoords().getY();
                field.removeElement(x, y);
                elementos.remove(e);
                return true;
            }
        }
        return false;
    }

    public Set<IElemento> getElementosByElemento(ElementoEnum elementoEnum){
        Set<IElemento> elementosByElemento = new HashSet<>();
        for(IElemento e : elementos){
            if(e.getElemento() == elementoEnum){
                elementosByElemento.add(e);
            }
        }
        return elementosByElemento;
    }


}
