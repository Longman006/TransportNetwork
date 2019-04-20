package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import tomek.szypula.controller.Controller;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Controller controller = new Controller(root);
        Scene theScene = new Scene( root,1100, 800 );
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(theScene);

        ArrayList<String> input = new ArrayList<String>();

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        System.out.println("input key code : "+code);

                        // only add once... prevent duplicates
                        if ( !input.contains(code) )
                            input.add( code );
                    }
                });

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        input.remove( code );
                    }
                });


        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(200),
                ae -> {
                    controller.step();
                    if (input.contains("LEFT")) {
                        controller.addCars();
                    }
                    else if (input.contains("RIGHT")) {
                        controller.stopCar();
                    }}));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
