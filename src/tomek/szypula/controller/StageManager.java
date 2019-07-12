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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;
import tomek.szypula.view.View;

import java.util.List;

public class StageManager {

    Stage window;
    Model model;
    View view;

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
    Scene mainScene = new Scene(mainSceneLayout,800,600);
    Scene mainMenuScene  = new Scene(mainMenuLayout,400,300);
    Scene editorScene = new Scene(editorLayout,800,600);

    //Animation
    Timeline timeline = null;

    public StageManager(Stage window,Model model) {
        this.window=window;
        this.model = model;
        editor = new Editor(model.getRoadList(),editorPane);

        setupEditor();
        setupModelScene();
        setupMainMenu();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu helpMenu = new Menu("Help");
        helpMenu.getItems().add(new MenuItem("Model description"));
        Menu openMenu = new Menu("Open");
        MenuItem editorItem = new MenuItem("Editor");
        MenuItem menuItem = new MenuItem("Main Menu");
        MenuItem simulationItem = new MenuItem("Simulation");
        openMenu.getItems().addAll(menuItem,editorItem,simulationItem);

        simulationItem.setOnAction(actionEvent -> loadSimulation());
        editorItem.setOnAction(actionEvent -> loadEditor());
        menuItem.setOnAction(actionEvent -> loadMainMenu());

        menuBar.getMenus().add(helpMenu);
        menuBar.getMenus().add(openMenu);

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
        new DefaultNetworks().loadDefaultNetwork(model.getRoadList());
        for (Road road : model.getRoadList()) {
            System.out.println(road);
        }
    }

    private void setupModelScene() {
        //Main Model Scene setup
        mainSceneLayout.setCenter(modelControlsSplitPane);
        mainSceneLayout.setTop(createMenuBar());
        modelControlsSplitPane.prefWidthProperty().bind(mainSceneLayout.widthProperty());
        modelControlsSplitPane.prefHeightProperty().bind(mainSceneLayout.heightProperty());
    }


    public void loadSimulation() {
        if (view == null){
            view = new View(model,modelControlsSplitPane);
        }
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
    }

    public void loadMainMenu(){
        window.setScene(mainMenuScene);
    }
    public void loadEditor(){
        window.setScene(editorScene);
    }
    public void step(){

        model.updateSpeed();
//        try {
//            dataManagementSystem.updateFile("TestFile.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
