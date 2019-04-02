package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tomek.szypula.controller.Controller;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Controller controller = new Controller(root);
        Scene theScene = new Scene( root );
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1100, 800));

        ArrayList<String> input = new ArrayList<String>();

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();

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

        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                // Here comes your void to refresh the whole application.
                controller.step();
                if (input.contains("LEFT"))
                    controller.addCars(3);

            }
        }, 200, 200);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
