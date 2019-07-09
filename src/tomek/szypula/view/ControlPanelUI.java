package tomek.szypula.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tomek.szypula.controller.Highlighted;
import tomek.szypula.models.CarParameters;
import tomek.szypula.models.Model;
import tomek.szypula.models.TrafficManagementSystem;

import java.util.zip.GZIPOutputStream;

public class ControlPanelUI implements CreateUI{

    private Model model;
    private VBox vBox = new VBox(10);


    public ControlPanelUI(Model model) {
        this.model = model;
        CarParameters carParameters = TrafficManagementSystem.getCarParameters();
        VBox desiredSpeed = getSliderText("Desired Speed",1,30,11.5,carParameters.desiredSpeedProperty());
        VBox acceleration = getSliderText("Acceleration",1,20,1.5,carParameters.accelerationProperty());
        VBox timegap = getSliderText("Time Gap", 0,10,1,carParameters.timeGapProperty());
        VBox minimumGap = getSliderText("Minimum Gap",0,10,2,carParameters.minimumGapProperty());
        VBox deceleration = getSliderText("Deceleration",1,20,1.3,carParameters.decelerationProperty());
        VBox accelerationExponent = getSliderText("Acceleration Exponent",1,10,4.0,carParameters.accelerationExponentProperty());
        VBox size = getSliderText("Size",1,10,3,carParameters.sizeProperty());
        VBox numberOfCars = getSliderText("Number Of Cars",0,300,model.getTrafficManagementSystem().getNumberOfCars(),model.getTrafficManagementSystem().numberOfCarsProperty());
        Text text = new Text("Speed of Car : 0.0");

        vBox.getChildren().addAll(desiredSpeed,acceleration,timegap,minimumGap,deceleration,accelerationExponent,size,numberOfCars,text);
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

    @Override
    public void createUI(Group parent) {
        parent.getChildren().add(vBox);
    }

    @Override
    public void remove(Group parent) {
        parent.getChildren().remove(vBox);
    }
}
