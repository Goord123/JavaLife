package org.isec.pa.ecossistema.model;

import javafx.application.Platform;
import org.isec.pa.ecossistema.model.GameEngine.GameEngine;
import org.isec.pa.ecossistema.model.GameEngine.IGameEngine;
import org.isec.pa.ecossistema.model.GameEngine.IGameEngineEvolve;
import org.isec.pa.ecossistema.model.command.AddElementCommand;
import org.isec.pa.ecossistema.model.command.CommandManager;
import org.isec.pa.ecossistema.model.command.EditElementCommand;
import org.isec.pa.ecossistema.model.command.RemoveElementCommand;
import org.isec.pa.ecossistema.model.data.Ecossistema;
import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.data.Flora;
import org.isec.pa.ecossistema.model.data.IElemento;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EcossistemaManager implements IGameEngineEvolve, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final String PROP_ELEMENT = "_element_";
    private transient IGameEngine gameEngine;
    private Ecossistema ecossistema;
    private CommandManager commandManager;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private int tickSpeed;

    public EcossistemaManager(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
        this.commandManager = new CommandManager();
        this.tickSpeed = 1000;
        this.gameEngine = new GameEngine();
        this.gameEngine.start(tickSpeed);
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
                } else if (elemento instanceof Fauna && ((Fauna) elemento).reproduce()) {
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
                ecossistema.removeElementos(elementsToRemove);
                ecossistema.addElementos(elementsToAdd);

                pcs.firePropertyChange(PROP_ELEMENT, null, null);
            }
        });
    }

    public void refreshUI() {
        Platform.runLater(() -> {
            pcs.firePropertyChange(PROP_ELEMENT, null, null);
        });
    }

    public int getMapHeight() {
        return ecossistema.getMapHeight();
    }

    public int getMapWidth() {
        return ecossistema.getMapWidth();
    }

    public void setForcaDefault(double newForcaDefault) {
        ecossistema.setForcaDefault(newForcaDefault);
    }

    public void setTickSpeed(int tickSpeed) {
        this.tickSpeed = tickSpeed;
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

    public List<IElemento> getElementosByArea(Area area) {
        return ecossistema.getElementosByArea(area);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(property, listener);
    }

    public void spawnBorder(double width, double height) {
        ecossistema.spawnBorder(width, height);
    }

    public void spawnRandoms(double x, double y, double width, double height) {
        ecossistema.spawnRandoms(x, y, width, height);
    }

    public Area convertToPixels(double mouseX, double mouseY) {
        // Convert coordinates to multiples of 20
        int x1 = (int) (mouseX / 20) * 20;
        int y1 = (int) (mouseY / 20) * 20;

        return new Area(x1, x1 + 20, y1, y1 + 20);
    }

    public void addElementCommand(ElementoEnum elementoEnum) {
        if (commandManager.invokeCommand(new AddElementCommand(ecossistema, elementoEnum))) {
            refreshUI();
        }
    }


    public void editarElementoCommand(ElementoEnum elementoEnum, int id, double newForca, int newVelocidade) {
        if (commandManager.invokeCommand(new EditElementCommand(ecossistema, elementoEnum, id, newForca, newVelocidade))) {
            refreshUI();
        }

    }

    public void removeElementoCommand(ElementoEnum elementoEnum, int id) {
        if (commandManager.invokeCommand(new RemoveElementCommand(ecossistema, elementoEnum, id))) {
            refreshUI();
        }
    }

    public void undo() {
        commandManager.undo();
    }


    public void redo() {
        commandManager.redo();
    }

    public Ecossistema getEcossistema() {
        return ecossistema;
    }

    public void saveToBinFile(File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(ecossistema);
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void openBinFile(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ecossistema = (Ecossistema) ois.readObject();
            EcossistemaManager manager = (EcossistemaManager) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // para serializar
    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    public void aplicarSolEvent() {
        for (IElemento elemento : ecossistema.getElementos()) {
            if (elemento instanceof Flora) {
                ((Flora) elemento).setForcaPorTurno(((Flora) elemento).getForcaPorTurno() * 2);
            }
        }
    }

    public void pauseGameEngine() {
        gameEngine.pause();
    }

    public void resumeGameEngine() {
        gameEngine.resume();
    }

    public void changeTickSpeed(int speed) {
        this.setTickSpeed(speed);
        gameEngine.setInterval(speed);
    }

    public void stopGameEngine() {
        gameEngine.stop();
    }

    public void startGameEngine() {
        gameEngine.start(tickSpeed);
    }
}
