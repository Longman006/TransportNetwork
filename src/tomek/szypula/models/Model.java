package tomek.szypula.models;

import javafx.beans.property.DoubleProperty;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Road> roadList = new ArrayList<>();


    private void model() {
        double Vn, VnNext;
        double VnNew;
        double distance, x1, x2, desiredDistance;
        Car car, carNext;
        for (int i = 0; i < carsList.size(); i++) {
            car = carsList.get(i);
            carNext = carsList.get((i + 1) % carsList.size());
            VnNext = carNext.getSpeed();
            Vn = car.getSpeed();
            x2 = carNext.getCircle().getCenterX()-carNext.getCircle().getRadius();
            x1 = car.getCircle().getCenterX()+car.getCircle().getRadius();
            if (x2 > x1)
                distance = x2 - x1;
            else
                distance = screenWidth - x1 + x2 ;
            desiredDistance = minimumGap + Math.max(0, Vn * timeGap + Vn * (Math.abs(VnNext - Vn)) / 2 / Math.sqrt(acceleration * comfortableDecceleration));
            VnNew = Math.abs(car.getSpeed()+acceleration * (1 - Math.pow(Vn / desiredSpeed, accelerationExponent) - Math.pow(desiredDistance / distance, 2)));
            car.setSpeed(VnNew);
            car.translateCar();
        }
        for (Car carcar : carsList) {
            carcar.play();
        }

    }
    public Car getNextCar(Car car,Road road){
        if (road.hasNextCar(car))
            return road.getNextCar(car);
        else
            return getNextCar(car,car.getDriver().nextRoad(road));
    }

    public void UpdateSpeed(){
        for (Road road : roadList){
            for (Car car : road.getCarList()){
                CarParameters carParameters = car.getParameters();
                Car carNext = getNextCar(car,road);

                Vector2D carNextSpeed = carNext.getSpeed();
                Vector2D carSpeed = car.getSpeed();

                /**
                 * Temporary solution for calculating distance
                 * for now it will be just the distance in a straight line
                 * later i hope on using Route class
                 */

                double distance = Math.sqrt(Vector2DMath.distanceSquared(carNext.getPosition(),car.getPosition()));





            }
        }
    }
}
