package org.isec.pa.ecossistema.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.data.*;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.util.List;
import java.util.Objects;

public class MapArea extends Canvas {
    public static final String PROP_ELEMENT = "_element_";
    EcossistemaManager ecossistemaManager;


    public MapArea(EcossistemaManager ecossistemaManager) {
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
        this.setOnMousePressed((mouseEvent) -> {
            double mouseX = mouseEvent.getX();
            double mouseY = mouseEvent.getY();
            Area areaMouse = this.ecossistemaManager.convertToPixels(mouseX, mouseY);
            List<IElemento> listElements = this.ecossistemaManager.getElementosByArea(areaMouse);
            if (!listElements.isEmpty()) {
                for (IElemento e : listElements) {
                    if (e.getElemento() == ElementoEnum.FAUNA) {
                        Fauna aux = (Fauna) e;
                        createWindowInfoComFauna(aux);
                    } else if (e.getElemento() == ElementoEnum.FLORA) {
                        Flora aux = (Flora) e;
                        createWindowInfoComFlora(aux);
                    } else {
                        createWindowInfoInanimado(e);
                    }
                }

            }
        });
    }

    public void update() {
        GraphicsContext gc = this.getGraphicsContext2D();
        this.clearScreen(gc);
        this.ecossistemaManager.getElementos().forEach((element) -> {
            if (element instanceof Inanimado || element instanceof Flora)
                this.drawFigure(gc, (ElementoBase) element);
        });
        this.ecossistemaManager.getElementos().forEach((element) -> {
            if (element instanceof Fauna)
                this.drawFigure(gc, (ElementoBase) element);
        });
        this.ecossistemaManager.getElementos().forEach((element) -> {
            if (element instanceof Fauna || element instanceof Flora)
                this.drawForca(gc, (ElementoBase) element);
        });
    }

    private void clearScreen(GraphicsContext gc) {
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(0.0, 0.0, ecossistemaManager.getMapWidth(), ecossistemaManager.getMapHeight());
    }

    public void spawnBorder() {
        ecossistemaManager.spawnBorder(ecossistemaManager.getMapWidth(), ecossistemaManager.getMapHeight());
    }

    public void spawnRandoms() {
        ecossistemaManager.spawnRandoms(1.1, 1.1, ecossistemaManager.getMapWidth(), ecossistemaManager.getMapHeight());
    }

    private void drawFigure(GraphicsContext gc, ElementoBase element) {
        if (element != null) {
            switch (element.getElemento()) {
                case INANIMADO -> {
                    gc.setStroke(Color.GREY.darker());
                    gc.setFill(Color.GREY);
                    gc.fillRect(element.getArea().x1(), element.getArea().y1(), element.getWidth(), element.getHeight());
                    gc.strokeRect(element.getArea().x1(), element.getArea().y1(), element.getWidth(), element.getHeight());
                }
                case FLORA -> {
                    Flora floraAux = (Flora) element;
                    gc.setStroke(Color.GREEN.darker());
                    gc.setFill(new Color(0.0, 1.0, 0.0, floraAux.getForca() / 100.0));
                    gc.fillRect(element.getArea().x1(), element.getArea().y1(), element.getWidth(), element.getHeight());
                    gc.strokeRect(element.getArea().x1(), element.getArea().y1(), element.getWidth(), element.getHeight());
                }
                case FAUNA -> {
                    Fauna fauna = (Fauna) element;
                    String imagePath = fauna.getImagem();
                    if (imagePath != null && !imagePath.isEmpty()) {
                        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)), 20, 20, false, false);
                        gc.drawImage(image, element.getArea().x1(), element.getArea().y1(), element.getWidth(), element.getHeight());
                    } else {
                        gc.setStroke(Color.MAGENTA.darker());
                        gc.setFill(Color.MAGENTA);
                        gc.fillRect(element.getArea().x1(), element.getArea().y1(), element.getWidth(), element.getHeight());
                        gc.strokeRect(element.getArea().x1(), element.getArea().y1(), element.getWidth(), element.getHeight());
                    }
                }
            }
        }
    }

    private void drawForca(GraphicsContext gc, ElementoBase element) {
        if (element != null) {
            gc.setFill(Color.BLACK);  // Set the text color to black
            gc.setFont(new Font(10));  // Set the font size
            if (element.getElemento().equals(ElementoEnum.FLORA)) {
                Flora floraAux = (Flora) element;
                gc.fillText(String.valueOf(floraAux.getForca()), floraAux.getArea().x1(), floraAux.getArea().y1());  // Draw the number
            } else {
                Fauna faunaAux = (Fauna) element;
                gc.fillText(String.valueOf(faunaAux.getForca()), faunaAux.getArea().x1(), faunaAux.getArea().y1());  // Draw the number
            }
        }
    }

    private void createWindowInfoInanimado(IElemento e) {

        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        // Create a label for the element
        Label type = new Label("Tipo de Elemento: " + e.getElemento());
        Label elementLabel = new Label("ID: " + e.getId());
        Label area = new Label("Area: " + "  X1:" + e.getArea().x1() + "  Y1:" + e.getArea().y1() + "  X2:" + e.getArea().x2() + "  Y2:" + e.getArea().y2());
        layout.getChildren().addAll(type, elementLabel, area);

        Scene scene = new Scene(layout, 300, 200);

        window.setTitle("Informação do Elemento");
        window.setScene(scene);
        window.show();
    }

    private void createWindowInfoComFlora(Flora flora) {
        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        // Create a label for the element
        Label type = new Label("Tipo de Elemento: " + flora.getElemento());
        Label elementLabel = new Label("ID: " + flora.getId());
        Label hp = new Label("Força: " + flora.getForca());
        Label area = new Label("Area: " + "  X1:" + flora.getArea().x1() + "  Y1:" + flora.getArea().y1() + "  X2:" + flora.getArea().x2() + "  Y2:" + flora.getArea().y2());
        layout.getChildren().addAll(type, hp, elementLabel, area);

        Scene scene = new Scene(layout, 300, 200);

        window.setTitle("Informação do Elemento");
        window.setScene(scene);
        window.show();
    }

    private void createWindowInfoComFauna(Fauna fauna) {
        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        // Create a label for the element
        Label type = new Label("Tipo de Elemento: " + fauna.getElemento());
        Label elementLabel = new Label("ID: " + fauna.getId());
        Label forca = new Label("Forca: " + fauna.getForca());
        Label velocidade = new Label("Velocidade: " + fauna.getVelocity());
        Label area = new Label("Area: " + "  X1:" + fauna.getArea().x1() + "  Y1:" + fauna.getArea().y1() + "  X2:" + fauna.getArea().x2() + "  Y2:" + fauna.getArea().y2());
        layout.getChildren().addAll(type, elementLabel, forca, velocidade, area);


        Scene scene = new Scene(layout, 300, 200);

        window.setTitle("Informação do Elemento");
        window.setScene(scene);
        window.show();
    }
}
