package tomek.szypula.models;

import javafx.beans.property.DoubleProperty;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Road> roadList = new ArrayList<>();

    public Model(List<Road> roadList) {
        this.roadList = roadList;
    }

    public Car getNextCar(Car car, Road road){
        if (road.hasNextCar(car))
            return road.getNextCar(car);
        else
            return getNextCar(car,car.getDriver().nextRoad(road));
    }
    private double calculateNewSpeed(Car car, Road road){
        CarParameters carParameters = car.getParameters();
        Car carNext = getNextCar(car,road);


        Vector2D carNextSpeed = carNext.getSpeed();
        Vector2D carSpeed = car.getSpeed();
        double carSpeedValue = carSpeed.getLength();
        double carSpeedNextValue = carNextSpeed.getLength();

        /**
         * Temporary solution for calculating distance
         * for now it will be just the distance in a straight line
         * later i hope on using Route class
         */
/*
        double distance = Math.sqrt(Vector2DMath.distanceSquared(Vector2DMath.vector2DSum(
                carNext.getPosition(),
                new Vector2D(carNextSpeed).normalize().multiply(-1.0*Math.sqrt(carNext.getSize()))
        ),Vector2DMath.vector2DSum(
                car.getPosition(),
                new Vector2D(carSpeed).normalize().multiply(1.0*Math.sqrt(carNext.getSize()))
        )));
 */
        double distance = Math.sqrt(Vector2DMath.distanceSquared(carNext.getPosition(),car.getPosition()));
        double desiredDistance = carParameters.getMinimumGap() + Math.max(0, carSpeedValue * carParameters.getTimeGap() + carSpeedValue * (Math.abs(carSpeedNextValue - carSpeedValue)) / 2 / Math.sqrt(carParameters.getAcceleration() * carParameters.getDeceleration()));
        double carSpeedNewValue = Math.abs(carSpeedValue+carParameters.getAcceleration() * (1 - Math.pow(carSpeedValue / carParameters.getDesiredSpeed(), carParameters.getAccelerationExponent()) - Math.pow(desiredDistance / distance, 2)));
        return carSpeedNewValue;


    }

    public void updateSpeed(){
        for (Road road : roadList){
            for (int i = 0 ; i< road.getCarList().size(); i++){
                Car car = road.getCarList().get(i);
                double carSpeedNew = calculateNewSpeed(car,road);
                car.setSpeed(new Vector2D(road.getLineSegment().getDirection()).normalize().multiply(Math.sqrt(carSpeedNew)));
                updatePosition(car,road);

            }
        }
    }
    private void updatePosition(Car car, Road road){
        Vector2D carSpeed = car.getSpeed();
        double carSpeedValue = carSpeed.getLength();
        double distanceToEnd = road.getDistanceToEnd(car);
        double distanceOnNewRoad = carSpeedValue - distanceToEnd;

        if ( distanceOnNewRoad <= 0 ){
            car.setPosition(car.getPosition().addVector2D(carSpeed));
        }
        else {
            Road nextRoad = car.getDriver().nextRoad(road);


            //direction.multiply(Math.sqrt(distanceOnNewRoad));
           // car.setPosition(Vector2DMath.vector2DSum(direction,road.getStart()));


            if (nextRoad.insertCar(car)){
                road.removeCar(car);
                Vector2D direction = new Vector2D(nextRoad.getLineSegment().getDirection()).normalize();
                car.setSpeed(new Vector2D(direction).multiply(Math.sqrt(carSpeedValue)));
            }
            else
                car.setPosition(road.getEnd());
                car.setSpeed(new Vector2D());
        }





    }
}
