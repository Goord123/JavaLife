package org.isec.pa.ecossistema.model.data;

import org.isec.pa.ecossistema.utils.ElementoEnum;

public class Inanimado extends Ecossistema implements IElemento{
    private static int lastId = 0;
    private final int id;
    private final ElementoEnum elementoEnum = ElementoEnum.INANIMADO;

    public Inanimado() {
        this.id = ++lastId;
    }

    // GETTERS E SETTERS
    @Override
    public int getId() {
        return id;
    }
    @Override
    public ElementoEnum getElemento() {
        return elementoEnum;
    }

    /*public void generateBarrier(int height, int width){

    }

    public void generateInanimated(int number){
        // Create a Random object
        Random random = new Random();

        // Generate a random integer between 0 and 99
        int randomNumber = random.nextInt(100);
    }*/
}
