package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.io.Serializable;

public sealed interface IElemento extends Serializable permits ElementoBase, Flora, Fauna, Inanimado {

    ElementoEnum getElemento();

    int getId();

    Area getArea();

    void evolve();

}
