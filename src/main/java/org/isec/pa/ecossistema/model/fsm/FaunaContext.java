package org.isec.pa.ecossistema.model.fsm;

import org.isec.pa.ecossistema.model.data.Ecossistema;
import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.IFaunaState;

public class FaunaContext {

    private Fauna data;
    private IFaunaState currentState;

    public FaunaContext(Ecossistema ecossistema){
        this.data = new Fauna(ecossistema);
        this.currentState = FaunaState.LOOKING_FOR_FLORA.getInstance(this, data);
    }

    public void changeState(IFaunaState newState){
        this.currentState = newState;
    }

    public void move(){
        currentState.move();
    }

    public void eat(){
        currentState.eat();
    }

    public void reproduce(){
        currentState.reproduce();
    }

    public void die(){
        currentState.die();
    }

}
