package org.isec.pa.ecossistema.model;

import javafx.application.Platform;
import org.isec.pa.ecossistema.model.command.AddElementCommand;
import org.isec.pa.ecossistema.model.command.CommandManager;
import org.isec.pa.ecossistema.model.command.EditElementCommand;
import org.isec.pa.ecossistema.model.command.RemoveElementCommand;
import org.isec.pa.ecossistema.model.data.*;
import org.isec.pa.ecossistema.model.fsm.GameEngine.GameEngine;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngine;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngineEvolve;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.isec.pa.ecossistema.utils.UtilFunctions.getRandomNumber;

public class EcossistemaManager implements IGameEngineEvolve, Serializable {
    public static final String PROP_ELEMENT = "_element_";
    public static final String PROP_SWITCH_REDO = "_switchRedo_";
    public static final String PROP_SWITCH_UNDO = "_switchUndo_";
    private final int pixelMultiplier = 20;
    private final double tamBorder = 20;
    private final IGameEngine gameEngine;
    private Ecossistema ecossistema;
    private CommandManager commandManager;
    private double velocidadeDefault = 1;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public EcossistemaManager(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
        this.commandManager = new CommandManager();
        this.gameEngine = new GameEngine();
        this.gameEngine.start(1000);
        this.gameEngine.registerClient(this);
        ecossistema.setMapHeight(580);
        ecossistema.setMapWidth(800);

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
                    Fauna newFauna = new Fauna(ecossistema);
                    newFauna.setArea(areaForNewFauna);
                    elementsToAdd.add(newFauna);
                } else if (elemento instanceof Flora && ((Flora) elemento).reproduce()) {
                    ((Flora) elemento).setForca(60);
                    ((Flora) elemento).setTimesReproduced(((Flora) elemento).getTimesReproduced() + 1);
                    Area areaForNewFlora = ((Flora) elemento).getAdjacentArea();
                    if (areaForNewFlora != null) {
                        Flora newFlora = new Flora(ecossistema);
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

    public void switchUndo() {
        Platform.runLater(() -> {
            pcs.firePropertyChange(PROP_SWITCH_UNDO, null, null);
        });
    }

    public void switchRedo() {
        Platform.runLater(() -> {
            pcs.firePropertyChange(PROP_SWITCH_REDO, null, null);
        });
    }

    public double getTamBorder() {
        return ecossistema.getTamBorder();
    }

    public int getMapHeight() {
        return ecossistema.getMapHeight();
    }

    public void setMapHeight(int mapHeight) {
        ecossistema.setMapHeight(mapHeight);
    }

    public int getMapWidth() {
        return ecossistema.getMapWidth();
    }

    public void setMapWidth(int mapWidth) {
        ecossistema.setMapWidth(mapWidth);
    }

    public int getPixelMultiplier() {
        return ecossistema.getPixelMultiplier();
    }

    public void addElemento(IElemento elemento) {
        ecossistema.addElemento(elemento);
    }

    public void removeElemento(IElemento elemento) {
        ecossistema.getElementos().remove(elemento);
    }

    public double getForcaDefault() {
        return ecossistema.getForcaDefault();
    }

    public void setForcaDefault(double newForcaDefault) {
        ecossistema.setForcaDefault(newForcaDefault);
    }

    public Set<IElemento> getElementos() {
        return ecossistema.getElementos();
    }

    public void removeAllElementos() {
        ecossistema.removeAllElementos();
    }

    public IElemento getElementoByIdAndType(int id, ElementoEnum elemento) {
        return ecossistema.getElementoByIdAndType(id, elemento);
    }

    public Set<IElemento> getElementosByElemento(ElementoEnum elementoEnum) {
        return ecossistema.getElementosByElemento(elementoEnum);
    }

    public List<IElemento> getElementosByArea(Area area) {
        return ecossistema.getElementosByArea(area);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(property, listener);
    }

//    public void createFigure(double x, double y) {
//        ecossistema.createElement(x,y);
//        pcs.firePropertyChange(PROP_ELEMENT,null,null);
//    }

    public void spawnBorder(double x, double y, double width, double height) {
        ecossistema.spawnBorder(x, y, width, height);
    }

    public void spawnRandoms(double x, double y, double width, double height) {
        ecossistema.spawnRandoms(x, y, width, height);
    }

    public boolean existsElement(double x1, double y1, double x2, double y2) {
        return ecossistema.existsElement(x1, y1, x2, y2);
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

        Flora floraTemp = new Flora(ecossistema);
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

        Fauna faunaTemp = new Fauna(ecossistema);
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

    public void addElementCommand(ElementoEnum elementoEnum) {
        if (commandManager.invokeCommand(new AddElementCommand(ecossistema, elementoEnum))) {
            System.out.println("switched undo");
            switchUndo();
            refreshUI();
        }
    }


    public void editarElementoCommand(ElementoEnum elementoEnum, int id, double newForca, int newVelocidade) {
        if (commandManager.invokeCommand(new EditElementCommand(ecossistema, elementoEnum, id, newForca, newVelocidade))) {
            System.out.println("switched undo");
            switchUndo();
            refreshUI();
        }

    }

    public void removeElementoCommand(ElementoEnum elementoEnum, int id) {
        if (commandManager.invokeCommand(new RemoveElementCommand(ecossistema, elementoEnum, id))) {
            System.out.println("switched undo");
            switchUndo();
            refreshUI();
        }
    }

    public void undo() {
        commandManager.undo();
    }
    
    public Ecossistema getEcossistema() {
        return ecossistema;
    }
}
