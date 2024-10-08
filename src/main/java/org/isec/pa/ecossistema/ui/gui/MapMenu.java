package org.isec.pa.ecossistema.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.isec.pa.ecossistema.model.EcossistemaManager;
import org.isec.pa.ecossistema.model.data.ElementoBase;
import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.data.Flora;
import org.isec.pa.ecossistema.model.data.Inanimado;
import org.isec.pa.ecossistema.utils.ElementoEnum;

import java.io.File;
import java.io.IOException;

public class MapMenu extends MenuBar {
    public static final String PROP_SWITCH_REDO = "_switchRedo_";
    public static final String PROP_SWITCH_UNDO = "_switchUndo_";
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
    private ElementoBase elementoBase;

    private double forcaEdit;
    private int velocidadeEdit;

    public MapMenu(EcossistemaManager ecossistemaManager, MapArea mapArea) {
        this.ecossistemaManager = ecossistemaManager;
        this.mapArea = mapArea;
        this.createViews();
        this.registerHandlers();
        this.update();
//        this.mnUndo.setDisable(true);
//        this.mnRedo.setDisable(true);
    }

    private void openSimulation() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir ficheiro...");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Guardar (.dat)", "*.dat"),
                new FileChooser.ExtensionFilter("Todos", "*.*"));
        File file = fileChooser.showOpenDialog(this.getScene().getWindow());
        if (file != null) {
            ecossistemaManager.openBinFile(file);
        }
    }

    private void saveSimulation() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar ficheiro...");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Guardar (.dat)", ".dat"),
                new FileChooser.ExtensionFilter("Todos", "."));
        File file = fileChooser.showSaveDialog(this.getScene().getWindow());
        if (file != null) {
            try {
                ecossistemaManager.saveToBinFile(file);
                //ecossistemaManager.resetSaveChanges();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        //this.mnGravarSnapshot = new MenuItem("_Gravar Snapshot");
        //this.mnRestaurarSnapshot = new MenuItem("_Restaurar Snapshot");
        this.mnAplicarSol = new MenuItem("_Aplicar Sol");
        this.mnAplicarHerbicida = new MenuItem("_Aplicar Herbicida");
        this.mnInjetarForca = new MenuItem("_Injetar Forca");


        //this.mnOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, new KeyCombination.Modifier[]{KeyCodeCombination.ALT_DOWN}));
        this.mnFicheiro.getItems().addAll(new MenuItem[]{this.mnCriar, this.mnAbrir, this.mnGravar, new SeparatorMenuItem(), this.mnExportar, this.mnImportar, this.mnSair});
        this.mnEcossistema.getItems().addAll(new MenuItem[]{this.mnConfigGeraisEcossistema, this.mnAdicionarElementoInanimado, this.mnAdicionarElementoFlora, this.mnAdicionarElementoFauna, this.mnEditarElemento, this.mnEliminarElemento, this.mnUndo, this.mnRedo});
        this.mnSimulacao.getItems().addAll(new MenuItem[]{this.mnConfigSimulacao, this.mnExecutar, this.mnParar, this.mnPausar, this.mnContinuar});
        this.mnEventos.getItems().addAll(new MenuItem[]{this.mnAplicarSol, this.mnAplicarHerbicida, this.mnInjetarForca});
        this.getMenus().addAll(new Menu[]{this.mnFicheiro, this.mnEcossistema, this.mnSimulacao, this.mnEventos});
    }

    private void registerHandlers() {
        this.mnAdicionarElementoInanimado.setOnAction((event) -> {
            activateCommands(1);
        });
        this.mnAdicionarElementoFlora.setOnAction((event) -> {
            activateCommands(2);
        });
        this.mnAdicionarElementoFauna.setOnAction((event) -> {
            activateCommands(3);
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
        this.mnSair.setOnAction((event) -> {
            openExitConfirmationWindow();
        });
        this.mnUndo.setOnAction((event) -> {
            activateCommands(6);
        });
        this.mnRedo.setOnAction((event) -> {
            activateCommands(7);
        });
        this.mnGravar.setOnAction((event) -> {
            saveSimulation();
        });
        this.mnAbrir.setOnAction((event) -> {
            openSimulation();

        });
        this.mnImportar.setOnAction((event) -> {
            ecossistemaManager.getEcossistema().load();
            System.out.println("Ecossistema Carregado");
        });
        this.mnExportar.setOnAction((event) -> {
            try {
                ecossistemaManager.getEcossistema().save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Ecossistema Gravado");
        });
        this.mnAplicarHerbicida.setOnAction((event) -> {
            openHerbicidaWindow();
        });
        this.mnInjetarForca.setOnAction((event) -> {
            openInjectFaunalWindow();
        });
        this.mnAplicarSol.setOnAction((event) -> {
            ecossistemaManager.aplicarSolEvent();
        });
        this.mnPausar.setOnAction((event) -> {
            ecossistemaManager.pauseGameEngine();
        });
        this.mnContinuar.setOnAction((event) -> {
            ecossistemaManager.resumeGameEngine();
        });
        this.mnParar.setOnAction((event) -> {
            ecossistemaManager.stopGameEngine();
        });
        this.mnExecutar.setOnAction((event) -> {
            ecossistemaManager.startGameEngine();
        });
    }

    private void update() {

    }

    private void openExitConfirmationWindow() {
        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label instructionLabel = new Label("Deseja guardar a simulação?");
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        HBox buttonLayout = new HBox(10); // Create an HBox to align buttons horizontally
        buttonLayout.setAlignment(Pos.CENTER); // Center align the buttons

        Button confirmButton = new Button("Gravar");
        confirmButton.setOnAction(event -> {
            try {
                ecossistemaManager.saveToBinFile(new File("ecossistema.dat"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            window.close();
            Platform.exit();
        });

        Button cancelButton = new Button("Sair");
        cancelButton.setOnAction(event -> {
            window.close();
            Platform.exit();
        });

        buttonLayout.getChildren().addAll(confirmButton, cancelButton); // Add buttons to HBox

        layout.getChildren().addAll(instructionLabel, buttonLayout, errorLabel);

        Scene scene = new Scene(layout, 250, 120); // Adjusted window size
        window.setTitle("Confirmação de Saída");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    private void openHerbicidaWindow() {
        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label instructionLabelId = new Label("Digite o ID da Flora que deseja eliminar: ");
        TextField inputFieldId = new TextField();
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);

        Button submitButton = new Button("Confirmar");
        submitButton.setOnAction(event -> {
            String input = inputFieldId.getText();
            if (isNumeric(input)) {
                int id = Integer.parseInt(input);
                Flora floraAux = (Flora) ecossistemaManager.getElementoByIdAndType(id, ElementoEnum.FLORA);
                if (floraAux == null) {
                    errorLabel.setText("O ID inserido não existe");
                } else {
                    window.close();
                    ecossistemaManager.removeElementoCommand(ElementoEnum.FLORA, id);
                }
            } else {
                errorLabel.setText("Digite um ID válido.");
            }
        });

        Button closeButton = new Button("Cancelar");
        closeButton.setOnAction(event -> window.close());

        buttonLayout.getChildren().addAll(submitButton, closeButton);

        layout.getChildren().addAll(instructionLabelId, inputFieldId, errorLabel, buttonLayout);

        Scene scene = new Scene(layout, 300, 150);
        window.setTitle("Digite um ID");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }


    private void openInjectFaunalWindow() {
        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label instructionLabelId = new Label("Digite o ID da Fauna que deseja injetar: ");
        TextField inputFieldId = new TextField();
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);

        Button submitButton = new Button("Confirmar");
        submitButton.setOnAction(event -> {
            String input = inputFieldId.getText();
            if (isNumeric(input)) {
                int id = Integer.parseInt(input);
                Fauna faunaAux = (Fauna) ecossistemaManager.getElementoByIdAndType(id, ElementoEnum.FAUNA);
                if (faunaAux == null) {
                    errorLabel.setText("O ID inserido não existe");
                } else {
                    window.close();
                    faunaAux.setForca(faunaAux.getForca() + 50);
                    if (faunaAux.getForca() > 100) {
                        faunaAux.setForca(100);
                    }
                }
            } else {
                errorLabel.setText("Digite um ID válido.");
            }
        });

        Button closeButton = new Button("Cancelar");
        closeButton.setOnAction(event -> window.close());

        buttonLayout.getChildren().addAll(submitButton, closeButton);

        layout.getChildren().addAll(instructionLabelId, inputFieldId, errorLabel, buttonLayout);

        Scene scene = new Scene(layout, 300, 150);
        window.setTitle("Digite um ID");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }


    private void openCustomWindowConfigGeraisEcossistema() {
        Stage window = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label instructionLabelForca = new Label("Força Inicial (Fauna e Flora): ");
        TextField inputFieldForca = new TextField();
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);

        Button submitButton = new Button("Confirmar");
        submitButton.setOnAction(event -> {
            String input1 = inputFieldForca.getText();
            if (isNumeric(input1)) {
                float userInput1 = (float) Integer.parseInt(input1);
                ecossistemaManager.setForcaDefault(userInput1);
                window.close();
            } else {
                errorLabel.setText("Insira um valor numérico inteiro");
            }
        });

        Button closeButton = new Button("Cancelar");
        closeButton.setOnAction(event -> window.close());

        buttonLayout.getChildren().addAll(submitButton, closeButton);

        layout.getChildren().addAll(instructionLabelForca, inputFieldForca, errorLabel, buttonLayout);

        Scene scene = new Scene(layout, 300, 150);
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

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);

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
                    Flora floraAux = (Flora) ecossistemaManager.getElementoByIdAndType(userInput1, ElementoEnum.FLORA);
                    elementoBase = (ElementoBase) floraAux;
                    forcaEdit = userInput3;
                    activateCommands(4);
                } else if (input2.equalsIgnoreCase("FAUNA")) {
                    Fauna faunaAux = (Fauna) ecossistemaManager.getElementoByIdAndType(userInput1, ElementoEnum.FAUNA);
                    elementoBase = (ElementoBase) faunaAux;
                    forcaEdit = userInput3;
                    velocidadeEdit = userInput4;
                    activateCommands(4);
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

        buttonLayout.getChildren().addAll(submitButton, closeButton);

        layout.getChildren().addAll(instructionLabelId, inputFieldId, instructionLabelTipo, inputFieldTipo, instructionLabelForca, inputFieldForca, instructionLabelVel, inputFieldVel, errorLabel, buttonLayout);

        Scene scene = new Scene(layout, 300, 300);
        window.setTitle("Editar Elemento");
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

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);

        Button submitButton = new Button("Confirmar");
        submitButton.setOnAction(event -> {
            String input1 = inputFieldId.getText();
            String input2 = inputFieldType.getText();
            if (isNumeric(input1)) {
                int userInput1 = Integer.parseInt(input1);
                if (input2.equalsIgnoreCase("INANIMADO")) {
                    Inanimado inanimadoAux = (Inanimado) ecossistemaManager.getElementoByIdAndType(userInput1, ElementoEnum.INANIMADO);
                    if (inanimadoAux == null) {
                        errorLabel.setText("O id inserido não existe");
                        return;
                    }
                    if (inanimadoAux.isBarreira()) {
                        errorLabel.setText("O id inserido faz parte da BARREIRA");
                        return;
                    }
                    elementoBase = (ElementoBase) inanimadoAux;
                    activateCommands(5);
                } else if (input2.equalsIgnoreCase("FLORA")) {
                    Flora floraAux = (Flora) ecossistemaManager.getElementoByIdAndType(userInput1, ElementoEnum.FLORA);
                    if (floraAux == null) {
                        errorLabel.setText("O id inserido não existe");
                        return;
                    }
                    elementoBase = (ElementoBase) floraAux;
                    activateCommands(5);
                } else if (input2.equalsIgnoreCase("FAUNA")) {
                    Fauna faunaAux = (Fauna) ecossistemaManager.getElementoByIdAndType(userInput1, ElementoEnum.FAUNA);
                    if (faunaAux == null) {
                        errorLabel.setText("O id inserido não existe");
                        return;
                    }
                    elementoBase = (ElementoBase) faunaAux;
                    activateCommands(5);
                } else {
                    errorLabel.setText("Tipo de elemento deve ser um dos 3: INANIMADO, FLORA ou FAUNA");
                }

                System.out.println("ID eliminado: " + userInput1);
                window.close();
            } else {
                errorLabel.setText("Campo deve ser preenchido com NUMEROS INTEIROS");
            }
        });

        Button closeButton = new Button("Cancelar");
        closeButton.setOnAction(event -> window.close());

        buttonLayout.getChildren().addAll(submitButton, closeButton);

        layout.getChildren().addAll(instructionLabelId, inputFieldId, instructionLabelType, inputFieldType, errorLabel, buttonLayout);

        Scene scene = new Scene(layout, 300, 200);
        window.setTitle("Eliminar Elemento");
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
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);

        Button submitButton = new Button("Confirmar");
        submitButton.setOnAction(event -> {
            String input1 = inputFieldTimeUnit.getText();
            if (isNumeric(input1)) {
                int userInput1 = Integer.parseInt(input1);
                if (userInput1 < 500) {
                    errorLabel.setText("A unidade de tempo tem que ser pelo menos 500 milisegundos");
                    return;
                }
                System.out.println("Unidade de tempo de cada movimentação: " + userInput1);
                window.close();
                System.out.println("Unidade de tempo de cada movimentação: " + userInput1);
                this.ecossistemaManager.changeTickSpeed(userInput1);
            } else {
                errorLabel.setText("Ambos os campos devem ser preenchidos com NUMEROS INTEIROS");
            }
        });

        Button closeButton = new Button("Cancelar");
        closeButton.setOnAction(event -> window.close());

        buttonLayout.getChildren().addAll(submitButton, closeButton);

        layout.getChildren().addAll(instructionLabelTimeUnit, inputFieldTimeUnit, errorLabel, buttonLayout);

        Scene scene = new Scene(layout, 300, 150);
        window.setTitle("Configurações de Início da Simulação");
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

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);

        Button submitButton = new Button("Confirmar");
        submitButton.setOnAction(event -> {
            String input1 = inputFieldTimeUnit.getText();
            if (isNumeric(input1)) {
                int userInput1 = Integer.parseInt(input1);
                System.out.println("Unidade de tempo de cada movimentação: " + userInput1);
                ecossistemaManager.changeTickSpeed(userInput1);
            } else {
                errorLabel.setText("O campo deve ser preenchido com um NÚMERO INTEIRO");
            }
        });

        Button closeButton = new Button("Cancelar");
        closeButton.setOnAction(event -> window.close());

        buttonLayout.getChildren().addAll(submitButton, closeButton);

        layout.getChildren().addAll(instructionLabelTimeUnit, inputFieldTimeUnit, errorLabel, buttonLayout);

        Scene scene = new Scene(layout, 300, 150);
        window.setTitle("Configurações de Simulação");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }


    private void activateCommands(int op) {
        switch (op) {
            case 1 -> ecossistemaManager.addElementCommand(ElementoEnum.INANIMADO);
            case 2 -> ecossistemaManager.addElementCommand(ElementoEnum.FLORA);
            case 3 -> ecossistemaManager.addElementCommand(ElementoEnum.FAUNA);
            case 4 ->
                    ecossistemaManager.editarElementoCommand(elementoBase.getElemento(), elementoBase.getId(), forcaEdit, velocidadeEdit);
            case 5 -> ecossistemaManager.removeElementoCommand(elementoBase.getElemento(), elementoBase.getId());
            case 6 -> ecossistemaManager.undo();
            case 7 -> ecossistemaManager.redo();
            default -> System.out.println("Erro");
        }
    }
}
