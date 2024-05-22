package org.isec.pa;

import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.data.Ecossistema;
import org.isec.pa.ecossistema.ui.EcossistemaUI;

public class Main {
    public static void main(String[] args) {
        Ecossistema ecossistema = new Ecossistema();
        EcossistemaManager ecossistemaManager = new EcossistemaManager(ecossistema);
        EcossistemaUI ui = new EcossistemaUI(ecossistemaManager);
        ui.start();
    }
}