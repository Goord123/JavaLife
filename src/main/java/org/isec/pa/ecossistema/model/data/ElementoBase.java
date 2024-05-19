package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

public abstract sealed class ElementoBase implements IElemento permits Inanimado, Flora, Fauna {

    public abstract int getId();

    public abstract Area getArea();

    public abstract ElementoEnum getElemento();

}
