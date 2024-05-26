package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngine;
import org.isec.pa.ecossistema.model.fsm.GameEngine.IGameEngineEvolve;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.io.Serializable;

public final class Flora extends ElementoBase implements IElemento, Serializable, IElementoComImagem, IElementoComForca, IGameEngineEvolve {

    private static int lastId = 0; // Static variable to keep track of the last ID used
    private final int id;
    private final ElementoEnum elementoEnum = ElementoEnum.FLORA;
    private double forca = 50;
    private int size;
    private int timesReproduced = 0;
    private Area area;
    private String imagem;

    public Flora(int size) {
        this.id = ++lastId;
        this.size = size;
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
        //TODO implementar
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

    public void setArea(Area area) {
        this.area = area;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTimesReproduced() {
        return timesReproduced;
    }

    // METODOS

    public void reproduce() {
        if (this.forca >= 90 && this.timesReproduced < 2) {
            this.timesReproduced++;
            this.forca = 60;
        }
    }

    public void die() {
        this.forca = 0;
    }

    @Override
    public String getImagem() {
        return this.imagem;
    }

    @Override
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
