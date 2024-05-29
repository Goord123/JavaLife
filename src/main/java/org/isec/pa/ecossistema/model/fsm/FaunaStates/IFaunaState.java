package org.isec.pa.ecossistema.model.fsm.FaunaStates;

public interface IFaunaState {

    void move();

    void eat();

    boolean reproduce();

    IFaunaState getCurrentState();

    void evolve();
}
