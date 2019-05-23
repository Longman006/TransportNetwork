package tomek.szypula.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import tomek.szypula.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Jam {
    private Road road;
    private Vector2D start = new Vector2D();
    private Vector2D end = new Vector2D();
    private Vector2D position = new Vector2D();
    private DoubleProperty mass = new SimpleDoubleProperty();

    public Jam(Road road) {
        this.road = road;
        position.xProperty().bind((start.xProperty().add(end.xProperty())).divide(2));
        position.yProperty().bind((start.yProperty().add(end.yProperty())).divide(2));
        updateJam();
    }

    public void updateJam(){
        List<Car> carList = new ArrayList<>();
        double tmpMass = 0;
        for (Car car : road.getCarList()){
            if (car.getSpeed().getLength() < car.getParameters().getDesiredSpeed()/10)
                carList.add(car);
        }
        for (int i = 0 ; i < carList.size(); i++){
            Car car = carList.get(i);
            tmpMass++;
            if (i == 0)
                start.setVector(car.getPosition());
            if (i == carList.size()-1)
                end.setVector(car.getPosition());
        }
        mass.set(tmpMass);
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    public Vector2D getPosition() {
        return position;
    }

    public double getMass() {
        return mass.get();
    }

    public DoubleProperty massProperty() {
        return mass;
    }

    public Road getRoad() {
        return road;
    }
}
