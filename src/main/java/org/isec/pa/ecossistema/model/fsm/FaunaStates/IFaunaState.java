package org.isec.pa.ecossistema.model.fsm.FaunaStates;

import org.isec.pa.ecossistema.model.fsm.FaunaState;

public interface IFaunaState {

    void move();
    void eat();
    void reproduce();
    void die();
    FaunaState getCurrentState();
    void evolve();
}
