package org.isec.pa.ecossistema.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.data.Ecossistema;

public class MainJFX extends Application {

    private EcossistemaManager manager;
    private Ecossistema ecossistema;

    public void init() throws Exception {
        super.init();
        this.ecossistema = new Ecossistema();
        this.manager = new EcossistemaManager(ecossistema);
    }

    @Override
    public void start(Stage stage) {
        this.createOneStage(stage);
    }

    private void createOneStage(Stage stage) {
        EcossistemaUI ecossistemaUI = new EcossistemaUI(this.manager);
        Scene scene = new Scene(ecossistemaUI,800,605);  //largura , altura
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Drawing@PA");
        stage.show();
    }
}
