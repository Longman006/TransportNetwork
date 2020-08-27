package tomek.szypula.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tomek.szypula.controller.Highlightable;
import tomek.szypula.controller.Highlighted;
import tomek.szypula.controller.UniqueId;
import tomek.szypula.models.CarParameters;
import tomek.szypula.models.Model;
import tomek.szypula.models.TrafficManagementSystem;

public class ControlPanelUI implements CreateUI{

    private Model model;
    private VBox vBox = new VBox(10);


    public ControlPanelUI(Model model) {
        this.model = model;
        CarParameters carParameters = TrafficManagementSystem.getCarParameters();
        VBox desiredSpeed = getSliderText("Desired Speed",1,30,carParameters.getDesiredSpeed(),carParameters.desiredSpeedProperty());
        VBox acceleration = getSliderText("Acceleration",0,20,carParameters.getAcceleration(),carParameters.accelerationProperty());
        VBox timegap = getSliderText("Time Gap", 0,10,carParameters.getTimeGap(),carParameters.timeGapProperty());
        VBox minimumGap = getSliderText("Minimum Gap",0,10,carParameters.getMinimumGap(),carParameters.minimumGapProperty());
        VBox deceleration = getSliderText("Deceleration",1,20,carParameters.getDeceleration(),carParameters.decelerationProperty());
        VBox accelerationExponent = getSliderText("Acceleration Exponent",1,10,carParameters.getAccelerationExponent(),carParameters.accelerationExponentProperty());
        VBox size = getSliderText("Size",1,10,carParameters.getSize(),carParameters.sizeProperty());
        VBox numberOfCars = getSliderText("Number Of Cars",0,600,model.getTrafficManagementSystem().getDesiredNumberOfCars(),model.getTrafficManagementSystem().desiredNumberOfCarsProperty());
        HBox carID = getDisplayLabel("Car",Highlighted.getHighlightedCar());
        HBox waveFrontID = getDisplayLabel("Wave",Highlighted.getHighlightedWaveFront());
        HBox roadID = getDisplayLabel("Road",Highlighted.getHighlightedRoad());
        HBox timeDisplay = getTimeDisplay();

//        Label labelId = new Label("Car ID : ");
//        TextField textFieldId = new TextField();
//        textFieldId.setPrefWidth(250);
//        HBox idBox = new HBox();
//        idBox.getChildren().addAll(labelId, textFieldId);
//        idBox.setSpacing(10);
//
//        Highlighted.isChangeCarProperty().addListener((observableValue, oldValue, newValue) -> {
//            if (newValue){
//                String id = Highlighted.getHighlightedCar().getId();
//                textFieldId.setText(id);
//            }
//            Highlighted.setIsChangeCar(false);
//        });

        vBox.getChildren().addAll(desiredSpeed,acceleration,timegap,minimumGap,deceleration,accelerationExponent,size,numberOfCars,carID,waveFrontID,roadID,timeDisplay);
    }

    private HBox getTimeDisplay() {
        Label labelId = new Label("Time" + " : ");
        TextField textField = new TextField();
        textField.setPrefWidth(250);
        HBox idBox = new HBox();
        idBox.getChildren().addAll(labelId, textField);
        idBox.setSpacing(10);
        textField.textProperty().bind(model.timeProperty().asString());
        return idBox;
    }

    public VBox getControlPane() {
        return vBox;
    }

    private VBox getSliderText(String name, double minValue, double maxValue, double defValue, DoubleProperty bindProperty){
        VBox sliderText = new VBox(10);
        Slider slider = new Slider(minValue,maxValue,defValue);
        Text text = new Text(name+" : "+defValue);

        slider.valueProperty().addListener((observableValue, number, t1) -> text.setText(name+" : "+String.format("%.2f",slider.getValue())));
        slider.setMaxWidth(Double.MAX_VALUE);
        bindProperty.bindBidirectional(slider.valueProperty());
        sliderText.getChildren().addAll(text,slider);

        return sliderText;
    }
    private VBox getSliderText(String name, int minValue, int maxValue, int defValue, IntegerProperty bindProperty){
        VBox sliderText = new VBox(10);
        Slider slider = new Slider(minValue,maxValue,defValue);
        Text text = new Text(name+" : "+defValue);

        slider.valueProperty().addListener((observableValue, number, t1) -> {
            slider.setValue(t1.intValue());
            text.setText(name+" : "+String.format("%d",(int)slider.getValue()));
        });
        slider.setMaxWidth(Double.MAX_VALUE);
        bindProperty.bind(slider.valueProperty());
        sliderText.getChildren().addAll(text,slider);

        return sliderText;
    }

    private HBox getDisplayLabel(String name, Highlightable highlightable){
        Label labelId = new Label(name + " : ");
        TextField textFieldId = new TextField();
        textFieldId.setPrefWidth(250);
        HBox idBox = new HBox();
        idBox.getChildren().addAll(labelId, textFieldId);
        idBox.setSpacing(10);

        highlightable.isChangeProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue){
                String id = ((UniqueId)highlightable.getHighlighted()).getId();
                textFieldId.setText(id);
            }
            highlightable.isChangeProperty().setValue(false);
        });

        return idBox;
    }

    @Override
    public void createUI(Group parent) {
        parent.getChildren().add(vBox);
    }

    @Override
    public void remove(Group parent) {
        parent.getChildren().remove(vBox);
    }
}
