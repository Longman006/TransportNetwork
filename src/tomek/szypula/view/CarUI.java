package tomek.szypula.view;

import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tomek.szypula.models.Car;

public class CarUI implements CreateUI{
    Car car;
    Circle carShape;

    public CarUI(Car car,Color color) {
        this.car = car;
        carShape  = new Circle();
        carShape.setCenterX(car.getX());
        carShape.setCenterY(car.getY());
        carShape.setRadius(car.getSize());
        carShape.centerXProperty().bind(car.getXProperty());
        carShape.centerYProperty().bind(car.getYProperty());
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        carShape.setStroke(Color.BLACK);
        carShape.setEffect(dropShadow);
        carShape.setFill(color);
    }

    @Override
    public void createUI(Group parent) {
        parent.getChildren().add(carShape);
    }
}
