package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.IFaunaState;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngine;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngineEvolve;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.DirectionEnum;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.io.Serializable;
import java.util.List;

public final class Fauna extends ElementoBase implements IElemento, IFaunaState, Serializable, IElementoComForca, IGameEngineEvolve {

    private static int lastId = 0; // Static variable to keep track of the last ID used
    private final int id;
    private final ElementoEnum elementoEnum = ElementoEnum.FAUNA;
    private final static int HP_PER_TICK_EATING = 10;
    private final EcossistemaManager ecossistemaManager;
    private double forca;

    private int velocity;
    private DirectionEnum direction;
    private Area area;
    private Area target;
    private int size;



    private int timesReproduced = 0;
    private int segundosParaReproduzir;

    private IFaunaState currentState;
    private FaunaContext context;
    private final int pixelMultiplier;
    private Area reproductionArea;

    public Fauna(EcossistemaManager ecossistemaManager) {
        this.forca = ecossistemaManager.getForcaDefault();
        this.ecossistemaManager = ecossistemaManager;
        this.id = ++lastId;
        this.velocity = 1 * ecossistemaManager.getPixelMultiplier();
        this.pixelMultiplier = ecossistemaManager.getPixelMultiplier();
        this.segundosParaReproduzir = 0;
        this.context = new FaunaContext(ecossistemaManager,this);
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
    public void evolve(IGameEngine gameEngine, long currentTime) {
        context.evolve();
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

    @Override
    public void evolve() {
        // isto vai ser chamado pelo evolve do gameEngine
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

    public void setArea(Area area) {
        this.area = area;
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
            Area newArea  = new Area(newX1, newX2, newY1, newY2);

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
        List<IElemento> elementos = this.ecossistemaManager.getElementosByArea(this.area);
        for (IElemento element : elementos) {
            if (element != this) {
                if (element.getElemento() == ElementoEnum.FLORA) {
                    Flora flora = (Flora) element;
                    if (flora.getForca() <= HP_PER_TICK_EATING) {
                        this.forca += flora.getForca();
                        flora.die();
                        //this.ecossistemaManager.deletedDeadElement(flora.getId(), flora);
                        this.target = null;
                    } else {
                        flora.setForca(flora.getForca() - HP_PER_TICK_EATING);
                        if (this.forca >= 80) {
                            this.forca = 100;
                        } else {
                            this.forca += HP_PER_TICK_EATING;
                        }
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
        //this.area = new Area(0, 0, 0, 0);
        //ecossistemaManager.unregisterClient(this); // pára de fazer evolve
        // porque que isto está assim?
        // porque se tentarmos remover/modificar o HashSet durant eo evolve, é lançada uma
        // ConcurrentModificationException, logo mete-se a força a 0 e a area como inválida (n dá para ser null)
        // e no final do programa, removem-se os elementos com força 0 e area inválida
    }

    public boolean checkIfCanMove(Area area, Fauna fauna) {
        // Get all elements in the specified area
        List<IElemento> elementos = this.ecossistemaManager.getElementosByArea(area);
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


    public void spawn(Area area) {
        Fauna newFauna = new Fauna(ecossistemaManager);
        newFauna.setArea(area);
        ecossistemaManager.addElemento(newFauna);
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
                if (newX1 < 0 || newY1 < 0 || newX2 > ecossistemaManager.getMapWidth() || newY2 > ecossistemaManager.getMapHeight()) {
                    continue;
                }

                Area areaToCheck = new Area(newX1, newX2, newY1, newY2);
                List<IElemento> elementos = ecossistemaManager.getElementosByArea(areaToCheck);
                for (IElemento element : elementos) {
                    if (element.getElemento() == ElementoEnum.FLORA) {
                        System.out.println("Found flora");
                        return areaToCheck;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean reproduce() {
        if (this.forca <= 25) {
            this.die();
        } else {
            Area area = checkForAdjacentFlora();
            if (area != null) {
                //spawn(area);
                this.setReproductionArea(area);
                this.forca -= 25;
                this.timesReproduced++;
                return true;
            }
        }
        return false;
    }

    public void hunt() {
        if (this.forca < 80) return;
        // Assuming we need to check the 7x7 cells around the top-left corner of the area
        double x1 = this.area.x1();
        double y1 = this.area.y1();

        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                if (i == 0 && j == 0) continue;

                double newX1 = x1 + i * pixelMultiplier;
                double newY1 = y1 + j * pixelMultiplier;
                double newX2 = newX1 + pixelMultiplier;
                double newY2 = newY1 + pixelMultiplier;

                // Check bounds (ensure new coordinates are within map dimensions)
                if (newX1 < 0 || newY1 < 0 || newX2 > ecossistemaManager.getMapWidth() || newY2 > ecossistemaManager.getMapHeight()) {
                    continue;
                }

                Area areaToCheck = new Area(newX1, newX2, newY1, newY2);
                List<IElemento> elementos = ecossistemaManager.getElementosByArea(areaToCheck);

                for (IElemento element : elementos) {
                    if (element.getElemento() == ElementoEnum.FAUNA) {
                        Fauna faunaTarget = (Fauna) element;
                        if (faunaTarget.getForca() < 80 && faunaTarget.getCurrentState().equals(FaunaState.LOOKING_FOR_FAUNA.getInstance(context, faunaTarget))) {
                            if (this.forca < faunaTarget.getForca()) {
                                faunaTarget.setForca(faunaTarget.getForca() + this.forca);
                                this.die();
                            } else {
                                this.setForca(this.getForca() + faunaTarget.getForca() - 10);
                                faunaTarget.die();
                                this.target = null;
                            }
                        } else {
                            this.setForca(this.getForca() - 10);
                            if (this.forca <= 0) {
                                this.die();
                                faunaTarget.setForca(faunaTarget.getForca() + this.forca);
                            } else {
                                faunaTarget.die();
                                this.target = null;
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

                double newX1 = x1 + i * pixelMultiplier;
                double newY1 = y1 + j * pixelMultiplier;
                double newX2 = newX1 + pixelMultiplier;
                double newY2 = newY1 + pixelMultiplier;

                // Check bounds (ensure new coordinates are within map dimensions)
                if (newX1 < 0 || newY1 < 0 || newX2 > ecossistemaManager.getMapWidth() || newY2 > ecossistemaManager.getMapHeight()) {
                    continue;
                }

                Area areaToCheck = new Area(newX1, newX2, newY1, newY2);
                List<IElemento> elementos = ecossistemaManager.getElementosByArea(areaToCheck);

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
        List<IElemento> elementos = ecossistemaManager.getElementosByArea(this.area);
        for (IElemento element : elementos) {
            if (element.getElemento() == ElementoEnum.FLORA) {
                return true;
            }
        }
        return false;
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
                List<IElemento> elementos = ecossistemaManager.getElementosByArea(areaToCheck);
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

    public EcossistemaManager getEcossistemaManager() {
        return ecossistemaManager;
    }
}
