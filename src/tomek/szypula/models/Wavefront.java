package tomek.szypula.models;

import javafx.scene.text.Text;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;

public class Wavefront {
    Car car;
    Vector2D position;
    Vector2D previousPosition;
    Vector2D speed;
    int vicinity = 8;

    public Wavefront(Car car) {
        this.car = car;
        position = car.getPosition();
        previousPosition = car.getPosition();
        speed = new Vector2D();
    }

    public void setCar(Car car) {
        previousPosition.setVector(position);
        position.setVector(car.getPosition());
        speed.setVector(Vector2DMath.vector2DSubtract(position,previousPosition));
        this.car = car;
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
}
