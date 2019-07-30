package tomek.szypula.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
    private ObservableList<Wavefront> waveFronts = FXCollections.observableList(new ArrayList<>()) ;

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
        updateWaveFronts(waveFronts);
    }

    private void updateWaveFronts(List<Wavefront> waveFronts) {
        List<Car> cars = new ArrayList<>();
        List<Wavefront> waveFrontsToRemove = new ArrayList<>();
        //Get all current wavefronts
        for (Road road :
                roadList) {
            cars.addAll(findWaveFronts(road));
        }

        //Find coresponding existing wavefronts and update
        for (Wavefront wavefront :
                waveFronts) {
            for (int i = 0 ; i < cars.size() ; i++){
                if (wavefront.inVicinity(cars.get(i))){
                    wavefront.setCar(cars.remove(i));
                    break;
                }
                if (i == cars.size()-1){
                    waveFrontsToRemove.add(wavefront);
                }
            }


        }
        //delete old wavefronts
        waveFronts.removeAll(waveFrontsToRemove);
        //Add new wavefronts
        for (Car car :
                cars) {
            waveFronts.add(new Wavefront(car));
        }
    }

    private List<Car> findWaveFronts(Road road) {
        List<Car> cars = road.getCarList();
        List<Car> waveFronts = new ArrayList<>();
        //Check if there are any cars on the road
        if(cars.size() == 0){
            return waveFronts;
        }
        //check every pair of cars if they form a wavefront
        for (int i  = 1; i < cars.size();i++) {
            Car car = cars.get(i);
            if(checkIfWaveFront(car,cars.get(i-1)) ){
                waveFronts.add(car);

            }
        }
        //Check the first car on road with every possible previous car
        Car initialCar = cars.get(0);
        for (Road previousRoad :
                road.getPreviousRoadList()) {
            if (previousRoad.getCarList().size() > 0 &&
                    checkIfWaveFront(initialCar,previousRoad.getCarList().get(previousRoad.getCarList().size()-1))){
                waveFronts.add(initialCar);
                return waveFronts;
            }
        }
        //If no previous car was found but initial car has a small speed then treat as wavefront
        if (initialCar.hasSmallSpeed()){
            waveFronts.add(initialCar);
        }
        return waveFronts;
    }
    private boolean checkIfWaveFront(Car car, Car previousCar){
        if(car.hasSmallSpeed() && Vector2DMath.valueDifference(previousCar.getSpeedCopy(),car.getSpeedCopy()) < 0)
            return true;
        return false;
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

    public ObservableList<Wavefront> getWaveFronts() {
        return waveFronts;
    }
}
