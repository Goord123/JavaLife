package org.isec.pa.ecossistema.model.fsm.GameEngine;

public interface IGameEngineEvolve {
    void evolve(IGameEngine gameEngine, long currentTime);
}