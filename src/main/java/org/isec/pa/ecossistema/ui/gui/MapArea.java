package org.isec.pa.ecossistema.ui.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.data.ElementoBase;

public class MapArea extends Canvas {
    public static final String PROP_ELEMENT = "_element_";

    EcossistemaManager ecossistemaManager;
    public MapArea(EcossistemaManager ecossistemaManager) {
        //super(ecossistemaManager.getMapHeight(), ecossistemaManager.getMapWidth());
        super(ecossistemaManager.getMapWidth(), ecossistemaManager.getMapHeight());
        this.ecossistemaManager = ecossistemaManager;
        this.registerHandlers();
        this.spawnBorder();
        this.spawnRandoms();
        this.update();
    }

    private void registerHandlers() {
        this.ecossistemaManager.addPropertyChangeListener(PROP_ELEMENT, (evt) -> {
            this.update();
        });
//        this.setOnMousePressed((mouseEvent) -> {
//            this.ecossistemaManager.createFigure(mouseEvent.getX(), mouseEvent.getY());
//        });
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
        gc.fillRect(0.0, 0.0, ecossistemaManager.getMapWidth(), ecossistemaManager.getMapHeight());
    }

    public void spawnBorder(){
        ecossistemaManager.spawnBorder(1.1, 1.1, ecossistemaManager.getMapWidth(), ecossistemaManager.getMapHeight());
    }

    public void spawnRandoms(){
        ecossistemaManager.spawnRandoms(1.1, 1.1, ecossistemaManager.getMapWidth(), ecossistemaManager.getMapHeight());
    }

    private void drawFigure(GraphicsContext gc, ElementoBase element) {
        if (element != null) {
            //Color color = Color.color(element.getR(), element.getG(), element.getB());
            //Color color = Color.color(Cores.RED.getCode(), 0, 0);
            //gc.setFill(color);
            switch (element.getElemento()) {
                case INANIMADO:
                    gc.setStroke(Color.GREY.darker());
                    gc.setFill(Color.GREY);
                    gc.fillRect(element.getArea().x1(), element.getArea().y1(), element.getWidth(), element.getHeight());
                    gc.strokeRect(element.getArea().x1(), element.getArea().y1(), element.getWidth(), element.getHeight());
                    break;
                case FLORA:
                    gc.setStroke(Color.GREEN.darker());
                    gc.setFill(Color.GREEN);
                    gc.fillRect(element.getArea().x1(), element.getArea().y1(), element.getWidth(), element.getHeight());
                    gc.strokeRect(element.getArea().x1(), element.getArea().y1(), element.getWidth(), element.getHeight());
                    break;
                case FAUNA:
                    gc.setStroke(Color.MAGENTA.darker());
                    gc.setFill(Color.MAGENTA);
                    gc.fillRect(element.getArea().x1(), element.getArea().y1(), element.getWidth(), element.getHeight());
                    gc.strokeRect(element.getArea().x1(), element.getArea().y1(), element.getWidth(), element.getHeight());
            }
        }
    }

    public void updateSize(double newWidth, double newHeight) {
        this.setWidth(newWidth);
        this.setHeight(newHeight);
        this.update();
    }
}
