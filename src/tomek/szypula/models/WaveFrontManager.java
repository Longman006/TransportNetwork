package tomek.szypula.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class WaveFrontManager {

    List<Road> roadList;
    private ObservableList<WaveFront> waveFronts = FXCollections.observableList(new ArrayList<>()) ;


    public WaveFrontManager(List<Road> roadList) {
        this.roadList = roadList;
    }

    public void updateWaveFronts() {
        List<WaveFront> newWaveFronts = new ArrayList<>();
        List<WaveFront> waveFrontsToRemove = new ArrayList<>();
        //Get all current wavefronts
        for (Road road :
                roadList) {
            newWaveFronts.addAll(findWaveFronts(road));
        }

        //Find coresponding existing wavefronts and update
        for (WaveFront wavefront :
                waveFronts) {
            Route routeOfCurrentCar = wavefront.getCar().getDriver().getRoute();
            for (int i = 0 ; i < newWaveFronts.size() ; i++){
                if (wavefront.getCar().equals(newWaveFronts.get(i).getCar()) || routeOfCurrentCar.isCarInVicinityOnRoute(newWaveFronts.get(i).getCar())){
                    WaveFront removed = newWaveFronts.remove(i);
                    wavefront.setCar(removed.getCar(),removed.getCurrentRoad());
                    break;
                }
                //If we looped through all the new wavefronts then this wavefront should be removed.
                if (i == newWaveFronts.size()-1){
                    waveFrontsToRemove.add(wavefront);
                }
            }
        }
        //delete old wavefronts
        waveFronts.removeAll(waveFrontsToRemove);
        //Add new wavefronts
        for (WaveFront waveFront :
                newWaveFronts) {
            waveFronts.add(waveFront);
        }
    }

    private List<WaveFront> findWaveFronts(Road road) {
        List<Car> cars = road.getCarList();
        List<WaveFront> waveFronts = new ArrayList<>();
        //Check if there are any cars on the road
        if(cars.size() == 0){
            return waveFronts;
        }
        //check every pair of cars if they form a wavefront
        for (int i  = 1; i < cars.size();i++) {
            Car car = cars.get(i);
            if(checkIfWaveFront(car,cars.get(i-1)) ){
                waveFronts.add(new WaveFront(car,road));

            }
        }
        //Check the first car on road with every possible previous car
        Car initialCar = cars.get(0);
        for (Road previousRoad :
                road.getPreviousRoadList()) {
            if (previousRoad.getCarList().size() > 0 &&
                    checkIfWaveFront(initialCar,previousRoad.getCarList().get(previousRoad.getCarList().size()-1))){
                waveFronts.add(new WaveFront(initialCar,road));
                return waveFronts;
            }
        }
        //If no previous car was found but initial car has a small speed then treat as wavefront
        if (initialCar.hasSmallSpeed()){
            waveFronts.add(new WaveFront(initialCar,road));
        }
        return waveFronts;
    }
    private boolean checkIfWaveFront(Car car, Car previousCar){
//        if(car.hasSmallSpeed() && Vector2DMath.valueDifference(previousCar.getSpeedCopy(),car.getSpeedCopy()) > 0 )
//            return true;
//        return false;
        if (car.getSpeedDensityIndex() > 0.02)
            return true;
        return false;
    }
    public ObservableList<WaveFront> getWaveFronts() {
        return waveFronts;
    }
}
