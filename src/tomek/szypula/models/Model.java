package tomek.szypula.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import tomek.szypula.math.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {
    private List<Road> roadList ;
    private TrafficManagementSystem tms;
    private WaveFrontManager wfm;
    private DensityManager dm;
    private IntegerProperty time = new SimpleIntegerProperty();

    public Model(List<Road> roadList) {
        this.roadList = roadList;
        tms = new TrafficManagementSystem(roadList);
        wfm = new WaveFrontManager(roadList);
        dm = new DensityManager(roadList);
    }

    public List<Road> getRoadList() {
        return roadList;
    }

    private double calculateNewSpeed(Car car){
        CarParameters carParameters = car.getParameters();
        Route route = car.getDriver().getRoute();
        Car carNext = route.getNextCarOnRoute();
        Vector2D carNextSpeed = (carNext != null) ? carNext.getSpeedCopy() : new Vector2D();

        Vector2D carSpeed = car.getSpeedCopy();
        double carSpeedValue = carSpeed.getLength();
        double carSpeedNextValue = carNextSpeed.getLength();

        double distance = route.calculateDistanceAlongRoute(carNext);

        double desiredDistance = carParameters.getMinimumGap() + Math.max(0, carSpeedValue * carParameters.getTimeGap() + carSpeedValue * (( carSpeedValue-carSpeedNextValue)) / 2 / Math.sqrt(carParameters.getAcceleration() * carParameters.getDeceleration()));
        double carSpeedNewValue = (carSpeedValue+carParameters.getAcceleration() * (1 - Math.pow(carSpeedValue / carParameters.getDesiredSpeed(), carParameters.getAcceleration()) - Math.pow(desiredDistance / distance, 2)));
        if(carSpeedNewValue<0){
            carSpeedNewValue=0;
        }
        return carSpeedNewValue;

    }

    public void updateSpeed(){
        Collections.shuffle(roadList);
        for (Road road : roadList) {
            List<Car> cars = new ArrayList<>(road.getCarList());
            //Collections.shuffle(cars);
            for (int i = 0; i < road.getCarList().size(); i++) {
                Car car = cars.get(i);
                double carSpeedNew = calculateNewSpeed(car);
                car.setSpeed(road.getLineSegment().getDirection().multiply(carSpeedNew));
                updatePosition(car, road);
            }
        }
        tms.update();
        wfm.updateWaveFronts();
        dm.update();
        time.setValue(time.getValue()+1);
    }

    private void updatePosition(Car car, Road road){
        Vector2D carSpeed = car.getSpeedCopy();
        double carSpeedValue = carSpeed.getLength();
        double distanceToEnd = road.getDistanceToEnd(car);
        double distanceOnNewRoad = carSpeedValue - distanceToEnd;

        if ( distanceOnNewRoad <= 0 ){
            car.setPosition(car.getPosition().addVector2D(carSpeed));
        }
        else {
            Road nextRoad = car.getDriver().getRoute().getNextRoad();
            if (!road.getTrafficLightsEnd().isStop() && nextRoad!=null && nextRoad.insertCar(car)){
                road.removeCar(car);
                car.setPosition(car.getPosition().addVector2D(car.getDirection().multiply(distanceOnNewRoad)));
            }
            else {
                car.setSpeed(new Vector2D());
            }
        }
    }

    public IntegerProperty timeProperty() {
        return time;
    }
    public int getTime() {
        return time.getValue();
    }

    public TrafficManagementSystem getTrafficManagementSystem() {
        return tms;
    }

    public WaveFrontManager getWaveFrontManager() {  return wfm;   }
}
