package tomek.szypula.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import tomek.szypula.math.Vector2D;
import tomek.szypula.view.ColorValues;

public class Car {
    Vector2D position;
    Vector2D speed;
    CarParameters parameters;
    Driver driver;
    Vector2D direction;
    ColorValues colorValues = new ColorValues();

    public ColorValues getColorValues() {
        return colorValues;
    }
    public void setColorValues(int r,int g, int b){
        colorValues.setR(r);
        colorValues.setG(g);
        colorValues.setB(b);
    }

    public Car(Vector2D position, Vector2D direction, CarParameters parameters, Driver driver) {
        this.position = position;
        this.parameters = parameters;
        this.direction = direction;
        this.speed = new Vector2D(0,0);
        this.driver = driver;
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public Vector2D getPosition() {
        return new Vector2D(position);
    }

    public void setPosition(Vector2D vector2D) { this.position.setVector(vector2D); }

    public Vector2D getSpeed() {
        return new Vector2D(speed);
    }

    public Vector2D getDirection() {
        return new Vector2D(direction);
    }

    public void setSpeed(Vector2D speed) {
        this.speed.setVector(speed);
        if (!speed.isZero())
            direction.setVector(new Vector2D(speed).normalize());
    }
    public void setDirection(Vector2D direction){
        this.direction.setVector(new Vector2D(direction).normalize());
        this.speed.setVector(new Vector2D(this.direction).multiply(this.speed.getLength()));
    }

    public double getSize() {
        return parameters.getSize();
    }

    public DoubleProperty getXProperty() {return position.getXProperty(); }

    public CarParameters getParameters() {  return parameters; }

    public Driver getDriver() {return driver; }

    public DoubleProperty getYProperty() { return position.getYProperty();   }

    @Override
    public String toString() {
        return "Car{" +
                "position=" + position +
                '}';
    }


}
