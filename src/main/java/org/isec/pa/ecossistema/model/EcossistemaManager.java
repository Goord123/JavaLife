package org.isec.pa.ecossistema.model;

import javafx.application.Platform;
import org.isec.pa.ecossistema.model.data.*;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
    private final int pixelMultiplier = 20;
    private final double tamBorder = 20;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public EcossistemaManager(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
        mapHeight = 580;
        mapWidth = 800;

        // Add a listener to handle updates
        ecossistema.addPropertyChangeListener(evt -> {
            if (PROP_ELEMENT.equals(evt.getPropertyName())) {
                refreshUI();
            }
        });
    }

    public void refreshUI() {
        // Logic to refresh the UI, typically by updating the JavaFX scene graph
        Platform.runLater(() -> {
            pcs.firePropertyChange(PROP_ELEMENT, null, null);
        });
    }

    public int getPixelMultiplier(){
        return pixelMultiplier;
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
        //TODO falta comparar com ele próprio
        List<IElemento> elementosByArea = new ArrayList<>();
        for (IElemento e : ecossistema.getElementos()) {
            Area eArea = e.getArea();

            boolean intersects = !(eArea.x1() >= area.x2() ||
                    eArea.x2() <= area.x1() ||
                    eArea.y1() >= area.y2() ||
                    eArea.y2() <= area.y1());

            if (intersects) {
                elementosByArea.add(e);
            }
        }
        return elementosByArea;
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(property, listener);
    }

