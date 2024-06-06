package org.isec.pa.ecossistema.model.fsm;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.IFaunaState;

import java.io.Serial;
import java.io.Serializable;

public abstract class FaunaStateAdapter implements IFaunaState, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected FaunaContext context;
    protected Fauna fauna;

    protected FaunaStateAdapter(FaunaContext context, Fauna fauna) {
        this.context = context;
        this.fauna = fauna;
    }

    protected void changeState(FaunaState newState) {
        context.changeState(newState.getInstance(context, fauna));
    }


    @Override
    public void evolve() {
    }

    // para a serializacao
    public void setContext(FaunaContext context) {
        this.context = context;
    }

    // para a serializacao
    public void setFauna(Fauna fauna) {
        this.fauna = fauna;
    }
}
