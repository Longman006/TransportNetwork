package tomek.szypula.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class WaveFrontManager {

    private final Model model;
    List<Road> roadList;
    private ObservableList<WaveFront> waveFronts = FXCollections.observableList(new ArrayList<>()) ;
    WaveFrontFinder waveFrontFinder;


    public WaveFrontManager(Model model) {
        waveFrontFinder = new WaveFrontFinder();
        this.model = model;
        this.roadList = model.getRoadList();
    }

    public void updateWaveFronts() {
        List<WaveFront> newWaveFronts = new ArrayList<>();
        List<WaveFront> waveFrontsToRemove = new ArrayList<>();

        //Get all current wavefronts
        newWaveFronts.addAll(waveFrontFinder.findWaveFronts(model));

        //Find coresponding existing wavefront and update
        for (WaveFront wavefront :
                waveFronts) {

            Route routeOfCurrentCar = wavefront.getCar().getDriver().getRoute();
            WaveFront matchedNewWaveFront = null;

            for (int i = 0 ; i < newWaveFronts.size() ; i++){
                if (wavefront.getCar().equals(newWaveFronts.get(i).getCar()) || routeOfCurrentCar.isCarInVicinityOnRoute(newWaveFronts.get(i).getCar())){
                    matchedNewWaveFront = newWaveFronts.remove(i);
                    wavefront.setCar(matchedNewWaveFront.getCar(),matchedNewWaveFront.getCurrentRoad());
                    wavefront.setWaveSize(matchedNewWaveFront.getWaveSize());
                    break;
                }
                //If we looped through all the new wavefronts then this wavefront should be removed.
                if (i == newWaveFronts.size()-1){
                    waveFrontsToRemove.add(wavefront);
                }
            }
            newWaveFronts.remove(matchedNewWaveFront);

        }

        //delete old wavefronts
        waveFronts.removeAll(waveFrontsToRemove);

        //Add new wavefronts
        waveFronts.addAll(newWaveFronts);
        for (WaveFront waveFront :
                waveFronts) {
            System.out.println("WaveFront with CarID "+waveFront.getCar().getId() + " Size "+waveFront.getWaveSize());
        }
        System.out.println(" ");
    }

    public ObservableList<WaveFront> getWaveFronts() {
        return waveFronts;
    }
}
