package tomek.szypula.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;
import tomek.szypula.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StageManager {

    Stage window;
    Model model;
    View view;
    DataManagementSystem dataManagementSystem;

    //Model properties
    List<Road> roads = new ArrayList<>();

    //Main Menu Scene
    Label mainMenuLabel = new Label("Transport Network Simulation");
    Button loadDefaultNetworkButton = new Button("Load Default");
    Button openNetworkEditorButton = new Button("Open Editor");
    Button runSimulationButton = new Button("Run Simulation");
    BorderPane mainMenuLayout = new BorderPane();
    VBox mainMenuVBox = new VBox(20);

    //Main Model Scene
    SplitPane modelControlsSplitPane = new SplitPane();
    BorderPane mainSceneLayout = new BorderPane();

    //Editor Scene
    BorderPane editorLayout = new BorderPane();
    Pane editorPane = new Pane();
    Editor editor ;

    //Scenes
    Scene mainScene = new Scene(mainSceneLayout, Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight());
    Scene mainMenuScene  = new Scene(mainMenuLayout,400,300);
    Scene editorScene = new Scene(editorLayout,Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight());

    //Animation
    Timeline timeline = null;

    public StageManager(Stage window) {
        this.window=window;
        setupEditor();
        setupModelScene();
        setupMainMenu();
        dataManagementSystem = new DataManagementSystem(model);
    }

    private MenuBar createMenuBar() {

        //Ribbon Commands
        MenuBar menuBar = new MenuBar();

        //Help
        Menu helpMenu = new Menu("Help");
        helpMenu.getItems().add(new MenuItem("Model description"));

        //File
        Menu fileMenu = new Menu("File");
        CheckMenuItem xyvItem = new CheckMenuItem("t x y v");
        //TODO Create general method for CheckMenuItems to save to file ( use the BoolProperty in DMS to trigger save)
        xyvItem.selectedProperty();
        fileMenu.getItems().addAll(xyvItem);

        //Open
        Menu openMenu = new Menu("Open");
        MenuItem editorItem = new MenuItem("Editor");
        MenuItem menuItem = new MenuItem("Main Menu");
        MenuItem simulationItem = new MenuItem("Simulation");
        openMenu.getItems().addAll(menuItem,editorItem,simulationItem);

        //Editor
        Menu editorMenu = new Menu("Editor");
        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        undoItem.setOnAction(actionEvent -> {
            if(editor != null && window.getScene().equals(editorScene))
                editor.undo();
                });
        MenuItem clearItem = new MenuItem("Clear");
        clearItem.setOnAction(actionEvent -> {
            if(editor != null && window.getScene().equals(editorScene)) {
                roads.removeAll(roads);
                if (editor != null)
                    editor.clear();
                if (view != null)
                    view.clear();
            }
        });
        editorMenu.getItems().add(clearItem);
        editorMenu.getItems().add(undoItem);

        //MenuItems actionEvents
        simulationItem.setOnAction(actionEvent -> loadSimulation());
        editorItem.setOnAction(actionEvent -> loadEditor());
        menuItem.setOnAction(actionEvent -> loadMainMenu());

        menuBar.getMenus().addAll(helpMenu,fileMenu,openMenu,editorMenu);

        return menuBar;
    }

    private void setupMainMenu() {
        //Main Menu setup
        mainMenuVBox.getChildren().addAll(loadDefaultNetworkButton,openNetworkEditorButton,runSimulationButton);
        mainMenuVBox.setAlignment(Pos.CENTER);
        mainMenuLayout.setCenter(mainMenuVBox);
        mainMenuLayout.setTop(mainMenuLabel);
        mainMenuLabel.setTextFill(Color.web("#0076a3"));
        mainMenuLabel.setFont(new Font(16));
        Image image = new Image("network.png",50, 50, false, false);
        mainMenuLabel.setGraphic(new ImageView(image));
        BorderPane.setAlignment(mainMenuLabel, Pos.CENTER);
        BorderPane.setMargin(mainMenuLabel, new Insets(12,12,12,12));
        BorderPane.setAlignment(mainMenuVBox, Pos.CENTER);
        BorderPane.setMargin(mainMenuVBox, new Insets(12,12,12,12));
        //Button setup
        loadDefaultNetworkButton.setOnAction(actionEvent -> loadDefaultNetwork());
        runSimulationButton.setOnAction(actionEvent -> loadSimulation());
        openNetworkEditorButton.setOnAction(actionEvent -> loadEditor());

    }
    private void setupEditor(){
        //Editor
        editorLayout.setTop(createMenuBar());
        editorLayout.setCenter(editorPane);
    }

    private void loadDefaultNetwork() {
        new DefaultNetworks().loadDefaultNetwork(roads);
    }

    private void setupModelScene() {
        //Main Model Scene setup
        modelControlsSplitPane = new SplitPane();
        mainSceneLayout.setCenter(modelControlsSplitPane);
        mainSceneLayout.setTop(createMenuBar());
        //modelControlsSplitPane.prefWidthProperty().bind(mainSceneLayout.widthProperty());
        //modelControlsSplitPane.prefHeightProperty().bind(mainSceneLayout.heightProperty());
    }


    public void loadSimulation() {
            setupModelScene();
        model = new Model(roads);
        view = new View(model,modelControlsSplitPane);
        dataManagementSystem.setModel(model);

        window.setScene(mainScene);
        if(timeline==null) {
            timeline = new Timeline(new KeyFrame(
                    Duration.millis(100),
                    ae -> {
                        step();
                    }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
        else
            timeline.play();
    }

    public void loadMainMenu(){
        window.setScene(mainMenuScene);
    }
    public void loadEditor(){
        editor = new Editor(roads,editorPane);
        window.setScene(editorScene);
        if (timeline!=null)
            timeline.pause();
    }
    public void step(){

        model.updateSpeed();
        try {
            dataManagementSystem.updateFilePositionSpeed();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
