package tomek.szypula.models;

import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.controller.Highlightable;
import tomek.szypula.controller.Highlighted;
import tomek.szypula.controller.UniqueId;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WaveFront implements UniqueId, Highlightable {
    Car car;
    Road currentRoad;
    Vector2D position;
    Vector2D previousPosition;
    Vector2D speed;
    List<Vector2D> lastSpeeds = new ArrayList<>();
    Vector2D direction;
    UUID uuid;
    private int meanCount = 8;

    public WaveFront(Car car, Road road) {
        this.car = car;
        position = car.getPosition();
        previousPosition = car.getPosition();
        speed = new Vector2D();
        direction = new Vector2D();
        currentRoad = road;
        direction.setVector(road.getDirection());
        uuid = UUID.randomUUID();
    }

    public void setCar(Car car, Road road) {
        previousPosition.setVector(position);
        position.setVector(car.getPosition());
        setSpeed(Vector2DMath.vector2DSubtract(position,previousPosition));
        //speed.setVector();
        this.car = car;
        currentRoad = road;
        direction.setVector(currentRoad.getDirection());
    }

    private void setSpeed(Vector2D vector2DSubtract) {
        lastSpeeds.add(vector2DSubtract);
        Vector2D meanSpeed = Vector2DMath.meanVector(lastSpeeds);

        while (lastSpeeds.size() > meanCount){
            lastSpeeds.remove(0);
        }
        speed.setVector(meanSpeed);
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getSpeed() {
        return speed;
    }

    public Vector2D getDirectionCopy() {
        return new Vector2D(direction);
    }

    public Car getCar() {
        return car;
    }

    public Road getCurrentRoad() {
        return currentRoad;
    }

    @Override
    public String getId() {
        return uuid.toString();
    }

    @Override
    public void highlight() {
        Highlighted.setHighlightedWaveFront(this);
    }

    @Override
    public SimpleBooleanProperty isChangeProperty() {
        return Highlighted.isChangeWaveFrontProperty();
    }

    @Override
    public Object getHighlighted() {
        return Highlighted.getHighlightedWaveFront();
    }


}
