package org.isec.pa.ecossistema.ui.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.data.ElementoBase;
import org.isec.pa.ecossistema.model.data.Inanimado;
import org.isec.pa.ecossistema.utils.Cores;

public class MapArea extends Canvas { // NAO DEVE SER O CANVAS
    EcossistemaManager ecossistema;
    public MapArea(EcossistemaManager ecossistema) {
        super(ecossistema.getMapHeight(), ecossistema.getMapWidth());
        this.ecossistema = ecossistema;
        this.registerHandlers();
        this.update();
    }

    private void registerHandlers() {
        this.ecossistema.addPropertyChangeListener("_figures_", (evt) -> {
            this.update();
        });
        this.setOnMousePressed((mouseEvent) -> {
            this.ecossistema.createFigure(mouseEvent.getX(), mouseEvent.getY());
        });
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
        this.spawnBorder();
        this.ecossistema.getElementos().forEach((element) -> {
            this.drawFigure(gc, (ElementoBase) element);
        });
        //this.drawFigure(gc, this.ecossistema.getCurrentElement());
    }

    private void clearScreen(GraphicsContext gc) {
        gc.setFill(Color.LIGHTGREEN);
        //gc.fillRect(0.0, 0.0, 500, 250);
        gc.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
    }

    public void spawnBorder(){
        ecossistema.spawnBorder(1.1, 1.1, this.getWidth(), this.getHeight());
    }

    private void drawFigure(GraphicsContext gc, ElementoBase element) {
        if (element != null) {
            //Color color = Color.color(element.getR(), element.getG(), element.getB());
            Color color = Color.color(Cores.RED.getCode(), 0, 0);
            gc.setFill(color);
            switch (element.getElemento()) {
                case INANIMADO:
                    gc.setStroke(color);
                    gc.fillRect(element.getX1(), element.getY1(), element.getWidth(), element.getHeight());
                    break;
                case FLORA:
//                    gc.fillRect(element.getX1(), element.getY1(), element.getWidth(), element.getHeight());
//                    //gc.setStroke(color.darker());
//                    gc.strokeRect(element.getX1(), element.getY1(), element.getWidth(), element.getHeight());
                    break;
                case FAUNA:
//                    gc.fillOval(element.getX1(), element.getY1(), element.getWidth(), element.getHeight());
//                    //gc.setStroke(color.darker());
//                    gc.strokeRect(element.getX1(), element.getY1(), element.getWidth(), element.getHeight());
            }
        }
    }

    public void updateSize(double newWidth, double newHeight) {
        this.setWidth(newWidth);
        this.setHeight(newHeight);
        this.update();
    }
}
