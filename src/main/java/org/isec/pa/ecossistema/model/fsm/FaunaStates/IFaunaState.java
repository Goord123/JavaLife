package org.isec.pa.ecossistema.model.fsm.FaunaStates;

public interface IFaunaState {

    void move();
    void eat();
    void reproduce();
    void die();
    IFaunaState getCurrentState();

    void evolve();
    //void evolve();
}
