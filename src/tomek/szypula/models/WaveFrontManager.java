package tomek.szypula.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class WaveFrontManager {

    private final Model model;
    List<Road> roadList;
    private ObservableList<Wave> waves = FXCollections.observableList(new ArrayList<>()) ;
    WaveFrontFinder waveFrontFinder;


    public WaveFrontManager(Model model) {
        waveFrontFinder = new WaveFrontFinder();
        this.model = model;
        this.roadList = model.getRoadList();
    }

    public void updateWaveFronts() {
        List<Wave> newWaves = new ArrayList<>();
        List<Wave> waveFrontsToRemove = new ArrayList<>(waves);

        System.out.println(" ");
        //Get all current wavefronts
        newWaves.addAll(waveFrontFinder.findWaveFronts(model));

        //Find coresponding existing wavefront and update
        for (Wave wave :
                waves) {

            Route routeOfCurrentCar = wave.getWaveFront().getCar().getDriver().getRoute();
            Wave matchedNewWave = null;

            for (int i = 0; i < newWaves.size() ; i++){
                if (wave.getWaveFront().getCar().equals(newWaves.get(i).getWaveFront().getCar()) || routeOfCurrentCar.isCarInVicinityOnRoute(newWaves.get(i).getWaveFront().getCar())){
                    matchedNewWave = newWaves.remove(i);
                    wave.setWaveFronts(matchedNewWave);
                    waveFrontsToRemove.remove(wave);
                    break;
                }
            }
        }

        //delete old wavefronts
        removeWaves(waveFrontsToRemove);

        //Add new wavefronts
        waves.addAll(newWaves);

    }

    public boolean removeWaves(List<Wave> wavesToRemove){
        return waves.removeAll(wavesToRemove);
    }
    public void removeWaveFrontsFromCars(List<Car> carList){
        List<Wave> waveFrontsToRemove = new ArrayList<>();
        for (Wave wave :
                waves) {
            if(carList.contains(wave.getWaveFront().getCar()) || carList.contains(wave.getWaveEnd().getCar()))
                waveFrontsToRemove.add(wave);
        }
        removeWaves(waveFrontsToRemove);

    }



    public ObservableList<Wave> getWaves() {
        return waves;
    }
}
