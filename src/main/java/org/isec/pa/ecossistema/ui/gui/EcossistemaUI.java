package org.isec.pa.ecossistema.ui.gui;

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
    }

    private void createViews() {
        this.mapArea = new MapArea(this.ecossistema);
        this.setTop(new VBox(new MapMenu(this.ecossistema, mapArea)));

        this.mapAreaPane = new Pane(this.mapArea);
        this.setCenter(this.mapAreaPane);
    }
}
