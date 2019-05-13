package tomek.szypula.models;

import tomek.szypula.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Road> roadList = new ArrayList<>();
    public Model(List<Road> roadList) {
        this.roadList = roadList;
    }


    private double calculateNewSpeed(Car car){
        CarParameters carParameters = car.getParameters();
        Route route = car.getDriver().getRoute();
        Car carNext = route.getNextCarOnRoute();
        car.setColorValues(0,255,0);
        carNext.setColorValues(0,255,0);
        Vector2D carNextSpeed = carNext.getSpeedCopy();

        Vector2D carSpeed = car.getSpeedCopy();
        double carSpeedValue = carSpeed.getLength();
        double carSpeedNextValue = carNextSpeed.getLength();

        double distance = route.calculateDistanceAlongRoute(carNext);

        double desiredDistance = carParameters.getMinimumGap() + Math.max(0, carSpeedValue * carParameters.getTimeGap() + carSpeedValue * (( carSpeedValue-carSpeedNextValue)) / 2 / Math.sqrt(carParameters.getAcceleration() * carParameters.getDeceleration()));
        double carSpeedNewValue = (carSpeedValue+carParameters.getAcceleration() * (1 - Math.pow(carSpeedValue / carParameters.getDesiredSpeed(), carParameters.getAcceleration()) - Math.pow(desiredDistance / distance, 2)));
        if(carSpeedNewValue<0){
            System.out.println("speed : "+carSpeedValue);
            System.out.println("distance : "+distance);
            System.out.println("desiredDistance : "+ desiredDistance);
            System.out.println("carSpeedNewValue : "+carSpeedNewValue);
                        carSpeedNewValue=0;



        }
        return carSpeedNewValue;

    }

    public void updateSpeed(){
        for (Road road : roadList) {
            for (int i = 0; i < road.getCarList().size(); i++) {
                Car car = road.getCarList().get(i);
                double carSpeedNew = calculateNewSpeed(car);
                car.setSpeed(road.getLineSegment().getDirection().multiply(carSpeedNew));
                updatePosition(car, road);
            }
        }
    }
    private void updatePosition(Car car, Road road){
        Vector2D carSpeed = car.getSpeedCopy();
        double carSpeedValue = carSpeed.getLength();
        double distanceToEnd = road.getDistanceToEnd(car);
        double distanceOnNewRoad = carSpeedValue - distanceToEnd ;

        if ( distanceOnNewRoad <= 0 ){
            car.setPosition(car.getPosition().addVector2D(carSpeed));
        }
        else {
            Road nextRoad = car.getDriver().getRoute().getNextRoad();
            if (nextRoad.insertCar(car)){
                road.removeCar(car);
                car.setPosition(car.getPosition().addVector2D(car.getDirection().multiply(distanceOnNewRoad)));
            }
            else {
                car.setSpeed(new Vector2D());
            }
        }





    }
}
