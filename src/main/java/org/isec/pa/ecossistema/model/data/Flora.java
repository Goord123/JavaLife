package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.io.*;
import java.util.List;

public non-sealed class Flora extends ElementoBase implements IElemento, Serializable, IElementoComImagem, IElementoComForca {
    @Serial
    private static final long serialVersionUID = 1L;
    private static int lastId = 0; // Static variable to keep track of the last ID used
    private final ElementoEnum elementoEnum = ElementoEnum.FLORA;
    private int id;
    private double forca;
    private int size;
    private int timesReproduced = 0;
    private Area area;
    private String imagem;
    private Ecossistema ecossistema;
    private boolean dead = false;
    private double forcaPorTurno;

    public Flora(Ecossistema ecossistema) {
        this.ecossistema = ecossistema;
        this.forca = ecossistema.getForcaDefault();
        this.id = ++lastId;
        this.forcaPorTurno = 1;
    }

    public void evolve() {
        if (this.forca <= 0) dead = true;

        this.forca = this.forca + forcaPorTurno;
        if (this.forca > 100) this.forca = 100;

        reproduce();
    }

    // GETTERS E SETTERS
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getForca() {
        return forca;
    }

    public void setForca(double forca) {
        this.forca = forca;
    }

    public int getTimesReproduced() {
        return timesReproduced;
    }

    public void setTimesReproduced(int timesReproduced) {
        this.timesReproduced = timesReproduced;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public double getForcaPorTurno() {
        return forcaPorTurno;
    }

    public void setForcaPorTurno(double forcaPorTurno) {
        this.forcaPorTurno = forcaPorTurno;
    }

    @Override
    public String getImagem() {
        return this.imagem;
    }

    @Override
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    // METODOS


    public Area getAdjacentArea() {
        double x1 = this.area.x1();
        double y1 = this.area.y1();
        Area areaToCheck = null;

        // Iterate over the 3x3 grid around the current position
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Skip the current position
                if (i == 0 && j == 0) continue;

                double newX1 = x1 + i * ecossistema.getPixelMultiplier();
                double newY1 = y1 + j * ecossistema.getPixelMultiplier();
                double newX2 = newX1 + ecossistema.getPixelMultiplier();
                double newY2 = newY1 + ecossistema.getPixelMultiplier();

                // Check bounds (ensure new coordinates are within map dimensions)
                if (newX1 < 0 || newY1 < 0 || newX2 > ecossistema.getMapWidth() || newY2 > ecossistema.getMapHeight()) {
                    continue;
                }

                areaToCheck = new Area(newX1, newX2, newY1, newY2);
                if (checkIfAreaWithinBounds(areaToCheck)) continue;
                List<IElemento> elementos = ecossistema.getElementosByArea(areaToCheck);

                // Check if the area is empty
                if (elementos.isEmpty()) {
                    return areaToCheck;
                }
            }
        }
        return areaToCheck;
    }

    public boolean checkIfAreaWithinBounds(Area areaToCheck) {
        return (areaToCheck.x1() < 0 &&
                areaToCheck.y1() < 0 &&
                areaToCheck.x2() > ecossistema.getMapWidth() &&
                areaToCheck.y2() > ecossistema.getMapHeight());
    }

    public boolean reproduce() {
        if (this.forca >= 90 && this.timesReproduced < 2) {
            Area areaNewFlora = getAdjacentArea();
            if (areaNewFlora != null) {
                return true;
            }
        }
        return false;
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
}
