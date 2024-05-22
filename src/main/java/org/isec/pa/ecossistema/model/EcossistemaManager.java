package org.isec.pa.ecossistema.model;

import javafx.scene.canvas.GraphicsContext;
import org.isec.pa.ecossistema.model.data.Ecossistema;
import org.isec.pa.ecossistema.model.data.ElementoBase;
import org.isec.pa.ecossistema.model.data.IElemento;
import org.isec.pa.ecossistema.model.data.Inanimado;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.isec.pa.ecossistema.model.data.Ecossistema;
import org.isec.pa.ecossistema.model.data.IElemento;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EcossistemaManager {
    public static final String PROP_ELEMENT = "_element_";

    private Ecossistema ecossistema;
    private final int mapHeight;
    private final int mapWidth;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public EcossistemaManager(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
        mapHeight = 800;
        mapWidth = 600;
    }

    public void addElemento(IElemento elemento) {
        ecossistema.getElementos().add(elemento);
    }

    public void removeElemento(IElemento elemento) {
        ecossistema.getElementos().remove(elemento);
    }

    public Set<IElemento> getElementos() {
        return ecossistema.getElementos();
    }

    public ElementoBase getCurrentElement() {
        return this.ecossistema.getCurrentFigure();
    }

    public IElemento getElementoByIdAndType(int id, IElemento elemento) {
        for (IElemento e : ecossistema.getElementos()) {
            if (e.getId() == id && e.getElemento() == elemento.getElemento()) {
                return e;
            }
        }
        return null;
    }

    public Set<IElemento> getElementosByElemento(ElementoEnum elementoEnum) {
        Set<IElemento> elementosByElemento = new HashSet<>();
        for (IElemento e : ecossistema.getElementos()) {
            if (e.getElemento() == elementoEnum) {
                elementosByElemento.add(e);
            }
        }
        return elementosByElemento;
    }

    public List<IElemento> getElementosByArea(Area area) {
        List<IElemento> elementosByArea = new ArrayList<>();
        for (IElemento e : ecossistema.getElementos()) {
            Area eArea = e.getArea();

            boolean intersects = !(eArea.x1() > area.x2() ||
                    eArea.x2() < area.x1() ||
                    eArea.y1() > area.y2() ||
                    eArea.y2() < area.y1());

            if (intersects) {
                elementosByArea.add(e);
            }
        }
        return elementosByArea;
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(property, listener);
    }

    public void createFigure(double x, double y) {
        ecossistema.createElement(x,y);
        pcs.firePropertyChange(PROP_ELEMENT,null,null);
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void spawnBorder(double x, double y, double width, double height){
        // Draw top border
        for (double i = 0; i < width; i = i + 10) {
            Inanimado InanimadoTemp = new Inanimado();
            InanimadoTemp.setP1(i, 0);
            InanimadoTemp.setP2(i+10, 10);
            ecossistema.addElemento(InanimadoTemp);
        }

        // Draw bottom border
        for (double i = 0; i < width; i = i + 10) {
            Inanimado InanimadoTemp = new Inanimado();
            InanimadoTemp.setP1(i, height-10);
            InanimadoTemp.setP2(i+10, height);
            ecossistema.addElemento(InanimadoTemp);
        }

        // Draw left border
        for (double i = 0; i < height; i = i + 10) {
            Inanimado InanimadoTemp = new Inanimado();
            InanimadoTemp.setP1(0, i);
            InanimadoTemp.setP2(10, i+10);
            ecossistema.addElemento(InanimadoTemp);
        }

        // Draw right border
        for (double i = 0; i < height; i = i + 10) {
            Inanimado InanimadoTemp = new Inanimado();
            InanimadoTemp.setP1(width-10, i);
            InanimadoTemp.setP2(width, i+10);
            ecossistema.addElemento(InanimadoTemp);
        }
    }
}
