package org.isec.pa.ecossistema.model.fsm;

import org.isec.pa.ecossistema.model.data.FaunaData;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.IFaunaState;

public abstract class FaunaStateAdapter implements IFaunaState {
    protected FaunaContext context;
    protected FaunaData data;

    protected FaunaStateAdapter(FaunaContext context, FaunaData data){
        this.context = context;
        this.data = data;
    }

    protected void changeState(FaunaState newState) {
        context.changeState(newState.getInstance(context, data));
    }

    @Override
    public void move() {

    }

    @Override
    public void eat() {

    }

    @Override
    public void reproduce() {

    }

    @Override
    public void die() {

    }
}