//    public void createFigure(double x, double y) {
//        ecossistema.createElement(x,y);
//        pcs.firePropertyChange(PROP_ELEMENT,null,null);
//    }

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
            inanimadoTemp.setArea(new Area(i, i+tamBorder,0, tamBorder));
            ecossistema.addElemento(inanimadoTemp);

        }

        // Draw bottom border
        for (double i = 0; i < width; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setArea(new Area(i, i+tamBorder,height-tamBorder, height));
            ecossistema.addElemento(inanimadoTemp);

        }

        // Draw left border
        for (double i = 0; i < height; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setArea(new Area(0, tamBorder,i ,i+tamBorder));
            ecossistema.addElemento(inanimadoTemp);
        }

        // Draw right border
        for (double i = 0; i < height; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setArea(new Area(width-tamBorder, width,i , i+tamBorder));
            ecossistema.addElemento(inanimadoTemp);
        }
    }

    public void spawnRandoms(double x, double y, double width, double height){
        double randomWidth, randomHeight;
        int randomWidthInt, randomHeightInt;
        for(double i = 0; i < 140.0; i++){
            do{
                do{
                    randomWidthInt = getRandomNumber((int) width);
                    randomWidth = (double) (randomWidthInt / pixelMultiplier) * pixelMultiplier;
                }while(randomWidth < tamBorder || randomWidth > width-2*tamBorder);
                do{
                    randomHeightInt = getRandomNumber((int) height);
                    randomHeight = (double) (randomHeightInt / pixelMultiplier) * pixelMultiplier;
                }while(randomHeight < tamBorder || randomHeight > height-2*tamBorder);
            }while(existsElement(randomWidth, randomHeight, randomWidth + pixelMultiplier, randomHeight + pixelMultiplier));

            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setArea(new Area(randomWidth, randomWidth + tamBorder,randomHeight , randomHeight + tamBorder));
            ecossistema.addElemento(inanimadoTemp);
            //System.out.println(inanimadoTemp.getArea().x1());
        }

        for(double i = 0; i < 40.0; i++){
            do{
                do{
                    randomWidthInt = getRandomNumber((int) width);
                    randomWidth = (double) (randomWidthInt / pixelMultiplier) * pixelMultiplier;
                }while(randomWidth < tamBorder || randomWidth > width-2*tamBorder);
                do{
                    randomHeightInt = getRandomNumber((int) height);
                    randomHeight = (double) (randomHeightInt / pixelMultiplier) * pixelMultiplier;
                }while(randomHeight < tamBorder || randomHeight > height-2*tamBorder);
            }while(existsElement(randomWidth, randomHeight, randomWidth + pixelMultiplier, randomHeight + pixelMultiplier));

            Flora floraTemp = new Flora(20);
            floraTemp.setArea(new Area(randomWidth, randomWidth + tamBorder,randomHeight , randomHeight + tamBorder));
            ecossistema.addElemento(floraTemp);
        }
    }

    public boolean existsElement(double x1, double y1, double x2, double y2){
        Area area = new Area(x1, x2, y1, y2);
        return !getElementosByArea(area).isEmpty();

        //return ecossistema.existsElement(x1, y1, x2, y2);
    }

    public void adicionarElementoInanimado(double width, double height){
        double randomWidth, randomHeight;
        int randomWidthInt, randomHeightInt;
        do{
            do{
                randomWidthInt = getRandomNumber((int) width);
                randomWidth = (double) (randomWidthInt / pixelMultiplier) * pixelMultiplier;
            }while(randomWidth < tamBorder || randomWidth > width-2*tamBorder);
            do{
                randomHeightInt = getRandomNumber((int) height);
                randomHeight = (double) (randomHeightInt / pixelMultiplier) * pixelMultiplier;
            }while(randomHeight < tamBorder || randomHeight > height-2*tamBorder);
        }while(existsElement(randomWidth, randomHeight, randomWidth + pixelMultiplier, randomHeight + pixelMultiplier));

        Inanimado inanimadoTemp = new Inanimado();
        inanimadoTemp.setArea(new Area(randomWidth, randomWidth + tamBorder,randomHeight , randomHeight + tamBorder));
        ecossistema.addElemento(inanimadoTemp);
        this.pcs.firePropertyChange("_element_", (Object)null, (Object)null);
    }

    public void adicionarElementoFlora(double width, double height){
        double randomWidth, randomHeight;
        int randomWidthInt, randomHeightInt;
        do{
            do{
                randomWidthInt = getRandomNumber((int) width);
                randomWidth = (double) (randomWidthInt / pixelMultiplier) * pixelMultiplier;
            }while(randomWidth < tamBorder || randomWidth > width-2*tamBorder);
            do{
                randomHeightInt = getRandomNumber((int) height);
                randomHeight = (double) (randomHeightInt / pixelMultiplier) * pixelMultiplier;
            }while(randomHeight < tamBorder || randomHeight > height-2*tamBorder);
        }while(existsElement(randomWidth, randomHeight, randomWidth + pixelMultiplier, randomHeight + pixelMultiplier));

        Flora floraTemp = new Flora(20);
        floraTemp.setArea(new Area(randomWidth, randomWidth + tamBorder,randomHeight , randomHeight + tamBorder));
        ecossistema.addElemento(floraTemp);
        this.pcs.firePropertyChange("_element_", (Object)null, (Object)null);
    }

    public void adicionarElementoFauna(double width, double height){
        double randomWidth, randomHeight;
        int randomWidthInt, randomHeightInt;
        do{
            do{
                randomWidthInt = getRandomNumber((int) width);
                randomWidth = (double) (randomWidthInt / pixelMultiplier) * pixelMultiplier;
            }while(randomWidth < tamBorder || randomWidth > width-2*tamBorder);
            do{
                randomHeightInt = getRandomNumber((int) height);
                randomHeight = (double) (randomHeightInt / pixelMultiplier) * pixelMultiplier;
            }while(randomHeight < tamBorder || randomHeight > height-2*tamBorder);
        }while(existsElement(randomWidth, randomHeight, randomWidth + pixelMultiplier, randomHeight + pixelMultiplier));

        Fauna faunaTemp = new Fauna(this);
        faunaTemp.setArea(new Area(randomWidth, randomWidth + tamBorder,randomHeight , randomHeight + tamBorder));
        System.out.println(faunaTemp.getArea().toString());
        ecossistema.addElemento(faunaTemp);
        this.pcs.firePropertyChange("_element_", (Object)null, (Object)null);
    }
}
