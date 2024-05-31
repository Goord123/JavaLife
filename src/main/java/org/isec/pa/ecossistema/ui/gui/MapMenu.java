package org.isec.pa.ecossistema.ui.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.isec.pa.ecossistema.model.EcossistemaManager;

import java.io.IOException;

public class MapMenu extends MenuBar {
    EcossistemaManager ecossistemaManager;
    MapArea mapArea;
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

    public MapMenu(EcossistemaManager ecossistemaManager, MapArea mapArea) {
        this.ecossistemaManager = ecossistemaManager;
        this.mapArea = mapArea;
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
        this.mnConfigSimulacao = new MenuItem("_Configuração da Simulação");
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
        this.mnConfigGeraisEcossistema.setOnAction((event) -> {
            openCustomWindowConfigGeraisEcossistema();
        });
        this.mnConfigSimulacao.setOnAction((event) -> {
            openCustomWindowConfigSimulacao();
        });
        this.mnCriar.setOnAction((event) -> {
            //dá clean a tudo (Set com elementos e ecrã)
            ecossistemaManager.removeAllElementos();
            mapArea.update();
            // Como limpar a area do mapa vizualmente?
            openCustomWindowConfigSimulacaoInicio();
            mapArea.spawnBorder();
            mapArea.spawnRandoms();
            mapArea.update();
        });
        this.mnEditarElemento.setOnAction((event) -> {
            openCustomWindowEditarElemento();
        });
        this.mnEliminarElemento.setOnAction((event) -> {
            openCustomWindowEliminarElemento();
        });
        this.mnGravar.setOnAction((event) -> {
            try {
                ecossistemaManager.saveToCSV();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Ecossistema Gravado");
        });
        this.mnAbrir.setOnAction((event) -> {
            try {
                ecossistemaManager.loadFromCSV();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Ecossistema Carregado");
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

    private void openCustomWindowConfigGeraisEcossistema() {
        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label instructionLabelForca = new Label("Força: ");
        TextField inputFieldForca = new TextField();
        Label instructionLabelVel = new Label("Velocidade: ");
        TextField inputFieldVel = new TextField();
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button submitButton = new Button("Confirmar");
        submitButton.setOnAction(event -> {
            String input1 = inputFieldForca.getText();
            String input2 = inputFieldVel.getText();
            if (isNumeric(input1) && isNumeric(input2)) {
                float userInput1 = (float) Integer.parseInt(input1);
                float userInput2 = (float) Integer.parseInt(input2);
                System.out.println("Força: " + userInput1);
                System.out.println("Velocidade: " + userInput2);
                //EcossistemaManager.setForcaDefault(userInput1);
                //EcossistemaManager.setVelocidadeAll(userInput2);
                window.close();
                //mudar aqui valores
            } else {
                errorLabel.setText("Ambos os campos devem ser preenchidos com NUMEROS INTEIROS");
            }
        });

        Button closeButton = new Button("Cancelar");
        closeButton.setOnAction(event -> window.close());

        layout.getChildren().addAll(instructionLabelForca, inputFieldForca, instructionLabelVel, inputFieldVel, submitButton, errorLabel, closeButton);

        Scene scene = new Scene(layout, 400, 250);
        window.setTitle("Configurações Gerais do Ecossistema");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }


    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void openCustomWindowEditarElemento() {
        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label instructionLabelId = new Label("ID do elemento: ");
        TextField inputFieldId = new TextField();
        Label instructionLabelTipo = new Label("Tipo do elemento [FAUNA ou FLORA]: ");
        TextField inputFieldTipo = new TextField();
        Label instructionLabelForca = new Label("Força: ");
        TextField inputFieldForca = new TextField();
        Label instructionLabelVel = new Label("Velocidade: ");
        TextField inputFieldVel = new TextField();
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button submitButton = new Button("Confirmar");
        submitButton.setOnAction(event -> {
            String input1 = inputFieldId.getText();
            String input2 = inputFieldTipo.getText();
            String input3 = inputFieldForca.getText();
            String input4 = inputFieldVel.getText();
            if (isNumeric(input1) && isNumeric(input3) && isNumeric(input4)) {
                int userInput1 = Integer.parseInt(input1);
                double userInput3 = (double) Integer.parseInt(input3);
                int userInput4 = Integer.parseInt(input4);
                System.out.println("ID: " + userInput1);
                System.out.println("Força: " + userInput3);
                System.out.println("Velocidade: " + userInput4);
                if (input2.equalsIgnoreCase("FLORA")) {
                    ecossistemaManager.setForcaFlora(userInput1, userInput3);
                } else if (input2.equalsIgnoreCase("FAUNA")) {
                    ecossistemaManager.setForcaEVelocidadeFauna(userInput1, userInput3, userInput4);
                } else {
                    errorLabel.setText("Tipo de elemento deve ser um dos 2: FLORA ou FAUNA");
                }

                window.close();
            } else {
                errorLabel.setText("Ambos os campos devem ser preenchidos com NUMEROS INTEIROS");
            }
        });

        Button closeButton = new Button("Cancelar");
        closeButton.setOnAction(event -> window.close());

        layout.getChildren().addAll(instructionLabelId, inputFieldId, instructionLabelTipo, inputFieldTipo, instructionLabelForca, inputFieldForca, instructionLabelVel, inputFieldVel, submitButton, errorLabel, closeButton);

        Scene scene = new Scene(layout, 400, 400);
        window.setTitle("Configurações Gerais do Ecossistema");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    private void openCustomWindowEliminarElemento() {
        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label instructionLabelId = new Label("ID do elemento: ");
        TextField inputFieldId = new TextField();
        Label instructionLabelType = new Label("Tipo do elemento [INANIMADO, FAUNA, FLORA]: ");
        TextField inputFieldType = new TextField();
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button submitButton = new Button("Confirmar");
        submitButton.setOnAction(event -> {
            String input1 = inputFieldId.getText();
            if (isNumeric(input1)) {
                int userInput1 = Integer.parseInt(input1);
                // Convert to Enum (inputFieldType) ?
                //ecossistemaManager.removeElemento(EcossistemaManager.getElementoByIdAndType(userInput1, ConversionResult));

                System.out.println("ID eliminado: " + userInput1);
                window.close();
                //mudar aqui valores
            } else {
                errorLabel.setText("Campo deve ser preenchido com NUMEROS INTEIROS");
            }
        });

        Button closeButton = new Button("Cancelar");
        closeButton.setOnAction(event -> window.close());

        layout.getChildren().addAll(instructionLabelId, inputFieldId, submitButton, errorLabel, closeButton);

        Scene scene = new Scene(layout, 400, 350);
        window.setTitle("Configurações Gerais do Ecossistema");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    private void openCustomWindowConfigSimulacaoInicio() {
        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label instructionLabelTimeUnit = new Label("Unidade de tempo de cada movimentação: ");
        TextField inputFieldTimeUnit = new TextField();
        Label instructionLabelHeight = new Label("Tamanho de janela (Altura): ");
        TextField inputFieldHeight = new TextField();
        Label instructionLabelWidth = new Label("Tamanho de janela (Largura): ");
        TextField inputFieldWidth = new TextField();
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button submitButton = new Button("Confirmar");
        submitButton.setOnAction(event -> {
            String input1 = inputFieldTimeUnit.getText();
            String input2 = inputFieldHeight.getText();
            String input3 = inputFieldWidth.getText();
            if (isNumeric(input1) && isNumeric(input2) && isNumeric(input3)) {
                int userInput1 = Integer.parseInt(input1);
                int userInput2 = Integer.parseInt(input2);
                int userInput3 = Integer.parseInt(input3);
                System.out.println("Unidade de tempo de cada movimentação: " + userInput1);
                System.out.println("Tamanho de janela (Altura): " + userInput2);
                System.out.println("Tamanho de janela (Largura): " + userInput3);
                window.close();
                //TODO converter para multiplos de 20
                int height = (userInput2 / 20) * 20;
                int width = (userInput3 / 20) * 20;
                System.out.println("Altura" + height);
                System.out.println("Largura" + width);
                //mudar aqui valores
//                ecossistemaManager.setMapWidth(width);
//                ecossistemaManager.setMapHeight(height-25);
//                Platform.runLater(() -> {
//                    int height = (userInput2 / 20) * 20;
//                    int width = (userInput3 / 20) * 20;
//                    System.out.println("Altura: " + height);
//                    System.out.println("Largura: " + width);
//                    ecossistemaManager.setMapWidth(width);
//                    ecossistemaManager.setMapHeight(height - 25);
//                    window.close();
//                });
            } else {
                errorLabel.setText("Ambos os campos devem ser preenchidos com NUMEROS INTEIROS");
            }
        });

        Button closeButton = new Button("Cancelar");
        closeButton.setOnAction(event -> window.close());

        layout.getChildren().addAll(instructionLabelTimeUnit, inputFieldTimeUnit, instructionLabelHeight, inputFieldHeight, instructionLabelWidth, inputFieldWidth, submitButton, errorLabel, closeButton);

        Scene scene = new Scene(layout, 400, 350);
        window.setTitle("Configurações Gerais do Ecossistema");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    private void openCustomWindowConfigSimulacao() {
        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label instructionLabelTimeUnit = new Label("Unidade de tempo de cada movimentação: ");
        TextField inputFieldTimeUnit = new TextField();
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button submitButton = new Button("Confirmar");
        submitButton.setOnAction(event -> {
            String input1 = inputFieldTimeUnit.getText();
            if (isNumeric(input1)) {
                int userInput1 = Integer.parseInt(input1);
                System.out.println("Unidade de tempo de cada movimentação: " + userInput1);
                //TODO Set na unidade de tempo no manager
            } else {
                errorLabel.setText("Ambos os campos devem ser preenchidos com NUMEROS INTEIROS");
            }
        });

        Button closeButton = new Button("Cancelar");
        closeButton.setOnAction(event -> window.close());

        layout.getChildren().addAll(instructionLabelTimeUnit, inputFieldTimeUnit, submitButton, errorLabel, closeButton);

        Scene scene = new Scene(layout, 400, 350);
        window.setTitle("Configurações Gerais do Ecossistema");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

}
