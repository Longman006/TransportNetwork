package tomek.szypula.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tomek.szypula.controller.Updatable;
import tomek.szypula.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class TrafficManagementSystem implements Updatable {

    private List<Road> roadList;
    private static CarParameters defaultCarParameters = new CarParameters();
    private IntegerProperty desiredNumberOfCars = new SimpleIntegerProperty();
    private ObservableList<Car> cars = FXCollections.observableList(new ArrayList<>()) ;
    private List<Road> startingRoads = new ArrayList<>();

    public List<Road> getStartingRoads() {
        findStartingRoads();
        return startingRoads;
    }

    private ArrayList<Road> endRoads = new ArrayList<>();

    public TrafficManagementSystem(List<Road> roadList) {
        this.roadList = roadList;
        findStartingRoads();
        findEndRoads();
        desiredNumberOfCars.set(getCars().size());
    }

    private void findStartingRoads() {
        startingRoads = new ArrayList<>(roadList);
        for (Road road : roadList){
            startingRoads.removeAll(road.getRoadList());
        }
    }
    private void findEndRoads(){
        endRoads = new ArrayList<>();
        for (Road road : roadList){
            if (road.getRoadList().size() == 0){
                endRoads.add(road);
            }
        }
    }

    public static CarParameters getCarParameters() {
        return defaultCarParameters;
    }

    public List<Road> getRoadList() {
        return roadList;
    }

    public int getDesiredNumberOfCars() {
        return desiredNumberOfCars.get();
    }

    public IntegerProperty desiredNumberOfCarsProperty() {
        return desiredNumberOfCars;
    }

    public List<Car> addNewCars(int n){
        //findStartingRoads();
        System.out.println("n " + n);
        int i =0;
        int size = startingRoads.size();
        List<Car> carList = new ArrayList<>(n);
        while (i<n){
            Road road = startingRoads.get((int) (Math.random()*size));
            Car car = new Car(road.getStartCopy(),road.getLineSegment().getDirection(),TrafficManagementSystem.getCarParameters());
            car.setDriver(new RandomDriver(new Vector2D(),road,car));
            carList.add(car);
            road.addCar(car);
            i++;
        }
        cars.addAll(carList);
        return carList;
    }

    public List<Car> removeCars(int n){
        List<Car> cars = getCars();
        List<Car> carListToRemove = new ArrayList<>(n);

        for(int i = 0 ; i<n ; i++){
            carListToRemove.add(cars.get(i));
        }
        return removeCars(carListToRemove);
    }

    private List<Car> removeCars(List<Car> carListToRemove){
        for (Road road: roadList){
            road.getCarList().removeAll(carListToRemove);
        }
        cars.removeAll(carListToRemove);
        return carListToRemove;
    }

    public List<Road> getOnRamps() {
        List<Road> onRamps = new ArrayList<>();

        for (Road road :
                getRoadList()) {
            if (road.getOnRamp().isOnRamp())
                onRamps.add(road);
        }

        return onRamps;
    }

    @Override
    public void update() {
        removeCarsOnDeadEnds();
    }

    private void removeCarsOnDeadEnds() {
        List<Car> carListToRemove = new ArrayList<>();
        for (Road road :
                endRoads) {
            if (!road.isEmpty()) {
                Car lastCar = road.getLastCar();
                if ( lastCar.getSpeed().getLength() < defaultCarParameters.getDesiredSpeed()*0.01)
                    carListToRemove.add(lastCar);
            }
        }
        if (carListToRemove.isEmpty())
            return;

        removeCars(carListToRemove);
        addNewCars(carListToRemove.size());

    }

    public ObservableList<Car> getCars() {
        return cars;
    }
}
