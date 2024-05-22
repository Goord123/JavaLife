package org.isec.pa.ecossistema.ui;

import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;

public class EcossistemaUI {

    EcossistemaManager ecossistemaManager;
    FaunaContext faunaContext;

    public EcossistemaUI(EcossistemaManager ecossistemaManager) {
        this.ecossistemaManager = ecossistemaManager;
        this.faunaContext = new FaunaContext(ecossistemaManager);
    }

    public void start() {
        System.out.println("EcossistemaUI started");
        boolean res;
        do {
            res = switch (faunaContext.getCurrentState()) {
                case LOOKING_FOR_FLORA -> {
                    System.out.println("Looking for flora");
                    yield false;
                }
                case LOOKING_FOR_FAUNA -> {
                    System.out.println("Looking for fauna");
                    yield true;
                }
                case EATING -> {
                    System.out.println("Eating");
                    yield true;
                }
            };
        }while (res);
        }

    }
