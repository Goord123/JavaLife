package org.isec.pa.ecossistema.model;

import javafx.scene.canvas.GraphicsContext;
import org.isec.pa.ecossistema.model.data.*;

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

import static org.isec.pa.ecossistema.utils.UtilFunctions.getRandomNumber;

public class EcossistemaManager {
    public static final String PROP_ELEMENT = "_element_";

    private Ecossistema ecossistema;
    private final int mapHeight;
    private final int mapWidth;

    private final double tamBorder = 20;

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
        for (double i = 0; i < width; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setP1(i, 0);
            inanimadoTemp.setP2(i+tamBorder, tamBorder);
            inanimadoTemp.setArea(new Area(i, i+tamBorder,0, tamBorder));
            ecossistema.addElemento(inanimadoTemp);

        }

        // Draw bottom border
        for (double i = 0; i < width; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setP1(i, height-tamBorder);
            inanimadoTemp.setP2(i+tamBorder, height);
            inanimadoTemp.setArea(new Area(i, i+tamBorder,height-tamBorder, height));
            ecossistema.addElemento(inanimadoTemp);

        }

        // Draw left border
        for (double i = 0; i < height; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setP1(0, i);
            inanimadoTemp.setP2(tamBorder, i+tamBorder);
            inanimadoTemp.setArea(new Area(0, tamBorder,i ,i+tamBorder));
            ecossistema.addElemento(inanimadoTemp);
        }

        // Draw right border
        for (double i = 0; i < height; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setP1(width-tamBorder, i);
            inanimadoTemp.setP2(width, i+tamBorder);
            inanimadoTemp.setArea(new Area(width-tamBorder, width,i , i+tamBorder));
            ecossistema.addElemento(inanimadoTemp);
        }
    }

    public void spawnRandoms(double x, double y, double width, double height){
        double randomWidth, randomHeight;
        for(double i = 0; i < 15.0; i++){
            do{
                do{
                    randomWidth = (double) getRandomNumber((int) width);
                }while(randomWidth < tamBorder || randomWidth > width-2*tamBorder);
                do{
                    randomHeight = (double) getRandomNumber((int) height);
                }while(randomHeight < tamBorder || randomHeight > height-2*tamBorder);
            }while(existsElement(randomWidth, randomHeight, randomWidth + 20, randomHeight + 20));

            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setP1(randomWidth, randomHeight);
            inanimadoTemp.setP2(randomWidth + tamBorder, randomHeight + tamBorder);
            inanimadoTemp.setArea(new Area(randomWidth, randomWidth + tamBorder,randomHeight , randomHeight + tamBorder));
            ecossistema.addElemento(inanimadoTemp);
            //System.out.println(inanimadoTemp.getArea().x1());
        }

        for(double i = 0; i < 15.0; i++){
            do{
                do{
                    randomWidth = (double) getRandomNumber((int) width);
                }while(randomWidth < tamBorder || randomWidth > width-2*tamBorder);
                do{
                    randomHeight = (double) getRandomNumber((int) height);
                }while(randomHeight < tamBorder || randomHeight > height-2*tamBorder);
            }while(existsElement(randomWidth, randomHeight, randomWidth + 20, randomHeight + 20));

            Flora floraTemp = new Flora(20);
            floraTemp.setP1(randomWidth, randomHeight);
            floraTemp.setP2(randomWidth + tamBorder, randomHeight + tamBorder);
            floraTemp.setArea(new Area(randomWidth, randomWidth + tamBorder,randomHeight , randomHeight + tamBorder));
            ecossistema.addElemento(floraTemp);
        }
    }

    public boolean existsElement(double x1, double y1, double x2, double y2){
        Area area = new Area(x1, x2, y1, y2);
        return !getElementosByArea(area).isEmpty();

        //return ecossistema.existsElement(x1, y1, x2, y2);
    }
}
