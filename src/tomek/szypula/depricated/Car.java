package pl.edu.pw.tomasz.szypula;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
/**
 * Created by longman on 03.11.18.
 */
public class Car {
    private final Duration duration = javafx.util.Duration.millis(200);
    private final double maxSpeed = 38;
   Circle circle;
   TranslateTransition translateTransition = new TranslateTransition(duration,circle);
   int width = 0;
   double speed = 10 ;

    public Car(int centerX, int centerY, int radius, int width){
        this.width = width;
        this.circle = new Circle(centerX, centerY, radius, Color.BLUEVIOLET);
        this.circle.setOpacity(0.7);
        translateTransition = new TranslateTransition(Duration.millis(100),circle);
        translateTransition.setOnFinished(event -> {
            circle.setCenterX((circle.getTranslateX() + circle.getCenterX())%width);
            circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
            circle.setTranslateX(0);
            circle.setTranslateY(0);
            translateTransition.setByX(0);
        });
        translateTransition.setAutoReverse(false);
    }

    public Circle getCircle() {
        return this.circle;
    }
    public void translateCar(){
        circle.setFill(new Color(speed/maxSpeed,0,0,1- speed/maxSpeed));
        translateTransition.setByX(speed);
    }
    public void play(){
        translateTransition.play();
    }


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "x : "+(int)circle.getCenterX()+" speed : "+(int)speed+"\n";
    }
}