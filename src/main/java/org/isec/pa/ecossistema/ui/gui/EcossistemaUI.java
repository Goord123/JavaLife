package org.isec.pa.ecossistema.ui.gui;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.isec.pa.ecossistema.model.EcossistemaManager;

public class EcossistemaUI extends BorderPane {
    EcossistemaManager ecossistema;
    MapArea mapArea;
    Pane mapAreaPane;

    public EcossistemaUI(EcossistemaManager ecossistema) {
        this.ecossistema = ecossistema;
        this.createViews();
        this.registerHandlers();
        this.update();
    }

    private void createViews() {
        //this.setTop(new VBox(new Node[]{new DrawingMenu(this.drawing), new DrawingToolbar(this.drawing)}));
        this.mapArea = new MapArea(this.ecossistema);
        this.mapAreaPane = new Pane(new Node[]{this.mapArea});
        this.setCenter(this.mapAreaPane);
    }

    private void registerHandlers() {
        this.mapAreaPane.widthProperty().addListener((observable) -> {
            this.mapArea.updateSize(this.mapAreaPane.getWidth(), this.mapAreaPane.getHeight());
        });
        this.mapAreaPane.heightProperty().addListener((observable) -> {
            this.mapArea.updateSize(this.mapAreaPane.getWidth(), this.mapAreaPane.getHeight());
        });
    }

    private void update() {
    }
}
