package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.io.Serializable;

public sealed interface IElemento extends Serializable permits ElementoBase, Flora, Fauna, Inanimado {

    // METODOS COMUNS A TODOS OS ELEMENTOS DO ECOSSISTEMA

    ElementoEnum getElemento();
    int getId();
    Area getArea();
}
