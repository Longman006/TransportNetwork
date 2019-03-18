package tomek.szypula.models;

import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;

public class Car {
    Vector2D position;
    Vector2D speed;
    CarParameters parameters;

    public Car(Vector2D position, CarParameters parameters) {
        this.position = position;
        this.parameters = parameters;
        this.speed = new Vector2D();
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
    }
}
