package org.isec.pa.ecossistema.model;

import javafx.application.Platform;
import org.isec.pa.ecossistema.model.data.*;
import org.isec.pa.ecossistema.model.fsm.GameEngine.GameEngine;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngine;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngineEvolve;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.isec.pa.ecossistema.utils.UtilFunctions.getRandomNumber;

public class EcossistemaManager implements IGameEngineEvolve, Serializable {
    public static final String PROP_ELEMENT = "_element_";
    private final int pixelMultiplier = 20;
    private final double tamBorder = 20;
    private final IGameEngine gameEngine;
    private Ecossistema ecossistema;
    private int mapHeight;
    private int mapWidth;
    private double forcaDefault = 50;
    private double velocidadeDefault = 1;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public EcossistemaManager(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
        this.gameEngine = new GameEngine();
        this.gameEngine.start(1000);
        this.gameEngine.registerClient(this);
        mapHeight = 580;
        mapWidth = 800;

        // Add a listener to handle updates
        pcs.addPropertyChangeListener(evt -> {
            if (PROP_ELEMENT.equals(evt.getPropertyName())) {
                refreshUI();
            }
        });
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        Set<IElemento> elementos = ecossistema.getElementos();
        Set<IElemento> elementsToRemove = new HashSet<>();
        Set<IElemento> elementsToAdd = new HashSet<>();

        synchronized (elementos) {
            for (IElemento elemento : elementos) {
                elemento.evolve();
                if (elemento instanceof Fauna && ((Fauna) elemento).isDead()) {
                    elementsToRemove.add(elemento);
                } else if (elemento instanceof Flora && ((Flora) elemento).isDead()) {
                    elementsToRemove.add(elemento);
                    System.out.println("Removing Flora with ID: " + ((Flora) elemento).getId() + " as it is dead.");
                    elementsToRemove.add(elemento);
                } else if (elemento instanceof Fauna && ((Fauna) elemento).reproduce()) {
                    System.out.println("reproduzir ANIMAL");
                    Area areaForNewFauna = ((Fauna) elemento).getReproductionArea();
                    Fauna newFauna = new Fauna(((Fauna) elemento).getEcossistemaManager());
                    newFauna.setArea(areaForNewFauna);
                    elementsToAdd.add(newFauna);
                } else if (elemento instanceof Flora && ((Flora) elemento).reproduce()) {
                    ((Flora) elemento).setForca(60);
                    ((Flora) elemento).setTimesReproduced(((Flora) elemento).getTimesReproduced() + 1);
                    Area areaForNewFlora = ((Flora) elemento).getAdjacentArea();
                    if (areaForNewFlora != null) {
                        Flora newFlora = new Flora(((Flora) elemento).getEcossistemaManager());
                        newFlora.setArea(areaForNewFlora);
                        elementsToAdd.add(newFlora);
                    }
                }
            }
        }

        Platform.runLater(() -> {
            synchronized (elementos) {
                for (IElemento elem : elementsToRemove) {
                    System.out.println("Actually removing element with ID: " + elem.getId());
                }

                ecossistema.removeElementos(elementsToRemove);
                ecossistema.addElementos(elementsToAdd);

                pcs.firePropertyChange(PROP_ELEMENT, null, null);
            }
        });
    }

