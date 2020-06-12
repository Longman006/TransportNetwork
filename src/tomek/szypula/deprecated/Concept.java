package tomek.szypula.deprecated;/**
 * Created by longman on 03.11.18.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Concept extends Application {

    private static final double desiredSpeed = 20;
    private static final double timeGap = 1;
    private static final double minimumGap = 2;
    private static final double accelerationExponent = 4;
    private static final double acceleration = 1;
    private static final double comfortableDecceleration = 1.5;
    private double numberOfCars = 20 ;
    private int screenWidth = 1000 ;
    private int screenHeight = 400 ;
    private List<Car> carsList  = new ArrayList<>();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        createCars();
        Group group = new Group();
        for (Car car : carsList) {
            group.getChildren().add(car.getCircle());

        }
        final Scene scene = new Scene(group, screenWidth, screenHeight, Color.CORNSILK);
        primaryStage.setScene(scene);
        primaryStage.show();
        model();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case BACK_SPACE: carsList.get(0).setSpeed(0);
                case SPACE:             model();break;
                case ENTER: carsList.get(0).setSpeed(1);break;
            }

        } );

    }

    private void model() {
        for (int k = 0 ; k<1 ; k++) {
            double Vn, VnNext;
            double VnNew;
            double distance, x1, x2, desiredDistance;
            Car car, carNext;
            for (int i = 0; i < carsList.size(); i++) {
                car = carsList.get(i);
                carNext = carsList.get((i + 1) % carsList.size());
                VnNext = carNext.getSpeed();
                Vn = car.getSpeed();
                x2 = carNext.getCircle().getCenterX()-carNext.getCircle().getRadius();
                x1 = car.getCircle().getCenterX()+car.getCircle().getRadius();
                if (x2 > x1)
                    distance = x2 - x1;
                else
                    distance = screenWidth - x1 + x2 ;
                desiredDistance = minimumGap + Math.max(0, Vn * timeGap + Vn * (Math.abs(VnNext - Vn)) / 2 / Math.sqrt(acceleration * comfortableDecceleration));
                VnNew = Math.abs(car.getSpeed()+acceleration * (1 - Math.pow(Vn / desiredSpeed, accelerationExponent) - Math.pow(desiredDistance / distance, 2)));
                car.setSpeed(VnNew);
                car.translateCar();
            }
            for (Car carcar : carsList) {
                carcar.play();
            }
        }
    }

    private void createCars() {
        int deltaX = (int) (screenWidth / numberOfCars);
        int radius = 10;
        for( int i = 0 ; i < numberOfCars ; i++ ){
            carsList.add(new Car(radius+i*deltaX,screenHeight/2,radius, screenWidth));
        }

    }


}
