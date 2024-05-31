package org.isec.pa.ecossistema.model.command;

import org.isec.pa.ecossistema.model.data.Ecossistema;
import org.isec.pa.ecossistema.model.data.IElemento;
import org.isec.pa.ecossistema.model.data.Inanimado;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import static org.isec.pa.ecossistema.utils.UtilFunctions.getRandomNumber;

public class AddElementCommand extends AbstractCommand{

    private ElementoEnum tipoElemento;
    private IElemento elemento;
    public AddElementCommand(Ecossistema ecossistema, ElementoEnum elementoEnum){
        super(ecossistema);
        this.tipoElemento = elementoEnum;
    }

    @Override
    public boolean execute(){
        if(!ecossistema.mapHasSpace()){
            return false;
        }
        elemento = ecossistema.adicionarElementoCommand(tipoElemento);
        return true;
    }

    public boolean undo(){
        return ecossistema.adicionarElementoCommandUndo(elemento);
    }
}
