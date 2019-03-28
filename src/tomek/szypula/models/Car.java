package tomek.szypula.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;

public class Car {
    Vector2D position;
    Vector2D speed;
    CarParameters parameters;
    Driver driver;

    public Car(Vector2D position, CarParameters parameters, Driver driver) {
        this.position = position;
        this.parameters = parameters;
        this.speed = new Vector2D();
        this.driver = driver;
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Vector2D getSpeed() {
        return speed;
    }

    public void setSpeed(Vector2D speed) {
        this.speed = speed;
    }

    public double getSize() {
        return parameters.getSize();
    }

    public DoubleProperty getXProperty() {return position.getXProperty(); }

    public CarParameters getParameters() {  return parameters; }

    public Driver getDriver() {return driver; }
}
