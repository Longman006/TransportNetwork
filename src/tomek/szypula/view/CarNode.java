package tomek.szypula.view;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tomek.szypula.models.Car;

public class CarNode {
    Car car;
    Circle circle;

    public CarNode(Car car) {
        this.car = car;

        circle.setCenterX(car.getX());
        circle.setCenterY(car.getY());
        circle.setRadius(car.getSize());
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        circle.setStroke(Color.BLACK);
        circle.setEffect(dropShadow);
        circle.setFill(Color.RED);
    }
}
