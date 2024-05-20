package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.IFaunaState;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.DirectionEnum;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.io.Serializable;
import java.util.List;

public final class Fauna extends ElementoBase implements IElemento, IFaunaState, Serializable, IElementoComForca {

    private static int lastId = 0; // Static variable to keep track of the last ID used
    private final int id;
    private final ElementoEnum elementoEnum = ElementoEnum.FAUNA;
    private final static int HP_PER_TICK_EATING = 10;
    private final EcossistemaManager ecossistemaManager;
    private double forca = 50;
    private int velocity;
    private DirectionEnum direction;
    private Area area;
    private int size;
    private int timesReproduced = 0;
    private int segundosParaReproduzir;
    private FaunaState currentState;

    public Fauna(EcossistemaManager ecossistemaManager) {
        this.ecossistemaManager = ecossistemaManager;
        this.id = ++lastId;
        this.velocity = 0;
        this.segundosParaReproduzir = 0;
    }

    // GETTERS E SETTERS
    @Override
    public int getId() {
        return id;
    }

    @Override
    public Area getArea() {
        return this.area;
    }

    @Override
    public ElementoEnum getElemento() {
        return elementoEnum;
    }

    @Override
    public FaunaState getCurrentState() {
        return this.currentState;
    }

    public double getForca() {
        return forca;
    }

    public void setForca(double forca) {
        this.forca = forca;
    }

    @Override
    public void evolve() {
        // TODO: implement evolve
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
    }

    public int getTimesReproduced() {
        return timesReproduced;
    }