    public void refreshUI() {
        // Logic to refresh the UI, typically by updating the JavaFX scene graph
        Platform.runLater(() -> {
            pcs.firePropertyChange(PROP_ELEMENT, null, null);
        });
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getPixelMultiplier() {
        return pixelMultiplier;
    }

    public void addElemento(IElemento elemento) {
        ecossistema.getElementos().add(elemento);
    }

    public void removeElemento(IElemento elemento) {
        ecossistema.getElementos().remove(elemento);
    }

    public double getForcaDefault() {
        return forcaDefault;
    }

    public void setForcaDefault(double newForcaDefault) {
        this.forcaDefault = newForcaDefault;
    }

    public Set<IElemento> getElementos() {
        return ecossistema.getElementos();
    }

    public void removeAllElementos() {
        ecossistema.removeAllElementos();
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
            if (e.getElemento() == ElementoEnum.FAUNA) {
                Fauna fauna = (Fauna) e;
                if (fauna.isDead()) {
                    continue;
                }
            }
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

    public void spawnBorder(double x, double y, double width, double height) {
        // Draw top border
        for (double i = 0; i < width; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setArea(new Area(i, i + tamBorder, 0, tamBorder));
            ecossistema.addElemento(inanimadoTemp);

        }

        // Draw bottom border
        for (double i = 0; i < width; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setArea(new Area(i, i + tamBorder, height - tamBorder, height));
            ecossistema.addElemento(inanimadoTemp);

        }

        // Draw left border
        for (double i = 0; i < height; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setArea(new Area(0, tamBorder, i, i + tamBorder));
            ecossistema.addElemento(inanimadoTemp);
        }

        // Draw right border
        for (double i = 0; i < height; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setArea(new Area(width - tamBorder, width, i, i + tamBorder));
            ecossistema.addElemento(inanimadoTemp);
        }
    }

    public void spawnRandoms(double x, double y, double width, double height) {
        double randomWidth, randomHeight;
        int randomWidthInt, randomHeightInt;
        for (double i = 0; i < 140.0; i++) {
            do {
                do {
                    randomWidthInt = getRandomNumber((int) width);
                    randomWidth = (double) (randomWidthInt / pixelMultiplier) * pixelMultiplier;
                } while (randomWidth < tamBorder || randomWidth > width - 2 * tamBorder);
                do {
                    randomHeightInt = getRandomNumber((int) height);
                    randomHeight = (double) (randomHeightInt / pixelMultiplier) * pixelMultiplier;
                } while (randomHeight < tamBorder || randomHeight > height - 2 * tamBorder);
            } while (existsElement(randomWidth, randomHeight, randomWidth + pixelMultiplier, randomHeight + pixelMultiplier));

            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setArea(new Area(randomWidth, randomWidth + tamBorder, randomHeight, randomHeight + tamBorder));
            ecossistema.addElemento(inanimadoTemp);
            //System.out.println(inanimadoTemp.getArea().x1());
        }

        for (double i = 0; i < 40.0; i++) {
            do {
                do {
                    randomWidthInt = getRandomNumber((int) width);
                    randomWidth = (double) (randomWidthInt / pixelMultiplier) * pixelMultiplier;
                } while (randomWidth < tamBorder || randomWidth > width - 2 * tamBorder);
                do {
                    randomHeightInt = getRandomNumber((int) height);
                    randomHeight = (double) (randomHeightInt / pixelMultiplier) * pixelMultiplier;
                } while (randomHeight < tamBorder || randomHeight > height - 2 * tamBorder);
            } while (existsElement(randomWidth, randomHeight, randomWidth + pixelMultiplier, randomHeight + pixelMultiplier));

            Flora floraTemp = new Flora(this);
            floraTemp.setArea(new Area(randomWidth, randomWidth + tamBorder, randomHeight, randomHeight + tamBorder));
            ecossistema.addElemento(floraTemp);
        }
    }

    public boolean existsElement(double x1, double y1, double x2, double y2) {
        Area area = new Area(x1, x2, y1, y2);
        return !getElementosByArea(area).isEmpty();

        //return ecossistema.existsElement(x1, y1, x2, y2);
    }

    public void adicionarElementoInanimado(double width, double height) {
        double randomWidth, randomHeight;
        int randomWidthInt, randomHeightInt;
        do {
            do {
                randomWidthInt = getRandomNumber((int) width);
                randomWidth = (double) (randomWidthInt / pixelMultiplier) * pixelMultiplier;
            } while (randomWidth < tamBorder || randomWidth > width - 2 * tamBorder);
            do {
                randomHeightInt = getRandomNumber((int) height);
                randomHeight = (double) (randomHeightInt / pixelMultiplier) * pixelMultiplier;
            } while (randomHeight < tamBorder || randomHeight > height - 2 * tamBorder);
        } while (existsElement(randomWidth, randomHeight, randomWidth + pixelMultiplier, randomHeight + pixelMultiplier));

        Inanimado inanimadoTemp = new Inanimado();
        inanimadoTemp.setArea(new Area(randomWidth, randomWidth + tamBorder, randomHeight, randomHeight + tamBorder));
        ecossistema.addElemento(inanimadoTemp);
        this.pcs.firePropertyChange(PROP_ELEMENT, (Object) null, (Object) null);
    }

    public void adicionarElementoFlora(double width, double height) {
        double randomWidth, randomHeight;
        int randomWidthInt, randomHeightInt;
        do {
            do {
                randomWidthInt = getRandomNumber((int) width);
                randomWidth = (double) (randomWidthInt / pixelMultiplier) * pixelMultiplier;
            } while (randomWidth < tamBorder || randomWidth > width - 2 * tamBorder);
            do {
                randomHeightInt = getRandomNumber((int) height);
                randomHeight = (double) (randomHeightInt / pixelMultiplier) * pixelMultiplier;
            } while (randomHeight < tamBorder || randomHeight > height - 2 * tamBorder);
        } while (existsElement(randomWidth, randomHeight, randomWidth + pixelMultiplier, randomHeight + pixelMultiplier));

        Flora floraTemp = new Flora(this);
        floraTemp.setArea(new Area(randomWidth, randomWidth + tamBorder, randomHeight, randomHeight + tamBorder));
        ecossistema.addElemento(floraTemp);
        this.pcs.firePropertyChange(PROP_ELEMENT, (Object) null, (Object) null);
    }

    public void adicionarElementoFauna(double width, double height) {
        double randomWidth, randomHeight;
        int randomWidthInt, randomHeightInt;
        do {
            do {
                randomWidthInt = getRandomNumber((int) width);
                randomWidth = (double) (randomWidthInt / pixelMultiplier) * pixelMultiplier;
            } while (randomWidth < tamBorder || randomWidth > width - 2 * tamBorder);
            do {
                randomHeightInt = getRandomNumber((int) height);
                randomHeight = (double) (randomHeightInt / pixelMultiplier) * pixelMultiplier;
            } while (randomHeight < tamBorder || randomHeight > height - 2 * tamBorder);
        } while (existsElement(randomWidth, randomHeight, randomWidth + pixelMultiplier, randomHeight + pixelMultiplier));

        Fauna faunaTemp = new Fauna(this);
        faunaTemp.setArea(new Area(randomWidth, randomWidth + tamBorder, randomHeight, randomHeight + tamBorder));
        System.out.println(faunaTemp.getArea().toString());
        ecossistema.addElemento(faunaTemp);
        this.pcs.firePropertyChange(PROP_ELEMENT, (Object) null, (Object) null);
    }

    public Area convertToPixels(double mouseX, double mouseY) {
        // Convert coordinates to multiples of 20
        int x1 = (int) (mouseX / 20) * 20;
        int y1 = (int) (mouseY / 20) * 20;

        // Assuming x2 and y2 are the same as x1 and y1 for simplicity
        return new Area(x1, x1 + 20, y1, y1 + 20);
    }

    public void deletedDeadElement(int id, IElemento elementoToDelete) {
        IElemento elemento = getElementoByIdAndType(id, elementoToDelete);
        if (elemento != null) {
            ecossistema.removeElemento(elemento);
            this.pcs.firePropertyChange(PROP_ELEMENT, (Object) null, (Object) null);
        }
    }

    public void setForcaFlora(int id, double forca) {
        ecossistema.setForcaFlora(id, forca);
//        Flora flora = (Flora) ecossistema.getElementoByIdAndType(id, new Fauna(this));
//        flora.setForca(forca);
    }

    public void setForcaEVelocidadeFauna(int id, double forca, int velocidade) {
        ecossistema.setForcaEVelocidadeFauna(id, forca, velocidade);
        //(Fauna) ecossistema.getElementoByIdAndType(id, new Fauna(this).setForca(forca));
//        fauna.setForca(forca);
//        fauna.setVelocity(velocidade);
    }

    public void saveToCSV() throws IOException {
        ecossistema.save();
    }

    public void loadFromCSV() throws IOException {
        ecossistema.load(this);
    }
}
