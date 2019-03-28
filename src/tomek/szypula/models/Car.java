package tomek.szypula.models;

import javafx.beans.property.DoubleProperty;
import tomek.szypula.math.Vector2D;

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

    public void setPosition(Vector2D vector2D) { this.position.setVector(vector2D); }

    public Vector2D getSpeed() {
        return speed;
    }

    public void setSpeed(Vector2D speed) {  this.speed.setVector(speed); }

    public double getSize() {
        return parameters.getSize();
    }

    public DoubleProperty getXProperty() {return position.getXProperty(); }

    public CarParameters getParameters() {  return parameters; }

    public Driver getDriver() {return driver; }
}
