package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.IFaunaState;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.DirectionEnum;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.io.Serializable;
import java.util.List;

public final class Fauna extends ElementoBase implements IElemento, IFaunaState, Serializable, IElementoComForca {

    private final static int HP_PER_TICK_EATING = 20;
    private static int lastId = 0; // Static variable to keep track of the last ID used
    private final int id;
    private final ElementoEnum elementoEnum = ElementoEnum.FAUNA;
    private final Ecossistema ecossistema;
    private final int pixelMultiplier;
    private double forca;
    private int velocity;
    private DirectionEnum direction;
    private Area area;
    private Area target;
    private int size;
    private boolean dead = false;
    private int timesReproduced = 0;
    private int segundosParaReproduzir;
    private IFaunaState currentState;
    private FaunaContext context;
    private Area reproductionArea;

    public Fauna(Ecossistema ecossistema) {
        this.forca = ecossistema.getForcaDefault();
        this.ecossistema = ecossistema;
        this.id = ++lastId;
        this.velocity = 1 * ecossistema.getPixelMultiplier();
        this.pixelMultiplier = ecossistema.getPixelMultiplier();
        this.segundosParaReproduzir = 0;
        this.context = new FaunaContext(this);
    }
    
    @Override
    public void evolve() {
        context.evolve();
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

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public ElementoEnum getElemento() {
        return elementoEnum;
    }

    @Override
    public IFaunaState getCurrentState() {
        return this.currentState;
    }

    public void changeState(IFaunaState newState) {
        this.currentState = newState;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public double getForca() {
        return forca;
    }

    public void setForca(double forca) {
        this.forca = forca;
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

    public Area getTarget() {
        return target;
    }

    public void setTarget(Area target) {
        this.target = target;
    }


    // METODOS

    public void getDirectionOfTarget() {
        double currentX1 = area.x1();
        double currentY1 = area.y1();
        double currentX2 = area.x2();
        double currentY2 = area.y2();

        if (this.target == null) {
            this.direction = DirectionEnum.values()[(int) (Math.random() * 4)];
            move();
            return;
        }
        double targetX1 = this.target.x1();
        double targetY1 = this.target.y1();
        double targetX2 = this.target.x2();
        double targetY2 = this.target.y2();

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
            Area newArea = new Area(newX1, newX2, newY1, newY2);

            if (checkIfCanMove(newArea, this)) {
                this.area = newArea;
                continua = false;
            } else {
                direction = DirectionEnum.values()[(int) (Math.random() * 4)];
            }
        }
        this.forca--;
    }


    @Override
    public void eat() {
        List<IElemento> elementos = this.ecossistema.getElementosByArea(this.area);
        for (IElemento element : elementos) {
            if (element != this) {
                if (element.getElemento() == ElementoEnum.FLORA) {
                    Flora flora = (Flora) element;
                    // se flora nao tem vida suficiente para sobreviver
                    if (flora.getForca() <= HP_PER_TICK_EATING) {
                        this.forca += flora.getForca();
                        if (forca > 100) forca = 100;
                        flora.setDead(true);
                        this.target = null;
                    } else {
                        flora.setForca(flora.getForca() - HP_PER_TICK_EATING);
                        this.forca += HP_PER_TICK_EATING;
                        if (forca > 100) forca = 100;
                    }
                }
            }
        }
    }

    public boolean checkIfAlive() {
        if (this.forca <= 0) {
            dead = true;
            return false;
        }
        return true;
    }

    public boolean checkIfCanMove(Area area, Fauna fauna) {
        // Get all elements in the specified area
        List<IElemento> elementos = this.ecossistema.getElementosByArea(area);
        for (IElemento element : elementos) {
            if (element.getElemento() == ElementoEnum.INANIMADO) {
                return false;
            }
            if (element.getElemento() == ElementoEnum.FAUNA && element != fauna) {
                return false;
            }
        }

        // If no obstructing elements are found, return true indicating the area is free
        return true;
    }

    public Area checkForAdjacentFlora() {
        double x1 = this.area.x1();
        double y1 = this.area.y1();

        // Iterate over the 3x3 grid around the current position
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                // Skip the current position
                if (i == 0 && j == 0) continue;

                double newX1 = x1 + i * pixelMultiplier;
                double newY1 = y1 + j * pixelMultiplier;
                double newX2 = newX1 + pixelMultiplier;
                double newY2 = newY1 + pixelMultiplier;

                // Check bounds (ensure new coordinates are within map dimensions)
                if (newX1 < 0 || newY1 < 0 || newX2 > ecossistema.getMapWidth() || newY2 > ecossistema.getMapHeight()) {
                    continue;
                }

                Area areaToCheck = new Area(newX1, newX2, newY1, newY2);
                if (checkIfAreaWithinBounds(areaToCheck)) continue;
                List<IElemento> elementos = ecossistema.getElementosByArea(areaToCheck);
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
    public boolean reproduce() {
        if (checkIfCanReproduce()) {
            if (this.forca <= 25) {
                dead = true;
                return false;
            } else {
                Area area = checkForAdjacentFlora();
                if (area != null) {
                    this.setReproductionArea(area);
                    this.forca -= 25;
                    this.timesReproduced++;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hunt() {
        // Assuming we need to check the 3x3 cells around the top-left corner of the area
        double x1 = this.area.x1();
        double y1 = this.area.y1();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                double newX1 = x1 + i * pixelMultiplier;
                double newY1 = y1 + j * pixelMultiplier;
                double newX2 = newX1 + pixelMultiplier;
                double newY2 = newY1 + pixelMultiplier;

                // Check bounds (ensure new coordinates are within map dimensions)
                if (newX1 < 0 || newY1 < 0 || newX2 > ecossistema.getMapWidth() || newY2 > ecossistema.getMapHeight()) {
                    continue;
                }

                Area areaToCheck = new Area(newX1, newX2, newY1, newY2);
                if (checkIfAreaWithinBounds(areaToCheck)) continue;
                List<IElemento> elementos = ecossistema.getElementosByArea(areaToCheck);

                for (IElemento element : elementos) {
                    if (element.getElemento() == ElementoEnum.FAUNA) {
                        Fauna faunaTarget = (Fauna) element;
                        if (faunaTarget.getForca() < 80 && faunaTarget.getCurrentState().equals(FaunaState.LOOKING_FOR_FAUNA.getInstance(context, faunaTarget))) {
                            if (this.forca < faunaTarget.getForca()) {
                                faunaTarget.setForca(faunaTarget.getForca() + this.forca - 10);
                                if (faunaTarget.getForca() > 100) faunaTarget.setForca(100);
                                dead = true;
                                return true;
                            } else {
                                this.setForca(this.getForca() + faunaTarget.getForca());
                                if (this.forca > 100) this.setForca(100);
                                faunaTarget.setDead(true);
                                this.target = null;
                                return true;
                            }
                        } else {
                            this.setForca(this.getForca() - 10);
                            if (this.forca <= 0) {
                                dead = true;
                                faunaTarget.setForca(faunaTarget.getForca() + this.forca);
                                if (faunaTarget.getForca() > 100) faunaTarget.setForca(100);
                                return true;
                            } else {
                                faunaTarget.setDead(true);
                                this.target = null;
                                this.setForca(this.getForca() + faunaTarget.getForca());
                                if (this.forca > 100) this.setForca(100);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    public boolean checkIfCanReproduce() {
        if (this.timesReproduced >= 2) return false;

        double x1 = this.area.x1();
        double y1 = this.area.y1();
        // 5*5 grid
        for (int i = -4; i <= 4; i++) {
            for (int j = -4; j <= 4; j++) {
                if (i == 0 && j == 0) continue;

                double newX1 = x1 + i * pixelMultiplier;
                double newY1 = y1 + j * pixelMultiplier;
                double newX2 = newX1 + pixelMultiplier;
                double newY2 = newY1 + pixelMultiplier;

                // Check bounds (ensure new coordinates are within map dimensions)
                if (newX1 < 0 || newY1 < 0 || newX2 > ecossistema.getMapWidth() || newY2 > ecossistema.getMapHeight()) {
                    continue;
                }

                Area areaToCheck = new Area(newX1, newX2, newY1, newY2);
                if (checkIfAreaWithinBounds(areaToCheck)) continue;
                List<IElemento> elementos = ecossistema.getElementosByArea(areaToCheck);

                for (IElemento element : elementos) {
                    if (element.getElemento() == ElementoEnum.FAUNA) {
                        Fauna faunaTarget = (Fauna) element;
                        if (!faunaTarget.getCurrentState().equals(FaunaState.LOOKING_FOR_FAUNA.getInstance(context, this))
                                && faunaTarget.getTimesReproduced() < 2) {
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
        List<IElemento> elementos = ecossistema.getElementosByArea(this.area);
        for (IElemento element : elementos) {
            if (element.getElemento() == ElementoEnum.FLORA) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfAreaWithinBounds(Area areaToCheck) {
        return (areaToCheck.x1() < 0 &&
                areaToCheck.y1() < 0 &&
                areaToCheck.x2() > ecossistema.getMapWidth() &&
                areaToCheck.y2() > ecossistema.getMapHeight());
    }


    public Area lookForWeakestFauna() {

        // Assuming we need to check the 9x9 cells around the top-left corner of the area
        double x1 = this.area.x1();
        double y1 = this.area.y1();
        int weakestForca = 100;
        Area weakestFaunaArea = null;

        for (int i = -4; i <= 4; i++) {
            for (int j = -4; j <= 4; j++) {
                if (i == 0 && j == 0) continue;
                Area areaToCheck = new Area(x1 + i * pixelMultiplier, x1 + (i + 1) * pixelMultiplier, y1 + j * pixelMultiplier, y1 + (j + 1) * pixelMultiplier);
                if (checkIfAreaWithinBounds(areaToCheck)) continue;
                List<IElemento> elementos = ecossistema.getElementosByArea(areaToCheck);
                for (IElemento element : elementos) {
                    if (element.getElemento() == ElementoEnum.FAUNA && element != this) {
                        if (((Fauna) element).getForca() < weakestForca) {
                            weakestFaunaArea = areaToCheck;
                        }
                    }
                }
            }
        }

        return weakestFaunaArea;
    }

    public Area getReproductionArea() {
        return reproductionArea;
    }

    public void setReproductionArea(Area reproductionArea) {
        this.reproductionArea = reproductionArea;
    }
}
