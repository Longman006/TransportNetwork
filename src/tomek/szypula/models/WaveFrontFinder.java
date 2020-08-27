package tomek.szypula.models;

import tomek.szypula.math.PeakDetector;

import java.util.*;

public class WaveFrontFinder {
    int lag = 15;
    double influence = 0;
    double threshold = 3.5;
    PeakDetector peakDetector;
    List<Double> dummyData;

    WaveFrontFinder(){
        dummyData = new ArrayList<>();
        peakDetector = new PeakDetector();

        for(int i = 0; i < lag ; i++)
            dummyData.add(1d);
    }

    List<Wave> findWaveFronts(Model model){
        List<Car> cars = model.getTrafficManagementSystem().getCars();
        List<Wave> allCurrentWaves = new ArrayList<>();

        //Apply peak detection algorithm and find all the traffic congestions
        detectCongestions(cars);

        //Based on traffic jams detected, measure the start and length of congestion waves
        allCurrentWaves = findWaveFrontsOnRoads(model.getRoadList());

        return new ArrayList<>(allCurrentWaves);
    }
    private void detectCongestions(List<Car> cars){
        //if there are no cars lets just ignore
        if (cars.size() == 0)
            return;
        List<Double> dataInput = new ArrayList<>();

        //Adding dummy data for initial reference
        dataInput.addAll(dummyData);

        //Adding actual data as input
        for (Car car :
                cars) {
            dataInput.add(car.getSpeedDensityIndex());
        }

        //applying algorithm on input data
        HashMap<String,List> outputData = peakDetector.analyzeDataForSignals(dataInput,lag,threshold,influence);

        //Retrieve the results
        List<Integer> peaksDetected = outputData.get("signals");

        //Get rid of dummy initial reference values
        peaksDetected = peaksDetected.subList(lag,peaksDetected.size());

        //Based on peaks detected specify is car is in traffic congestion or not
        for(int index = 0; index < cars.size(); index++){
            if(peaksDetected.get(index) == 0)
                cars.get(index).setIsCongestionWave(false);
            else
                cars.get(index).setIsCongestionWave(true);
        }

    }

    private List<Wave> findWaveFrontsOnRoads(List<Road> roads) {

        Set<Wave> waves = new HashSet<>();
        Wave currentWave = null;

        for (Road road:
             roads) {
            //temporary list to store wavefronts
            List<Wave> wavesOnRoad = new ArrayList<>();
            List<Car> cars = road.getCarList();

            //Check if there are any cars on the road
            if (cars.size() == 0) {
                continue;
            }
            //If the first car is part of a traffic jam, check with every previous road
            //whether this is in fact the wavefront or just a continuation of a previous one
            if (cars.get(0).isIsCongestionWave()){
                currentWave = new Wave(cars.get(0),road);
                wavesOnRoad.add(currentWave);
                for (Road previousRoad:
                road.getPreviousRoadList()) {
                    Car lastCar = null;
                    if (!previousRoad.isEmpty())
                        lastCar = previousRoad.getLastCar();

                    //If there already exists a congestion don't include the currentWave. This jam has already been accounted for
                    if (lastCar != null && lastCar.isIsCongestionWave()){
                        wavesOnRoad = new ArrayList<>();
                        break;
                    }
                }
            }

            //setting up variables for tracking
            boolean previousValue = cars.get(0).isIsCongestionWave();
            Car previousCar = cars.get(0);
            boolean currentValue = previousValue;
            Car currentCar = previousCar;

            for(int index = 1 ; index < cars.size() ; index++){
                currentCar = cars.get(index);
                currentValue = currentCar.isIsCongestionWave();

                if (currentValue == true ){
                    if (previousValue == false){
                        //we have a new wavefront
                        currentWave = new Wave(currentCar,road);
                        wavesOnRoad.add(currentWave);
                    }
                    //We want to also track the size of the wave
                    currentWave.incrementWaveSize();
                }
                //Here we find the end of the wave
                else if(previousValue == true){
                    currentWave.getWaveEnd().setCar(previousCar,road);
                }
                previousCar = currentCar;
                previousValue = currentValue;
            }

            //if we ended on a wavefront we have to measure the rest of it, recurrence function
            if (currentValue == true){
                measureWaveFrontUntilEnd(currentWave,road, currentCar);
            }

            waves.addAll(wavesOnRoad);

        }

        return new ArrayList<>(waves);
    }

    void measureWaveFrontUntilEnd(Wave currentWave, Road currentRoad, Car lastCar){
        for (Road nextRoad :
                currentRoad.getRoadList() ) {
            List<Car> cars = nextRoad.getCarList();
            //setting up variables for tracking
            boolean currentValue = false;
            Car currentCar = lastCar;
            Car previousCar = lastCar;
            Road road = currentRoad;

            //if there are no cars on the road, the last car on the previous road was in fact the end
            if(cars.size() == 0) {
                currentWave.getWaveEnd().setCar(previousCar, road);
                break;
            }

            //copy pasted from above for now with modifications, maybe could be recurrence in the future
            for(int index = 0 ; index < cars.size() ; index++){
                currentCar = cars.get(index);
                currentValue = currentCar.isIsCongestionWave();

                //we found the end of the wave
                if (currentValue == false){
                    currentWave.getWaveEnd().setCar(previousCar,road);
                    break;
                }
                if (currentValue == true){
                    //We want to also track the size of the wave
                    currentWave.incrementWaveSize();
                }
                
                previousCar = currentCar;
                road = nextRoad;
            }

            //if we ended on a wavefront we have to measure the rest of it, recurrence function
            if (currentValue == true){
                measureWaveFrontUntilEnd(currentWave,road,currentCar);
            }
        }
    }
}
