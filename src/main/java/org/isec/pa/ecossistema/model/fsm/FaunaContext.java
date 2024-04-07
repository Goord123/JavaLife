package org.isec.pa.ecossistema.model.fsm;

import org.isec.pa.ecossistema.model.data.FaunaData;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.IFaunaState;

public class FaunaContext {

    private FaunaData data;
    private IFaunaState currentState;

    public FaunaContext(){
        this.data = new FaunaData();
        this.currentState = FaunaState.INITIAL_STATE.getInstance(this, data);
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
