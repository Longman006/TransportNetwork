package tomek.szypula.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;
import tomek.szypula.depricated.ColorValues;

public class Car {
    Vector2D position;
    Vector2D speed;
    CarParameters parameters;
    Driver driver;
    Vector2D direction;
    BooleanProperty highlighted = new SimpleBooleanProperty(false);

    public Car(Vector2D position, Vector2D direction, CarParameters parameters) {
        this.position = position;
        this.parameters = parameters;
        this.direction = direction;
        this.speed = new Vector2D(0,0);
    }

    public boolean isHighlighted() {
        return highlighted.get();
    }

    public BooleanProperty highlightedProperty() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted.set(highlighted);
    }

    public void setDriver(Driver driver) {
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

    public Vector2D getSpeedCopy() {
        return new Vector2D(speed);
    }

    public Vector2D getSpeed(){return speed;}

    public Vector2D getDirection() {
        return new Vector2D(direction);
    }

    public void setSpeed(Vector2D speed) {
        if (speed.getLength() > getParameters().getDesiredSpeed()*3){
            System.out.println("here");
            this.speed.setVector(speed);
            this.speed.normalize();
        }
        else
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

    public DoubleProperty getXProperty() {return position.xProperty(); }

    public CarParameters getParameters() {  return parameters; }

    public Driver getDriver() {return driver; }

    public DoubleProperty getYProperty() { return position.yProperty();   }

    @Override
    public String toString() {
        return "Car{" +
                "position=" + position +
                '}';
    }
    public double getDistanceToCar(Car car){
        double distance = Vector2DMath.distance(car.getPosition(),this.getPosition()) - car.getSize() - this.getSize();
        if (distance < 0){
            return 0;
        }
        return distance;
    }


    public void switchHighlighted() {
        setHighlighted(!isHighlighted());
    }

    public boolean hasSmallSpeed() {
        if (speed.getLength() < parameters.getDesiredSpeed()/4)
            return true;
        return false;
    }
}
