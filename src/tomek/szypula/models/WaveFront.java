package tomek.szypula.models;

import javafx.scene.text.Text;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;

public class WaveFront {
    Car car;
    Road currentRoad;
    Vector2D position;
    Vector2D previousPosition;
    Vector2D speed;
    int vicinity = 8;

    public WaveFront(Car car, Road road) {
        this.car = car;
        position = car.getPosition();
        previousPosition = car.getPosition();
        speed = new Vector2D();
        currentRoad = road;
    }

    public void setCar(Car car,Road road) {
        previousPosition.setVector(position);
        position.setVector(car.getPosition());
        speed.setVector(Vector2DMath.vector2DSubtract(position,previousPosition));
        this.car = car;
        currentRoad = road;
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

    public Car getCar() {
        return car;
    }

    public Road getCurrentRoad() {
        return currentRoad;
    }
}
