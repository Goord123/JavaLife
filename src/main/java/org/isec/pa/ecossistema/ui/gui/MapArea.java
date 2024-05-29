package org.isec.pa.ecossistema.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.data.ElementoBase;
import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.data.Flora;
import org.isec.pa.ecossistema.model.data.IElemento;
import org.isec.pa.ecossistema.utils.Area;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.util.List;

public class MapArea extends Canvas {
    public static final String PROP_ELEMENT = "_element_";

    EcossistemaManager ecossistemaManager;
//    private Stage infoWindow;
//    private VBox infoLayout;
//    private Scene infoScene;
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
            if(!listElements.isEmpty()){
                for(IElemento e : listElements){
//                    if(e.getElemento() == ElementoEnum.FAUNA || e.getElemento() == ElementoEnum.FLORA){
//                        IElementoComForca aux = (IElementoComForca) e;
//                        createWindowInfoComForca(aux, e);
//                    }
                    if(e.getElemento() == ElementoEnum.FAUNA){
                        Fauna aux = (Fauna) e;
                        createWindowInfoComFauna(aux);
                    } else if(e.getElemento() == ElementoEnum.FLORA) {
                        Flora aux = (Flora) e;
                        createWindowInfoComFlora(aux);
                    } else {
                        createWindowInfoInanimado(e);
                    }
                }

            }
        });
        //widthProperty().addListener() -> update();
//        this.setOnMouseDragged((mouseEvent) -> {
//            this.drawing.updateCurrentFigure(mouseEvent.getX(), mouseEvent.getY());
//        });
//        this.setOnMouseReleased((mouseEvent) -> {
//            this.drawing.finishCurrentFigure(mouseEvent.getX(), mouseEvent.getY());
//        });
    }

    public void update() {
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
                    //TODO mudar para a percentagem da força
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

    private void createWindowInfoInanimado(IElemento e){

        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Informação do Elemento");

        // Create a label for the element
        Label type = new Label("Type of Element: " + e.getElemento());
        Label elementLabel = new Label("ID: " + e.getId());
        Label area = new Label("Area: " + "  X1:" + e.getArea().x1() + "  Y1:" + e.getArea().y1() + "  X2:" + e.getArea().x2() + "  Y2:" + e.getArea().y2());
        layout.getChildren().addAll(type, elementLabel, area);

        Scene scene = new Scene(layout, 300, 200);

        window.setTitle("Informação do Elemento");
        window.setScene(scene);
        window.show();
    }

    private void createWindowInfoComFlora(Flora flora){
        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Informação do Elemento");

        // Create a label for the element
        Label type = new Label("Type of Element: " + flora.getElemento());
        Label elementLabel = new Label("ID: " + flora.getId());
        Label hp = new Label("Força: " + flora.getForca());
        Label area = new Label("Area: " + "  X1:" + flora.getArea().x1() + "  Y1:" + flora.getArea().y1() + "  X2:" + flora.getArea().x2() + "  Y2:" + flora.getArea().y2());
        //Label secondsToReproduce = new Label("Seconds to Reproduce: " + e.getSecondsToReproduce());
        layout.getChildren().addAll(type, hp, elementLabel, area);

        Scene scene = new Scene(layout, 300, 200);

        window.setTitle("Informação do Elemento");
        window.setScene(scene);
        window.show();
    }

    private void createWindowInfoComFauna(Fauna fauna){
        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Informação do Elemento");

        // Create a label for the element
        Label type = new Label("Type of Element: " + fauna.getElemento());
        Label elementLabel = new Label("ID: " + fauna.getId());
        Label forca = new Label("Forca: "+fauna.getForca());
        Label velocidade = new Label("Velocidade: "+fauna.getVelocity());
        Label area = new Label("Area: " + "  X1:" + fauna.getArea().x1() + "  Y1:" + fauna.getArea().y1() + "  X2:" + fauna.getArea().x2() + "  Y2:" + fauna.getArea().y2());
        //Label secondsToReproduce = new Label("Seconds to Reproduce: " + e.getSecondsToReproduce());
        layout.getChildren().addAll(type, elementLabel, forca, velocidade, area);


        Scene scene = new Scene(layout, 300, 200);

        window.setTitle("Informação do Elemento");
        window.setScene(scene);
        window.show();
    }
}
