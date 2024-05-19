package org.isec.pa.ecossistema.model.data;

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
    private final Ecossistema ecossistema;
    private double forca = 50;
    private int velocity;
    private DirectionEnum direction;
    private Area area;
    private int size;
    private int timesReproduced = 0;
    private int segundosParaReproduzir;
    private FaunaState state;

    public Fauna(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
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
    public FaunaState getState() {
        return this.state;
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


    // METODOS

    @Override
    public void move() {
        if (checkIfCanMove()) {
            double newUpperLeftX = area.upperLeft();
            double newUpperLeftY = area.upperLeft();
            double newLowerRightX = area.lowerRight();
            double newLowerRightY = area.lowerRight();

            switch (direction) {
                case UP -> {
                    newUpperLeftY -= velocity;
                    newLowerRightY -= velocity;
                }
                case DOWN -> {
                    newUpperLeftY += velocity;
                    newLowerRightY += velocity;
                }
                case LEFT -> {
                    newUpperLeftX -= velocity;
                    newLowerRightX -= velocity;
                }
                case RIGHT -> {
                    newUpperLeftX += velocity;
                    newLowerRightX += velocity;
                }
            }

            this.area = new Area(newUpperLeftX, newUpperLeftY, newLowerRightX, newLowerRightY);
        }
        this.forca--;
    }



    @Override
    public void reproduce() {
    }

    @Override
    public void die() {
    }

    @Override
    public void eat() {
        List<IElemento> elementos = this.ecossistema.getElementosByArea(this.area);
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

    public boolean checkIfCanMove() {
        List<IElemento> elementos = this.ecossistema.getElementosByArea(this.area);
        for (IElemento element : elementos) {
            if (element != this) {
                if (element.getElemento() == ElementoEnum.FAUNA ||
                        element.getElemento() == ElementoEnum.INANIMADO) {
                    return false;
                }
            }
        }
        return true;
    }
/*
    public void move() {
        if (checkIfAlive()) {
            Coords newCoords = new Coords(this.coords.getX(), this.coords.getY());
            switch (this.direction) {
                case UP -> newCoords.setY(newCoords.getY() - this.velocity);
                case DOWN -> newCoords.setY(newCoords.getY() + this.velocity);
                case LEFT -> newCoords.setX(newCoords.getX() - this.velocity);
                case RIGHT -> newCoords.setX(newCoords.getX() + this.velocity);
            }
            if (super.getField().checkIfValidMoveCoords(newCoords, this.size, this.height)) {
                super.field.moveElement(this, newCoords.getX(), newCoords.getY());
                this.coords = newCoords;
            }
            this.hp--;
        }
    }

    public void spawn(Coords coords) {
        Fauna newFauna = new Fauna();
        newFauna.setCoords(coords);
    }

    public Coords checkForAdjacentFlora() {
        // verifica as 3x3 celulas adjacentes por flora
        int x = this.coords.getX();
        int y = this.coords.getY();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                IElemento element = super.getField().getElementAt(x + i, y + j);
                if (element != null && element.getElemento() == ElementoEnum.FLORA) {
                    return new Coords(x + i, y + j);
                }
            }
        }
        return null;
    }

    public void reproduce() {
        if (this.hp <= 25) {
            this.die();
        }
        else {
            if (checkForAdjacentFlora() != null) {
                spawn(checkForAdjacentFlora());
                this.hp -= 25;
                this.timesReproduced++;
            }
        }
    }

    public void die() {
        this.forca = 0;
        super.removeElemento(this);
        //TODO: change state to dead
    }

    public void choosekDirection() {
        // escolhe um target e uma direcao baseada no target
    }
    */
/*
    public void hunt() {
        if (this.hp < 80) return;
        // verifica as celulas adjacentes
        int x = this.coords.getX();
        int y = this.coords.getY();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;
                IElemento element = super.getField().getElementAt(x + i, y + j);
                if (element != null && element.getElemento() == ElementoEnum.FAUNA) {
                    Fauna faunaTarget = (Fauna) element;
                    // CASO ESTEJAM OS DOIS À PROCURA DE COMIDA (ganha o mais forte)
                    if (faunaTarget.getHp() < 80 && faunaTarget.getState().equals(FaunaState.LOOKING_FOR_FAUNA)) {
                        if (this.hp < faunaTarget.hp) {
                            faunaTarget.hp += this.hp;
                            this.hp = 0;
                            this.die();
                        }
                        else {
                            this.hp += faunaTarget.hp - 10;
                            faunaTarget.die();
                        }
                    }
                    else { // tenta atacar e morre
                        this.hp -= 10;
                        if (this.hp <= 0) {
                            this.die();
                            faunaTarget.hp += this.hp;
                        }
                        else { // ataca e mata
                            faunaTarget.die();
                            this.hp += faunaTarget.hp;
                        }
                    }
                }
            }
        }
    }

    public boolean checkIfCanReproduce() {
        // vai ver se há um elemento de fauna à volta, que nao esteja à procura de outra fauna
        if (this.timesReproduced >= 2) return false;
        int x = this.coords.getX();
        int y = this.coords.getY();
        for (int i = -4; i <= 4; i++) {
            for (int j = -4; j <= 4; j++) {
                if (i == 0 && j == 0) continue;
                IElemento element = super.getField().getElementAt(x + i, y + j);
                if (element != null && element.getElemento() == ElementoEnum.FAUNA) {
                    Fauna faunaTarget = (Fauna) element;
                    if (!faunaTarget.getState().equals(FaunaState.LOOKING_FOR_FAUNA) && faunaTarget.getTimesReproduced() < 2) {
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
        return false;
    }
*/
}