    public int getSegundosParaReproduzir() {
        return segundosParaReproduzir;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setArea(Area area) {
        this.area = area;
    }


    // METODOS

    @Override
    public void move() {
        boolean continua = true;
        while (continua) {
            double newX1 = area.x1(); // top-left x
            double newY1 = area.y1(); // top-left y
            double newX2 = area.x2(); // bottom-right x
            double newY2 = area.y2(); // bottom-right y

            if (direction == null) {
                direction = DirectionEnum.values()[(int) (Math.random() * 4)];
            }

            switch (direction) {
                case UP -> {
                    newY1 -= velocity;
                    newY2 -= velocity;
                }
                case DOWN -> {
                    newY1 += velocity;
                    newY2 += velocity;
                }
                case LEFT -> {
                    newX1 -= velocity;
                    newX2 -= velocity;
                }
                case RIGHT -> {
                    newX1 += velocity;
                    newX2 += velocity;
                }
            }

            // Update the area with new coordinates
            Area aux  = new Area(newX1, newX2, newY1, newY2);
            if (checkIfCanMove(aux)) {
                this.area = aux;
                continua = false;
            } else {
                direction = DirectionEnum.values()[(int) (Math.random() * 4)];
            }
        }
        this.forca--;
    }

    @Override
    public void eat() {
        List<IElemento> elementos = this.ecossistemaManager.getElementosByArea(this.area);
        for (IElemento element : elementos) {
            if (element != this) {
                if (element.getElemento() == ElementoEnum.FLORA) {
                    Flora flora = (Flora) element;
                    if (flora.getForca() <= HP_PER_TICK_EATING) {
                        this.forca += flora.getForca();
                        flora.die();
                    } else {
                        flora.setForca(flora.getForca() - HP_PER_TICK_EATING);
                    }
                }
            }
        }
    }


    public boolean checkIfAlive() {
        if (this.forca <= 0) {
            this.die();
            return false;
        }
        return true;
    }

    @Override
    public void die() {
        this.forca = 0;
        ecossistemaManager.removeElemento(this);
    }

    public boolean checkIfCanMove(Area area) {
        // Get all elements in the specified area
        List<IElemento> elementos = this.ecossistemaManager.getElementosByArea(area);

        // Iterate through each element in the list
        for (IElemento element : elementos) {
            // Check if the element is of type FAUNA or INANIMADO
            if (element.getElemento() == ElementoEnum.FAUNA ||
                    element.getElemento() == ElementoEnum.INANIMADO) {
                // If found, return false indicating the area is occupied
                return false;
            }
        }

        // If no obstructing elements are found, return true indicating the area is free
        return true;
    }


    public void spawn(Area area) {
        Fauna newFauna = new Fauna(ecossistemaManager);
        newFauna.setArea(area);
        ecossistemaManager.addElemento(newFauna);
    }

    public Area checkForAdjacentFlora() {
        // Assuming we need to check the 3x3 cells around the top-left corner of the area
        double x1 = this.area.x1();
        double y1 = this.area.y1();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Area areaToCheck = new Area(x1 + i * size, x1 + (i + 1) * size, y1 + j * size, y1 + (j + 1) * size);
                List<IElemento> elementos = ecossistemaManager.getElementosByArea(areaToCheck);
                for (IElemento element : elementos) {
                    if (element.getElemento() == ElementoEnum.FLORA) {
                        return areaToCheck;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void reproduce() {
        if (this.forca <= 25) {
            this.die();
        } else {
            Area area = checkForAdjacentFlora();
            if (area != null) {
                spawn(area);
                this.forca -= 25;
                this.timesReproduced++;
            }
        }
    }

    public void hunt() {
        if (this.forca < 80) return;
        // Assuming we need to check the 3x3 cells around the top-left corner of the area
        double x1 = this.area.x1();
        double y1 = this.area.y1();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                Area areaToCheck = new Area(x1 + i * size, x1 + (i + 1) * size, y1 + j * size, y1 + (j + 1) * size);
                List<IElemento> elementos = ecossistemaManager.getElementosByArea(areaToCheck);
                for (IElemento element : elementos) {
                    if (element.getElemento() == ElementoEnum.FAUNA) {
                        Fauna faunaTarget = (Fauna) element;
                        if (faunaTarget.getForca() < 80 && faunaTarget.getCurrentState().equals(FaunaState.LOOKING_FOR_FAUNA)) {
                            if (this.forca < faunaTarget.getForca()) {
                                faunaTarget.setForca(faunaTarget.getForca() + this.forca);
                                this.die();
                            } else {
                                this.setForca(this.getForca() + faunaTarget.getForca() - 10);
                                faunaTarget.die();
                            }
                        } else {
                            this.setForca(this.getForca() - 10);
                            if (this.forca <= 0) {
                                this.die();
                                faunaTarget.setForca(faunaTarget.getForca() + this.forca);
                            } else {
                                faunaTarget.die();
                                this.setForca(this.getForca() + faunaTarget.getForca());
                            }
                        }
                    }
                }
            }
        }
    }


    public boolean checkIfCanReproduce() {
        if (this.timesReproduced >= 2) return false;
        double x1 = this.area.x1();
        double y1 = this.area.y1();
        for (int i = -4; i <= 4; i++) {
            for (int j = -4; j <= 4; j++) {
                if (i == 0 && j == 0) continue;
                Area areaToCheck = new Area(x1 + i * size, x1 + (i + 1) * size, y1 + j * size, y1 + (j + 1) * size);
                List<IElemento> elementos = ecossistemaManager.getElementosByArea(areaToCheck);
                for (IElemento element : elementos) {
                    if (element.getElemento() == ElementoEnum.FAUNA) {
                        Fauna faunaTarget = (Fauna) element;
                        if (!faunaTarget.getCurrentState().equals(FaunaState.LOOKING_FOR_FAUNA) && faunaTarget.getTimesReproduced() < 2) {
                            this.segundosParaReproduzir++;
                            if (this.segundosParaReproduzir == 10) {
                                this.segundosParaReproduzir = 0;
                                this.reproduce();
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkIfOnFlora() {
        List<IElemento> elementos = ecossistemaManager.getElementosByArea(this.area);
        for (IElemento element : elementos) {
            if (element.getElemento() == ElementoEnum.FLORA) {
                return true;
            }
        }
        return false;
    }

    public void moveTo(Area target) {
        double currentX1 = area.x1();
        double currentY1 = area.y1();
        double currentX2 = area.x2();
        double currentY2 = area.y2();

        double targetX1 = target.x1();
        double targetY1 = target.y1();
        double targetX2 = target.x2();
        double targetY2 = target.y2();

        // Determine direction based on the target coordinates
        if (currentY1 > targetY1) {
            // Move up
            direction = DirectionEnum.UP;
        } else if (currentY2 < targetY2) {
            // Move down
            direction = DirectionEnum.DOWN;
        } else if (currentX1 > targetX1) {
            // Move left
            direction = DirectionEnum.LEFT;
        } else if (currentX2 < targetX2) {
            // Move right
            direction = DirectionEnum.RIGHT;
        }

        // Move in the chosen direction
        move();
    }
}
