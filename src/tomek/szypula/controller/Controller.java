package tomek.szypula.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import tomek.szypula.math.LineSegment;
import tomek.szypula.math.Vector2D;
import tomek.szypula.models.*;
import tomek.szypula.view.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    //Model properties
    List<Road> roads = new ArrayList<>();
    List<Car> carsList = new ArrayList<>();
    Model model;

    DataManagementSystem dataManagementSystem;

    //Windows
    Stage window;

    //Main Model Scene
    SplitPane modelControlsSplitPane = new SplitPane();
    BorderPane mainSceneLayout = new BorderPane();
    MenuBar menuBar = new MenuBar();
    Menu helpMenu = new Menu("Help");

    //Main Menu Scene
    Label mainMenuLabel = new Label("Transport Network Simulation");
    Button loadDefaultNetworkButton = new Button("Load Default");
    Button openNetworkEditorButton = new Button("Open Editor");
    Button runSimulationButton = new Button("Run Simulation");
    BorderPane mainMenuLayout = new BorderPane();
    VBox mainMenuVBox = new VBox(20);


    //Scenes
    Scene mainScene = new Scene(mainSceneLayout,800,600);
    Scene mainMenuScene  = new Scene(mainMenuLayout,400,300);

    //Animation
    Timeline timeline = null;

    private int x = 30;

    public Controller(Stage primaryStage){

        window = primaryStage;

        //Main Model Scene setup
        mainSceneLayout.setCenter(modelControlsSplitPane);
        helpMenu.getItems().add(new MenuItem("Model description"));
        menuBar.getMenus().add(helpMenu);
        mainSceneLayout.setTop(menuBar);

        modelControlsSplitPane.prefWidthProperty().bind(mainSceneLayout.widthProperty());
        modelControlsSplitPane.prefHeightProperty().bind(mainSceneLayout.heightProperty());

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
        loadDefaultNetworkButton.setOnAction(actionEvent -> loadDefaultNetwork());
        runSimulationButton.setOnAction(actionEvent -> openMainScene());
        openMainMenuScene();

        window.show();
    }

    private void openMainScene() {
        model = new Model(roads);
        View view = new View(model,modelControlsSplitPane);
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
    private void openMainMenuScene(){
        window.setScene(mainMenuScene);
    }


    private void loadDefaultNetwork() {
        createRoads();
        createRoadNetwork();
    }

    private void createRoads(){
        Vector2D a,b,c,d,e,f,g,h,i;

        a = new Vector2D(x,x);
        b = new Vector2D(x,7*x);
        c = new Vector2D(x,13*x);
        d = new Vector2D(6*x,x);
        e = new Vector2D(6*x,7*x);
        f = new Vector2D(6*x,13*x);
        g = new Vector2D(25*x,13*x);
        h = new Vector2D(25*x,7*x);
        i = new Vector2D(25*x,x);

        roads.add(new Road(new LineSegment(b,a)));
        roads.add(new Road(new LineSegment(b, c)));
        roads.add( new Road(new LineSegment(b,e)));
        roads.add(new Road(new LineSegment(a,d)));
        roads.add(new Road(new LineSegment(c,f)));
        roads.add(new Road(new LineSegment(f,e)));
        roads.add(new Road(new LineSegment(d,e)));
        roads.add(new Road(new LineSegment(g,f)));
        roads.add(new Road(new LineSegment(h,g)));
        roads.add(new Road(new LineSegment(h,i)));
        roads.add(new Road(new LineSegment(e,h)));
        roads.add(new Road(new LineSegment(i,d)));
    }
    private void printRoads(){
        for (Road road : roads  ) {
            System.out.println(road.toString());
        }
    }
    private void createRoadNetwork(){
        for (Road road : roads ) {
            for (Road roadNext : roads){
                if (road.getEnd().equalValue(roadNext.getStart()))
                    road.addRoad(roadNext);
                else if ( road.getStart().equalValue(roadNext.getEnd()))
                    road.addPreviousRoad(roadNext);
            }
        }
    }
    private void createCars(){
        for(int i = 0 ; i<roads.size()-8;i++){
            Car car = new Car(roads.get(i).getStart(),roads.get(i).getLineSegment().getDirection(),TrafficManagementSystem.getCarParameters());
            car.setDriver(new RandomDriver(new Vector2D(),roads.get(i),car));
            roads.get(i).insertCar(car);
            carsList.add(car);
        }
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
