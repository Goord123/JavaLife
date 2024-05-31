package org.isec.pa.ecossistema.model.fsm;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.IFaunaState;

public class FaunaContext {

    private Fauna fauna;
    private IFaunaState currentState;
    public FaunaContext(Fauna fauna){
        this.fauna = fauna;
        this.currentState = FaunaState.LOOKING_FOR_FLORA.getInstance(this, fauna);
        this.fauna.changeState(currentState);
    }

    public void changeState(IFaunaState newState){
        this.currentState = newState;
    }

    public void evolve() {
        currentState.evolve();
    }
    public IFaunaState getCurrentState() {
        return fauna.getCurrentState();
    }

}
