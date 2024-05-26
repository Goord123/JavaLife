package org.isec.pa.ecossistema.model.fsm;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.IFaunaState;

public abstract class FaunaStateAdapter implements IFaunaState {
    protected FaunaContext context;
    protected Fauna fauna;

    protected FaunaStateAdapter(FaunaContext context, Fauna fauna){
        this.context = context;
        this.fauna = fauna;
    }

    protected void changeState(FaunaState newState) {
        context.changeState(newState.getInstance(context, fauna));
    }

    @Override
    public void move() {

    }

    public abstract IFaunaState getCurrentState();

    @Override
    public void eat() {

    }

    @Override
    public void reproduce() {

    }

    @Override
    public void die() {

    }

    @Override
    public void evolve() {

    }
}
