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
    public void evolve() {
        if (!fauna.checkIfAlive()) return;
        System.out.println("valor: " + fauna.checkIfOnFlora());
        System.out.println("forca: " + fauna.getForca());
        if (fauna.checkIfOnFlora() && fauna.getForca() != 100) {
            System.out.println("Eating");
            fauna.eat();
        }
        else {
            // muda de estado
            changeState(FaunaState.LOOKING_FOR_FLORA);
        }
    }
}
