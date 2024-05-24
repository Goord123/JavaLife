package org.isec.pa.ecossistema.ui.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.data.ElementoBase;

public class MapArea extends Canvas { // NAO DEVE SER O CANVAS
    EcossistemaManager ecossistemaManager;
    public MapArea(EcossistemaManager ecossistemaManager) {
        super(ecossistemaManager.getMapHeight(), ecossistemaManager.getMapWidth());
        this.ecossistemaManager = ecossistemaManager;
        this.registerHandlers();
        this.spawnBorder();
        this.spawnRandoms();
        this.update();
    }

    private void registerHandlers() {
        this.ecossistemaManager.addPropertyChangeListener("_element_", (evt) -> {
            this.update();
        });
        this.setOnMousePressed((mouseEvent) -> {
            this.ecossistemaManager.createFigure(mouseEvent.getX(), mouseEvent.getY());
        });
        //widthProperty().addListener() -> update();
//        this.setOnMouseDragged((mouseEvent) -> {
//            this.drawing.updateCurrentFigure(mouseEvent.getX(), mouseEvent.getY());
//        });
//        this.setOnMouseReleased((mouseEvent) -> {
//            this.drawing.finishCurrentFigure(mouseEvent.getX(), mouseEvent.getY());
//        });
    }

    private void update() {
        GraphicsContext gc = this.getGraphicsContext2D();
        this.clearScreen(gc);
        //this.spawnBorder();
        this.ecossistemaManager.getElementos().forEach((element) -> {
            this.drawFigure(gc, (ElementoBase) element);
        });
        //this.drawFigure(gc, this.ecossistema.getCurrentElement());
    }

    private void clearScreen(GraphicsContext gc) {
        gc.setFill(Color.WHITESMOKE);
        //gc.fillRect(0.0, 0.0, 500, 250);
        gc.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
    }

    public void spawnBorder(){
        ecossistemaManager.spawnBorder(1.1, 1.1, this.getWidth(), this.getHeight());
    }

    public void spawnRandoms(){
        ecossistemaManager.spawnRandoms(1.1, 1.1, this.getWidth(), this.getHeight());
    }

    private void drawFigure(GraphicsContext gc, ElementoBase element) {
        if (element != null) {
            //Color color = Color.color(element.getR(), element.getG(), element.getB());
            //Color color = Color.color(Cores.RED.getCode(), 0, 0);
            //gc.setFill(color);
            switch (element.getElemento()) {
                case INANIMADO:
                    gc.setStroke(Color.DARKGREY);
                    gc.setFill(Color.DARKGREY);
                    gc.fillRect(element.getX1(), element.getY1(), element.getWidth(), element.getHeight());
                    break;
                case FLORA:
                    gc.setStroke(Color.GREEN);
                    gc.setFill(Color.GREEN);
                    gc.fillRect(element.getX1(), element.getY1(), element.getWidth(), element.getHeight());
                    break;
                case FAUNA:
                    gc.setStroke(Color.YELLOW);
                    gc.setFill(Color.YELLOW);
                    gc.fillRect(element.getX1(), element.getY1(), element.getWidth(), element.getHeight());
            }
        }
    }

    public void updateSize(double newWidth, double newHeight) {
        this.setWidth(newWidth);
        this.setHeight(newHeight);
        this.update();
    }
}
