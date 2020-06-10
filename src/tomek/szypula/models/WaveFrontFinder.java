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

    List<WaveFront> findWaveFronts(Model model){
        List<Car> cars = model.getTrafficManagementSystem().getCarList();
        List<WaveFront> allCurrentWaveFronts = new ArrayList<>();

        //Apply peak detection algorithm and find all the traffic congestions
        detectCongestions(cars);

        //Based on traffic jams detected, measure the start and length of congestion waves
        allCurrentWaveFronts = findWaveFrontsOnRoads(model.getRoadList());

        return new ArrayList<>(allCurrentWaveFronts);
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

    private List<WaveFront> findWaveFrontsOnRoads(List<Road> roads) {

        Set<WaveFront> waveFronts = new HashSet<>();
        WaveFront currentWaveFront = null;

        for (Road road:
             roads) {
            //temporary list to store wavefronts
            List<WaveFront> waveFrontsOnRoad = new ArrayList<>();
            List<Car> cars = road.getCarList();

            //Check if there are any cars on the road
            if (cars.size() == 0) {
                continue;
            }
            //If the first car is part of a traffic jam, check with every previous road
            //whether this is in fact the wavefront or just a continuation of a previous one
            if (cars.get(0).isIsCongestionWave()){
                currentWaveFront = new WaveFront(cars.get(0),road);
                waveFrontsOnRoad.add(currentWaveFront);
                for (Road previousRoad:
                road.getPreviousRoadList()) {
                    Car lastCar = null;
                    if (!previousRoad.isEmpty())
                        lastCar = previousRoad.getLastCar();

                    //If there already exists a congestion don't include the currentWaveFront. This jam has already been accounted for
                    if (lastCar != null && lastCar.isIsCongestionWave()){
                        waveFrontsOnRoad = new ArrayList<>();
                        break;
                    }
                }
            }

            //setting up variables for tracking
            boolean previousValue = cars.get(0).isIsCongestionWave();
            boolean currentValue = previousValue;
            Car currentCar;

            for(int index = 1 ; index < cars.size() ; index++){
                currentCar = cars.get(index);
                currentValue = currentCar.isIsCongestionWave();

                if (currentValue == true ){
                    if (previousValue == false){
                        //we have a new wavefront
                        currentWaveFront = new WaveFront(currentCar,road);
                        waveFrontsOnRoad.add(currentWaveFront);
                    }
                    currentWaveFront.incrementWaveSize();
                }
                previousValue=currentValue;
            }

            //if we ended on a wavefront we have to measure the rest of it, recurrence function
            if (currentValue == true){
              // measureWaveFrontUntilEnd(currentWaveFront,road);
            }

            waveFronts.addAll(waveFrontsOnRoad);

        }

        return new ArrayList<>(waveFronts);
    }

    void measureWaveFrontUntilEnd(WaveFront currentWaveFront, Road currentRoad){
        for (Road nextRoad :
                currentRoad.getRoadList() ) {
            List<Car> cars = nextRoad.getCarList();
            Car currentCar;
            boolean currentValue = false;
            //copy pasted from above for now with modifications, maybe could be recurrence in the future
            for(int index = 0 ; index < cars.size() ; index++){
                currentCar = cars.get(index);
                currentValue = currentCar.isIsCongestionWave();

                if (currentValue == false)
                    break;
                else
                    currentWaveFront.incrementWaveSize();
            }
            //if we ended on a wavefront we have to measure the rest of it, recurrence function
            if (currentValue == true)
                measureWaveFrontUntilEnd(currentWaveFront,nextRoad);
        }
    }
}
