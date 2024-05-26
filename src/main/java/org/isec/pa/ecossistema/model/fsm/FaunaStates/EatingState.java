package org.isec.pa.ecossistema.model.fsm.FaunaStates;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStateAdapter;

public class EatingState extends FaunaStateAdapter {

    public EatingState(FaunaContext context, Fauna fauna) {
        super(context, fauna);
    }

    @Override
    public IFaunaState getCurrentState() {
        return FaunaState.EATING.getInstance(context, fauna);
    }

    @Override
    public void eat() {
        if (!fauna.checkIfAlive()) return;

        if (fauna.checkIfOnFlora() && fauna.getForca() != 100)
            fauna.eat();
        else {
            // muda de estado
            changeState(FaunaState.LOOKING_FOR_FLORA);
            // avisa o contexto, que por sua vez avisa a fauna que mudou de estado
            context.changeState(FaunaState.LOOKING_FOR_FLORA.getInstance(context, fauna));
        }
    }
}
