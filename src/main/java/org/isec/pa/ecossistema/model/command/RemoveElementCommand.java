package org.isec.pa.ecossistema.model.command;

import org.isec.pa.ecossistema.model.data.Ecossistema;
import org.isec.pa.ecossistema.model.data.IElemento;
import org.isec.pa.ecossistema.utils.ElementoEnum;

public class RemoveElementCommand extends AbstractCommand{
    private int id;
    private ElementoEnum tipoElemento;
    private IElemento elemento;
    public RemoveElementCommand(Ecossistema ecossistema, ElementoEnum elementoEnum, int id){
        super(ecossistema);
        this.tipoElemento = elementoEnum;
        this.id = id;
    }

    @Override
    public boolean execute(){
        elemento = ecossistema.getElementoByIdAndType(id, tipoElemento);
        if(elemento == null)
            return false;
        ecossistema.removerElementoCommand(elemento);
        return true;
    }

    public boolean undo(){
        return ecossistema.removerElementoCommandUndo(elemento);
    }
}
