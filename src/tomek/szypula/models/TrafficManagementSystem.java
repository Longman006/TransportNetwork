package tomek.szypula.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;

import java.util.ArrayList;
import java.util.List;

public class TrafficManagementSystem {

    private List<Road> roadList;
    private List<Jam> jamList = new ArrayList<>();
    private static CarParameters defaultCarParameters = new CarParameters();
    private IntegerProperty numberOfCars = new SimpleIntegerProperty();
    private List<Road> startingRoads = new ArrayList<>();
    private ArrayList<Road> endRoads = new ArrayList<>();

    public TrafficManagementSystem(List<Road> roadList) {
        this.roadList = roadList;
        numberOfCars.set(getCarList().size());
        createJams();


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

    private void createJams() {
        for (Road road : roadList) {
            jamList.add(new Jam(road));
        }
    }


    public void update() {
        for (Jam jam : jamList){
            jam.updateJam();
        }

    }



    public List<Road> getRoadList() {
        return roadList;
    }

    public List<Car> getCarList(){
        List<Car> carList = new ArrayList<>();
        for (Road road : roadList)
            carList.addAll(road.getCarList());
        return carList;
    }

    public List<Jam> getJamList() {
        return jamList;
    }

    public int getNumberOfCars() {
        return numberOfCars.get();
    }

    public IntegerProperty numberOfCarsProperty() {
        return numberOfCars;
    }

    public List<Car> addNewCars(int n){
        findStartingRoads();
        int i =0;
        int size = startingRoads.size();
        List<Car> carList = new ArrayList<>(n);
        while (i<n){
            Road road = startingRoads.get(i%size);
            Car car = new Car(road.getStart(),road.getLineSegment().getDirection(),TrafficManagementSystem.getCarParameters());
            car.setDriver(new RandomDriver(new Vector2D(),road,car));
            road.addCar(car);
            i++;
            carList.add(car);
        }
        return carList;
    }
    public List<Car> removeCars(int n){
        List<Car> cars = getCarList();
        List<Car> carListRemoved = new ArrayList<>(n);

        for(int i = 0 ; i<n ; i++){
            carListRemoved.add(cars.get(i));
        }
        for (Road road: roadList){
            road.getCarList().removeAll(carListRemoved);
        }
        return carListRemoved;
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
}
