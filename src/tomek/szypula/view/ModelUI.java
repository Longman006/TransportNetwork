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
    List<WaveFrontUI> waveFrontUIs = new ArrayList<>();
    Group root =  new Group();
    Group carParent = new Group();
    Group roadParent = new Group();
    Group jamParent = new Group();
    Group waveParent = new Group();

    Model model;


    public ModelUI(Model model) {
        this.model = model;
        createCarUIs(model.getTrafficManagementSystem().getCarList());
        //createJamUIs();
        createRoadUIs();
        modelPane.getChildren().add(root);
        root.getChildren().addAll(roadParent,jamParent,carParent,waveParent);
        model.getTrafficManagementSystem().numberOfCarsProperty().addListener((observableValue, oldNumber, newNumber) -> {
            int difference = newNumber.intValue() - oldNumber.intValue();
            if (difference > 0 ){
                List<Car> carList = model.getTrafficManagementSystem().addNewCars(difference);
                createCarUIs(carList);
            }
            else if(difference < 0 ){
                List<Car> carList = model.getTrafficManagementSystem().removeCars(-difference);
                removeCarUIs(carList);
            }
            if (newNumber.intValue() != model.getTrafficManagementSystem().getNumberOfCars()){
                System.out.println("Not up to date");
                System.out.println("new : "+newNumber.intValue());
                System.out.println("Real : "+model.getTrafficManagementSystem().getNumberOfCars());
            }
        });
        model.getTrafficManagementSystem().getWaveFronts().addListener(new ListChangeListener<Wavefront>() {
            @Override
            public void onChanged(Change<? extends Wavefront> change) {
                //System.out.println("Detected a change in WaveFronts! ");
                while(change.next()){
                    if (change.wasAdded()){
                        createWaveFrontUIs((List<Wavefront>) change.getAddedSubList());
                    }
                    else if(change.wasRemoved()){
                        removeWaveUIs((List<Wavefront>) change.getRemoved());
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

    private void createJamUIs() {
        for (Jam jam : model.getTrafficManagementSystem().getJamList()){
            JamUI jamUI = new JamUI(jam);
            jamUIs.add(jamUI);
            jamUI.createUI(jamParent);
        }
        UIelements.addAll(jamUIs);

    }
    private void createRoadUIs(){
        for(Road road : model.getTrafficManagementSystem().getRoadList()){
            RoadUI roadUI = new RoadUI(road);
            roadUIs.add(roadUI);
            roadUI.createUI(roadParent);
        }
        UIelements.addAll(roadUIs);
    }
    private void createWaveFrontUIs(List<Wavefront> wavefronts){
        for (Wavefront wavefront :
                wavefronts) {
            WaveFrontUI waveFrontUI = new WaveFrontUI(wavefront);
            waveFrontUIs.add(waveFrontUI);
            waveFrontUI.createUI(waveParent);
        }
        UIelements.addAll(waveFrontUIs);
    }
    private void removeWaveUIs(List<Wavefront> wavefronts) {
        List<WaveFrontUI> waveUIsRemove = new ArrayList<>();
        for (WaveFrontUI waveFrontUI: waveFrontUIs){
            if (wavefronts.contains(waveFrontUI.getWavefront())){
                waveUIsRemove.add(waveFrontUI);
            }
        }
        for (WaveFrontUI waveFrontUI: waveUIsRemove){
            waveFrontUI.remove(waveParent);
            waveFrontUIs.remove(waveFrontUI);
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
