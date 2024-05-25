package org.isec.pa.ecossistema.ui.gui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import org.isec.pa.ecossistema.model.EcossistemaManager;

import java.io.File;

public class MapMenu extends MenuBar {
    EcossistemaManager ecossistemaManager;
    Menu mnFicheiro;
    Menu mnEcossistema;
    Menu mnSimulacao;
    Menu mnEventos;
    MenuItem mnCriar;
    MenuItem mnAbrir;
    MenuItem mnGravar;
    MenuItem mnExportar;
    MenuItem mnImportar;
    MenuItem mnSair;
    MenuItem mnConfigGeraisEcossistema;
    MenuItem mnAdicionarElementoInanimado;
    MenuItem mnAdicionarElementoFlora;
    MenuItem mnAdicionarElementoFauna;
    MenuItem mnEditarElemento;
    MenuItem mnEliminarElemento;
    MenuItem mnUndo;
    MenuItem mnRedo;
    MenuItem mnConfigSimulacao;
    MenuItem mnExecutar;
    MenuItem mnParar;
    MenuItem mnPausar;
    MenuItem mnContinuar;
    MenuItem mnGravarSnapshot;
    MenuItem mnRestaurarSnapshot;
    MenuItem mnAplicarSol;
    MenuItem mnAplicarHerbicida;
    MenuItem mnInjetarForca;
    public MapMenu(EcossistemaManager ecossistemaManager) {
        this.ecossistemaManager = ecossistemaManager;
        this.createViews();
        this.registerHandlers();
        this.update();
    }
    private void createViews() {
        this.mnFicheiro = new Menu("Ficheiro");
        this.mnEcossistema = new Menu("Ecossistema");
        this.mnSimulacao = new Menu("Simulação");
        this.mnEventos = new Menu("Eventos");
        this.mnCriar = new MenuItem("_Criar");
        this.mnAbrir = new MenuItem("_Abrir");
        this.mnGravar = new MenuItem("_Gravar");
        this.mnExportar = new MenuItem("_Exportar");
        this.mnImportar = new MenuItem("_Importar");
        this.mnSair = new MenuItem("_Sair");
        this.mnConfigGeraisEcossistema = new MenuItem("_Configurações Gerais do Ecossistema");
        this.mnAdicionarElementoInanimado = new MenuItem("_Adicionar Elemento Inanimado");
        this.mnAdicionarElementoFlora = new MenuItem("_Adicionar Elemento Flora");
        this.mnAdicionarElementoFauna = new MenuItem("_Adicionar Elemento Fauna");
        this.mnEditarElemento = new MenuItem("_Editar Elemento");
        this.mnEliminarElemento = new MenuItem("_Eliminar Elemento");
        this.mnUndo = new MenuItem("_Undo");
        this.mnRedo = new MenuItem("_Redo");
        this.mnConfigSimulacao = new MenuItem("_Configuração da Simulacao");
        this.mnExecutar = new MenuItem("_Executar");
        this.mnParar = new MenuItem("_Parar");
        this.mnPausar = new MenuItem("_Pausar");
        this.mnContinuar = new MenuItem("_Continuar");
        this.mnGravarSnapshot = new MenuItem("_Gravar Snapshot");
        this.mnRestaurarSnapshot = new MenuItem("_Restaurar Snapshot");
        this.mnAplicarSol = new MenuItem("_Aplicar Sol");
        this.mnAplicarHerbicida = new MenuItem("_Aplicar Herbicida");
        this.mnInjetarForca = new MenuItem("_Injetar Forca");


        //this.mnOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, new KeyCombination.Modifier[]{KeyCodeCombination.ALT_DOWN}));
        this.mnFicheiro.getItems().addAll(new MenuItem[]{this.mnCriar, this.mnAbrir, this.mnGravar, new SeparatorMenuItem(), this.mnExportar, this.mnImportar, this.mnSair});
        this.mnEcossistema.getItems().addAll(new MenuItem[]{this.mnConfigGeraisEcossistema, this.mnAdicionarElementoInanimado, this.mnAdicionarElementoFlora, this.mnAdicionarElementoFauna, this.mnEditarElemento, this.mnEliminarElemento, this.mnUndo, this.mnRedo});
        this.mnSimulacao.getItems().addAll(new MenuItem[]{this.mnConfigSimulacao, this.mnExecutar, this.mnParar, this.mnPausar, this.mnContinuar, this.mnGravarSnapshot, this.mnRestaurarSnapshot});
        this.mnEventos.getItems().addAll(new MenuItem[]{this.mnAplicarSol, this.mnAplicarHerbicida, this.mnInjetarForca});
        this.getMenus().addAll(new Menu[]{this.mnFicheiro, this.mnEcossistema, this.mnSimulacao, this.mnEventos});
    }

    private void registerHandlers() {
        this.mnAdicionarElementoInanimado.setOnAction((event) -> {
            this.ecossistemaManager.adicionarElementoInanimado(ecossistemaManager.getMapWidth(), ecossistemaManager.getMapHeight());
        });
        this.mnAdicionarElementoFlora.setOnAction((event) -> {
            this.ecossistemaManager.adicionarElementoFlora(ecossistemaManager.getMapWidth(), ecossistemaManager.getMapHeight());
        });
        this.mnAdicionarElementoFauna.setOnAction((event) -> {
            this.ecossistemaManager.adicionarElementoFauna(ecossistemaManager.getMapWidth(), ecossistemaManager.getMapHeight());
        });
//        this.mnNew.setOnAction((event) -> {
//            this.drawing.clearAll();
//        });
//        this.mnOpen.setOnAction((e) -> {
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("File open...");
//            fileChooser.setInitialDirectory(new File("."));
//            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("Drawing (*.dat)", new String[]{"*.dat"}), new FileChooser.ExtensionFilter("All", new String[]{"*.*"})});
//            File hFile = fileChooser.showOpenDialog(this.getScene().getWindow());
//            if (hFile != null) {
//                this.drawing.load(hFile);
//            }
//
//        });
//        this.mnSave.setOnAction((e) -> {
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("File save...");
//            fileChooser.setInitialDirectory(new File("."));
//            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("Drawing (*.dat)", new String[]{"*.dat"}), new FileChooser.ExtensionFilter("All", new String[]{"*.*"})});
//            File hFile = fileChooser.showSaveDialog(this.getScene().getWindow());
//            if (hFile != null) {
//                this.drawing.save(hFile);
//            }
//
//        });
    }

    private void update() {
        this.mnUndo.setDisable(true);
        this.mnRedo.setDisable(true);
    }
}
