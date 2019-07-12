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
import javafx.scene.layout.Pane;
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
    Model model = new Model(roads);

    //Windows
    Stage window;
    StageManager stageManager;

    public Controller(Stage primaryStage){

        window = primaryStage;
        stageManager = new StageManager(window,model);
        stageManager.loadMainMenu();
        window.show();
    }
}
