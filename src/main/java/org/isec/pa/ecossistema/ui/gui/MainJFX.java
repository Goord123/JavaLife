package org.isec.pa.ecossistema.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.data.Ecossistema;

public class MainJFX extends Application {

    private EcossistemaManager ecossistemaManager;
    private Ecossistema ecossistema;


    public void init() throws Exception {
        super.init();
        this.ecossistema = new Ecossistema();
        this.ecossistemaManager = new EcossistemaManager(ecossistema);
    }


    @Override
    public void start(Stage stage) {
        this.createOneStage(stage);
    }

    private void createOneStage(Stage stage) {
        EcossistemaUI ecossistemaUI = new EcossistemaUI(this.ecossistemaManager);
        Scene scene = new Scene(ecossistemaUI, ecossistemaManager.getMapWidth(), ecossistemaManager.getMapHeight() + 25);  //largura , altura
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("JavaLife");
        stage.show();
    }
}
