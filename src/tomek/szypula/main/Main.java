package tomek.szypula.main;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tomek.szypula.controller.Controller;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        Controller controller = new Controller(primaryStage);
        primaryStage.setTitle("Transport Network");

//        ArrayList<String> input = new ArrayList<String>();
//
//        theScene.setOnKeyPressed(
//                new EventHandler<KeyEvent>()
//                {
//                    public void handle(KeyEvent e)
//                    {
//                        String code = e.getCode().toString();
//                        System.out.println("input key code : "+code);
//
//                        // only add once... prevent duplicates
//                        if ( !input.contains(code) )
//                            input.add( code );
//                    }
//                });
//
//        theScene.setOnKeyReleased(
//                new EventHandler<KeyEvent>()
//                {
//                    public void handle(KeyEvent e)
//                    {
//                        String code = e.getCode().toString();
//                        input.remove( code );
//                    }
//                });


//        Timeline timeline = new Timeline(new KeyFrame(
//                Duration.millis(80),
//                ae -> {
//                    controller.step();
//                    if (input.contains("LEFT")) {
//                        //controller.addCars();
//                    }
//                    else if (input.contains("RIGHT")) {
//                        System.out.println("Right");
//                    }}));
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();
    }


    public static void main(String[] args) {
        launch(args);
    }
}