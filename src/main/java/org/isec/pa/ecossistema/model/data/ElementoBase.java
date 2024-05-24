package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

public abstract sealed class ElementoBase implements IElemento permits Inanimado, Flora, Fauna {
    //protected double x1,y1;
    //protected double x2,y2;
    public abstract int getId();

    public abstract Area getArea();

    public abstract ElementoEnum getElemento();




    public double getWidth() {
        return getArea().x2()-getArea().x1();
    }

    public double getHeight() {
        return getArea().y2()-getArea().y1();
    }
}
