package tomek.szypula.models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.text.Text;
import tomek.szypula.controller.Highlightable;
import tomek.szypula.controller.Highlighted;
import tomek.szypula.controller.UniqueId;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;

import java.util.UUID;

public class WaveFront implements UniqueId, Highlightable {
    Car car;
    Road currentRoad;
    Vector2D position;
    Vector2D previousPosition;
    Vector2D speed;
    Vector2D direction;
    int vicinity = 8;
    UUID uuid;

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

    public void setCar(Car car,Road road) {
        previousPosition.setVector(position);
        position.setVector(car.getPosition());
        speed.setVector(Vector2DMath.vector2DSubtract(position,previousPosition));
        this.car = car;
        currentRoad = road;
        direction.setVector(currentRoad.getDirection());
    }

    public boolean inVicinity(Car car) {
        if (Vector2DMath.vector2DSubtract(car.getPosition(),position).getLength() < car.getParameters().getSize()*vicinity) {
            return true;
        }
        return false;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getSpeed() {
        return speed;
    }

    public Vector2D getDirection() {
        return direction;
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
