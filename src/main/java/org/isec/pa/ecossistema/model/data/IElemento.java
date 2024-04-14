package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.ElementoEnum;

public interface IElemento {

    // METODOS COMUNS A TODOS OS ELEMENTOS DO ECOSSISTEMA

    ElementoEnum getElemento();
    int getId();
}
