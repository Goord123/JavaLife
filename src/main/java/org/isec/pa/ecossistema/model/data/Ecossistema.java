package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngine;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngineEvolve;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;
import org.isec.pa.ecossistema.utils.GameSaver;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.isec.pa.ecossistema.utils.UtilFunctions.getRandomNumber;

public class Ecossistema implements IGameEngineEvolve, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String FILE_PATH = "ecossistema.csv";
    private final int pixelMultiplier = 20;
    private final double tamBorder = 20;
    Set<IElemento> elementos = new HashSet<>();
    GameSaver gameSaver = new GameSaver();
    private int mapHeight;
    private int mapWidth;
    private double forcaDefault = 50;

    public Ecossistema() {
    }

    public void save() throws IOException {
        Set<IElemento> elementosToBeSaved = elementos;
        gameSaver.saveToCSV(elementosToBeSaved, FILE_PATH);
    }

    public void load() {
        try {
            Set<IElemento> elementosLoaded = gameSaver.loadFromCSV(this, FILE_PATH);
            setElementos(elementosLoaded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
    }

    public void addElemento(IElemento elemento) {
        synchronized (elementos) {
            elementos.add(elemento);
        }
    }

    public void removeAllElementos() {
        synchronized (elementos) {
            elementos.clear();
        }
    }

    public void removeElemento(IElemento elemento) {
        synchronized (elementos) {
            elementos.remove(elemento);
        }
    }

    public void removeElementos(Set<IElemento> elementsToRemove) {
        synchronized (elementos) {
            elementos.removeAll(elementsToRemove);
        }
    }

    public void addElementos(Set<IElemento> elementsToAdd) {
        synchronized (elementos) {
            elementos.addAll(elementsToAdd);
        }
    }

    public Set<IElemento> getElementos() {
        synchronized (elementos) {
            return new HashSet<>(elementos);
        }
    }

    public void setElementos(Set<IElemento> elementos) {
        synchronized (this.elementos) {
            this.elementos = elementos;
        }
    }

    public int getPixelMultiplier() {
        return pixelMultiplier;
    }

    public double getForcaDefault() {
        return forcaDefault;
    }

    public void setForcaDefault(double newForcaDefault) {
        forcaDefault = newForcaDefault;
    }

    public double getTamBorder() {
        return tamBorder;
    }

    public List<IElemento> getElementosByArea(Area area) {
        List<IElemento> elementosByArea = new ArrayList<>();
        synchronized (elementos) {
            for (IElemento e : elementos) {
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
        }
        return elementosByArea;
    }

    public boolean existsElement(double x1, double y1, double x2, double y2) {
        Area area = new Area(x1, x2, y1, y2);
        return !getElementosByArea(area).isEmpty();
    }

    public boolean mapHasSpace() {
        for (double widthCont = tamBorder; widthCont < mapWidth - tamBorder - pixelMultiplier; widthCont = widthCont + pixelMultiplier) {
            for (double heightCont = tamBorder; heightCont < mapHeight - tamBorder - pixelMultiplier; heightCont = heightCont + pixelMultiplier) {
                if (!existsElement(widthCont, heightCont, widthCont + pixelMultiplier, heightCont + pixelMultiplier)) {
                    return true;
                }
            }
        }
        return false;
    }

    public IElemento getElementoByIdAndType(int id, ElementoEnum elemento) {
        synchronized (elementos) {
            for (IElemento e : elementos) {
                if (e.getId() == id && e.getElemento().equals(elemento)) {
                    return e;
                }
            }
            return null;
        }
    }

    public Set<IElemento> getElementosByElemento(ElementoEnum elementoEnum) {
        Set<IElemento> elementosByElemento = new HashSet<>();
        synchronized (elementos) {
            for (IElemento e : elementos) {
                if (e.getElemento() == elementoEnum) {
                    elementosByElemento.add(e);
                }
            }
        }
        return elementosByElemento;
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

    public void spawnBorder(double x, double y, double width, double height) {
        for (double i = 0; i < width; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setBarreira(true);
            inanimadoTemp.setArea(new Area(i, i + tamBorder, 0, tamBorder));
            addElemento(inanimadoTemp);

        }
        for (double i = 0; i < width; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setBarreira(true);
            inanimadoTemp.setArea(new Area(i, i + tamBorder, height - tamBorder, height));
            addElemento(inanimadoTemp);

        }
        for (double i = 0; i < height; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setBarreira(true);
            inanimadoTemp.setArea(new Area(0, tamBorder, i, i + tamBorder));
            addElemento(inanimadoTemp);
        }
        for (double i = 0; i < height; i = i + tamBorder) {
            Inanimado inanimadoTemp = new Inanimado();
            inanimadoTemp.setBarreira(true);
            inanimadoTemp.setArea(new Area(width - tamBorder, width, i, i + tamBorder));
            addElemento(inanimadoTemp);
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
            addElemento(inanimadoTemp);
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
            addElemento(floraTemp);
        }
    }

    public void setForcaFlora(int id, double forca) {
        //System.out.println("Entrou set forca flora");
        synchronized (this.elementos) {
            for (IElemento e : elementos) {
                if (e.getElemento() == ElementoEnum.FLORA && e.getId() == id) {
                    Flora flora = (Flora) e;
                    flora.setForca(forca);
                    //System.out.println("deu set da forca");
                }
            }
        }
    }

    public void setForcaFauna(int id, double forca) {
        synchronized (this.elementos) {
            for (IElemento e : elementos) {
                if (e.getElemento() == ElementoEnum.FAUNA && e.getId() == id) {
                    Fauna fauna = (Fauna) e;
                    fauna.setForca(forca);
                }
            }
        }
    }

    public IElemento adicionarElementoCommand(ElementoEnum elementoEnum) {
        double randomWidth, randomHeight;
        int randomWidthInt, randomHeightInt;
        do {
            do {
                randomWidthInt = getRandomNumber((int) getMapWidth());
                randomWidth = (double) (randomWidthInt / getPixelMultiplier() * getPixelMultiplier());
            } while (randomWidth < getTamBorder() || randomWidth > getMapWidth() - 2 * getTamBorder());
            do {
                randomHeightInt = getRandomNumber((int) getMapHeight());
                randomHeight = (double) (randomHeightInt / getPixelMultiplier()) * getPixelMultiplier();
            } while (randomHeight < getTamBorder() || randomHeight > getMapHeight() - 2 * getTamBorder());
        } while (existsElement(randomWidth, randomHeight, randomWidth + getPixelMultiplier(), randomHeight + getPixelMultiplier()));

        if (elementoEnum.equals(ElementoEnum.INANIMADO)) {
            Inanimado elementoTemp = new Inanimado();
            elementoTemp.setArea(new Area(randomWidth, randomWidth + getTamBorder(), randomHeight, randomHeight + getTamBorder()));
            addElemento(elementoTemp);
            return elementoTemp;
        } else if (elementoEnum.equals(ElementoEnum.FLORA)) {
            Flora elementoTemp = new Flora(this);
            elementoTemp.setArea(new Area(randomWidth, randomWidth + getTamBorder(), randomHeight, randomHeight + getTamBorder()));
            addElemento(elementoTemp);
            return elementoTemp;
        } else if (elementoEnum.equals(ElementoEnum.FAUNA)) {
            Fauna elementoTemp = new Fauna(this);
            elementoTemp.setArea(new Area(randomWidth, randomWidth + getTamBorder(), randomHeight, randomHeight + getTamBorder()));
            addElemento(elementoTemp);
            return elementoTemp;
        }
        return null;
    }

    public boolean adicionarElementoCommandUndo(IElemento iElemento) {
        removeElemento(iElemento);
        return true;
    }

    public boolean editarElementoCommandUndo(int id, ElementoEnum tipoElemento, double forcaAntiga) {
        IElemento elemento = getElementoByIdAndType(id, tipoElemento);
        if(elemento == null)
            return false;

        if(tipoElemento.equals(ElementoEnum.FLORA)){
            setForcaFlora(id, forcaAntiga);
        } else if (tipoElemento.equals(ElementoEnum.FAUNA)){
            setForcaFauna(id, forcaAntiga);
        } else {
            return false;
        }
        return true;
    }

    public boolean removerElementoCommand(IElemento elemento) {
        removeElemento(elemento);
        return true;
    }

    public boolean removerElementoCommandUndo(IElemento elemento) {
        if(!mapHasSpace())
            return false;
        addElemento(elemento);
        return true;
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.gameSaver = new GameSaver();
    }
}
