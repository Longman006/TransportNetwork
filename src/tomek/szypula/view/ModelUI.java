package tomek.szypula.view;

import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import tomek.szypula.models.*;

import java.util.ArrayList;
import java.util.List;

public class ModelUI implements CreateUI {
    Pane modelPane = new Pane();
    private List<CreateUI> UIelements = new ArrayList<>();
    List<CarUI> carUIs = new ArrayList<>();
    List<CreateUI> roadUIs = new ArrayList<>();
    List<CreateUI> jamUIs = new ArrayList<>();
    List<WaveUI> waveUIs = new ArrayList<>();
    Group root =  new Group();
    Group carParent = new Group();
    Group roadParent = new Group();
    Group jamParent = new Group();
    Group waveParent = new Group();

    Model model;


    public ModelUI(Model model) {
        this.model = model;
        createCarUIs(model.getTrafficManagementSystem().getCars());
        //createJamUIs();
        createRoadUIs();
        modelPane.getChildren().add(root);
        root.getChildren().addAll(roadParent,jamParent,carParent,waveParent);

        //Tracking the number of Cars to add or remove UIs if necessary
        model.getTrafficManagementSystem().desiredNumberOfCarsProperty().addListener((observableValue, oldNumber, newNumber) -> {
            int difference = newNumber.intValue() - oldNumber.intValue();
            if (difference > 0 ){
                List<Car> carList = model.getTrafficManagementSystem().addNewCars(difference);
            }
            else if(difference < 0 ){
                List<Car> carList = model.getTrafficManagementSystem().removeCars(-difference);
            }
            if (newNumber.intValue() != model.getTrafficManagementSystem().getDesiredNumberOfCars()){
                System.out.println("Not up to date. Oops.");
                System.out.println("New : "+newNumber.intValue());
                System.out.println("Real : "+model.getTrafficManagementSystem().getDesiredNumberOfCars());
            }
        });

        //Tracking the number of cars
        model.getTrafficManagementSystem().getCars().addListener(new ListChangeListener<Car>() {
            @Override
            public void onChanged(Change<? extends Car> change) {
                while(change.next()){
                    if (change.wasAdded()){
                        createCarUIs((List<Car>) change.getAddedSubList());
                    }
                    else if(change.wasRemoved()){
                        model.getWaveFrontManager().removeWaveFrontsFromCars((List<Car>) change.getRemoved());
                        removeCarUIs((List<Car>) change.getRemoved());
                    }
                }
            }
        });
        //Tracking the number of WaveFronts
        model.getWaveFrontManager().getWaves().addListener(new ListChangeListener<Wave>() {
            @Override
            public void onChanged(Change<? extends Wave> change) {
                while(change.next()){
                    if (change.wasAdded()){
                        createWaveUIs((List<Wave>) change.getAddedSubList());
                    }
                    else if(change.wasRemoved()){
                        removeWaveUIs((List<Wave>) change.getRemoved());
                    }
                }
            }
        });

    }

    private void removeCarUIs(List<Car> carList) {
        List<CarUI> carUIsRemove = new ArrayList<>();
        for (CarUI carUI: carUIs){
            if (carList.contains(carUI.getCar())){
                carUIsRemove.add(carUI);
            }
        }
        for (CarUI carUI: carUIsRemove){
            carUI.remove(carParent);
            carUIs.remove(carUI);
        }
    }


    private void createCarUIs(List<Car> cars) {
        for (Car car : cars) {
            CarUI carUI = new CarUI(car);
            carUIs.add(carUI);
            carUI.createUI(carParent);
        }
        UIelements.addAll(carUIs);
    }

//    private void createJamUIs() {
//        for (Jam jam : model.getTrafficManagementSystem().getJamList()){
//            JamUI jamUI = new JamUI(jam);
//            jamUIs.add(jamUI);
//            jamUI.createUI(jamParent);
//        }
//        UIelements.addAll(jamUIs);
//
//    }
    private void createRoadUIs(){
        for(Road road : model.getTrafficManagementSystem().getRoadList()){
            RoadUI roadUI = new RoadUI(road);
            roadUIs.add(roadUI);
            roadUI.createUI(roadParent);
        }
        UIelements.addAll(roadUIs);
    }
    private void createWaveUIs(List<Wave> waves){
        for (Wave wave :
                waves) {
            WaveUI waveUI = new WaveUI(wave);
            waveUIs.add(waveUI);
            waveUI.createUI(waveParent);
        }
        UIelements.addAll(waveUIs);
    }
    private void removeWaveUIs(List<Wave> waves) {
        List<WaveUI> waveUIsRemove = new ArrayList<>();
        for (WaveUI waveUI: waveUIs){
            if (waves.contains(waveUI.getWave())){
                waveUIsRemove.add(waveUI);
            }
        }
        for (WaveUI waveUI: waveUIsRemove){
            waveUI.remove(waveParent);
            waveUIs.remove(waveUI);
        }
    }


    @Override
    public void createUI(Group parent) {
        parent.getChildren().add(root);
    }

    @Override
    public void remove(Group parent) {
        parent.getChildren().remove(root);
    }

    public Pane getModelPane() {
        return modelPane;
    }
}
