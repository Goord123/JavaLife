package org.isec.pa.ecossistema.model.fsm.FaunaStates;

public interface IFaunaState {


    IFaunaState getCurrentState();

    void evolve();
}
