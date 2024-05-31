package org.isec.pa.ecossistema.utils;

import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.data.*;

import java.io.Serializable;

public enum ElementoEnum implements Serializable {
    INANIMADO, FLORA, FAUNA;

//    public ElementoBase createFigure() {
//        return switch (this) {
//            case INANIMADO -> new Inanimado();
//            case FLORA -> new Flora(new EcossistemaManager(new Ecossistema())); //puz um só para não dar erro
//            // TODO
//            case FAUNA -> new Fauna(new EcossistemaManager(new Ecossistema()));//puz Fauna(new Ecossistema() só para não dar erro
//        };
//    }
}
