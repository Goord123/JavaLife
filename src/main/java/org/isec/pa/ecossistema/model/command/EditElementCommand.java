package org.isec.pa.ecossistema.model.command;

import org.isec.pa.ecossistema.model.data.Ecossistema;
import org.isec.pa.ecossistema.model.data.IElemento;
import org.isec.pa.ecossistema.utils.ElementoEnum;

public class EditElementCommand extends AbstractCommand{
    private int id;
    private ElementoEnum tipoElemento;
    private IElemento elemento;

    private double forca;
    private int velocidade;
    public EditElementCommand(Ecossistema ecossistema, ElementoEnum elementoEnum, int id, double newForca, int newVelocidade){
        super(ecossistema);
        this.tipoElemento = elementoEnum;
        this.id = id;
        this.forca = newForca;
        this.velocidade = newVelocidade;
    }

    @Override
    public boolean execute(){

        //verificar se o elemento existe
        elemento = ecossistema.getElementoByIdAndType(id, tipoElemento);
        if(elemento == null)
            return false;

        if(tipoElemento.equals(ElementoEnum.FLORA)){
            ecossistema.setForcaFlora(id, forca);
            System.out.println("Edit execute entrou no if flora");
        } else if (tipoElemento.equals(ElementoEnum.FAUNA)){
            ecossistema.setForcaEVelocidadeFauna(id, forca, velocidade);
        } else {
            return false;
        }
        return true;
    }

    public boolean undo(){
        return ecossistema.editarElementoCommandUndo();
    }
}
