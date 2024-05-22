package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

public abstract sealed class ElementoBase implements IElemento permits Inanimado, Flora, Fauna {
    protected double x1,y1;
    protected double x2,y2;
    public abstract int getId();

    public abstract Area getArea();

    public abstract ElementoEnum getElemento();


    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public double getWidth() {
        return x2-x1;
    }

    public double getHeight() {
        return y2-y1;
    }

    public void setP1(double x, double y) {
        this.x1 = x;
        this.y1 = y;
    }
    public void setP2(double x, double y) {
        this.x2 = x;
        this.y2 = y;
    }
}
