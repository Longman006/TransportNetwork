package tomek.szypula.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import tomek.szypula.math.Vector2D;
import tomek.szypula.models.Car;

import java.util.concurrent.Callable;

public class CarUI implements CreateUI{
    private Car car;
    private Circle carShape;

    public CarUI(Car car) {
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
        carShape.setFill(Color.GREEN);
    }

    public Car getCar(){return car;}

    public Circle getCarShape(){return carShape;}

    @Override
    public Paint getfill() {
        return carShape.getFill();
    }

    @Override
    public ObjectProperty<Paint> getfillProperty() {
        return carShape.fillProperty();
    }

    @Override
    public void createUI(Group parent) {
        parent.getChildren().add(carShape);
    }
}
