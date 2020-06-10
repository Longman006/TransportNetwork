package tomek.szypula.models;

import tomek.szypula.controller.Updatable;
import tomek.szypula.math.Vector2DMath;

import java.util.List;

public class DensityManager implements Updatable {
    List<Road> roadList;
    double desiredSpeed;
    double carSize;
    double minimumGap;

    public DensityManager(List<Road> roadList) {
        this.roadList = roadList;
        desiredSpeed = TrafficManagementSystem.getCarParameters().getDesiredSpeed();
        carSize = TrafficManagementSystem.getCarParameters().getSize();
        minimumGap = TrafficManagementSystem.getCarParameters().getMinimumGap();
    }

    private void updateCarSpeedDensityIndex(){
        double width = 5*carSize+minimumGap;
        for (Road road :
             roadList) {
            for (int i = 0; i < road.getCarList().size() ; i++  ) {
                Car car = road.getCarList().get(i);
                //System.out.println("Car "+car);
                double speedDensityIndex = calculateSpeedDensityIndex(car,car,width);
                double partialSpeedDensityIndex = 0;
                Car previousCar = car;
                Car nextCar = car;

                //Finding pairs Car - Car backwards
                while(true) {
                        previousCar = previousCar.getDriver().getRoute().getPreviousCarOnRoute();
                        partialSpeedDensityIndex = calculateSpeedDensityIndex(car, previousCar,width);
                        if (partialSpeedDensityIndex >= 0)
                            speedDensityIndex+=partialSpeedDensityIndex;
                        else
                            break;
                    }

                //Finding pairs Car - Car forward
                while(true) {
                    nextCar = nextCar.getDriver().getRoute().getNextCarOnRoute();
                    partialSpeedDensityIndex = calculateSpeedDensityIndex(car, nextCar,width);
                    if (partialSpeedDensityIndex >= 0)
                        speedDensityIndex+=partialSpeedDensityIndex;
                    else
                        break;
                    }

                car.setSpeedDensityIndex(speedDensityIndex);
            }
        }
    }

    double calculateSpeedDensityIndex(Car car, Car previousCar, double width){
        double distance = (previousCar != null) ? Vector2DMath.distance(car.getPosition(),previousCar.getPosition()) : width;
        double result = 0;
        if (distance < width) {
            //result = 1 / (1 +(car.getSpeed().getLength()));
            //result = Math.abs(car.getSpeed().getLength() - previousCar.getSpeed().getLength())/desiredSpeed;
            result = 1;
        }
        else
            result = -1;
        return result;
    }
    @Override
    public void update() {
        updateCarSpeedDensityIndex();
    }
}
