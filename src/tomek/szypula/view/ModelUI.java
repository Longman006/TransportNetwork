package tomek.szypula.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import tomek.szypula.models.Car;
import tomek.szypula.models.Jam;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

import java.util.ArrayList;
import java.util.List;

public class ModelUI implements CreateUI {
    Pane modelPane = new Pane();
    private List<CreateUI> UIelements = new ArrayList<>();
    List<CreateUI> carUIs = new ArrayList<>();
    List<CreateUI> roadUIs = new ArrayList<>();
    List<CreateUI> jamUIs = new ArrayList<>();
    Group root =  new Group();
    Group carParent = new Group();
    Group roadParent = new Group();
    Group jamParent = new Group();

    Model model;


    public ModelUI(Model model) {
        this.model = model;
        createCarUIs();
        createJamUIs();
        createRoadUIs();
        modelPane.getChildren().add(root);
        root.getChildren().addAll(roadParent,jamParent,carParent);

    }

    private void createCarUIs() {
        for (Car car : model.getTrafficManagementSystem().getCarList()) {
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


    @Override
    public void createUI(Group parent) {
        parent.getChildren().add(root);
    }

    public Pane getModelPane() {
        return modelPane;
    }
}
