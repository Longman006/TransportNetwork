package tomek.szypula.file;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.controller.Updatable;
import tomek.szypula.models.Car;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

import java.util.ArrayList;
import java.util.List;

public class DataManagementSystem implements Updatable {
    Model model;
    List<Road> onRamps = new ArrayList<>();


    PositionSpeedFile positionSpeedFile;
    List<DistanceSpeedToOnRampFile> distanceSpeedToOnRampFileList

    public DataManagementSystem(Model model) {
        positionSpeedFile = new PositionSpeedFile(model);
        distanceSpeedToOnRampFileList = new ArrayList<>();
        for (Road:
             road) {
        }

    }

    //t x y v
    private void updateFilePositionSpeed() {
        int time = model.getTime();
        for (Road road :
                model.getRoadList()) {
            for (Car car :
                    road.getCarList()) {
                writerPositionSpeed.updateFile(time,car.getId(), String.valueOf(car.getPosition().getX()), String.valueOf(car.getPosition().getY()), String.valueOf(car.getSpeed().getLength()));
            }
        }
    }

    //This will be called only whenever the user decides to save to file through the UI. After that no changes to the OnRamps will take place.
    private void setupDistanceSpeedOnRampFiles() {
        onRamps = new ArrayList<>();
        datawritersOnRamp = new ArrayList<>();
        for (Road road : model.getRoadList()) {
            if (road.getOnRamp().isOnRamp()) {
                StringBuilder filename = new StringBuilder();
                filename.append(road.identifier());
                filename.append(filenameDistanceSpeed);
                onRamps.add(road);
                datawritersOnRamp.add(new DataWriter(distanceToOnRampSpeedHeader, filename.toString()));
            }
        }
    }

    //t d v
    private void updateFileOnRampDistanceSpeed() {
        //Multiple OnRamps solved. Just run calculateDistanceToOnRamp on each on Ramp for each car. Create a seperate file for each onRamp
        //In terms of duplicate code. I think I can go ahead and just implement a method from scratch for this specific scenario.
        //Probably could have created a more generic method to save. Maybe think later. TOTHINK
        List<Car> cars = model.getTrafficManagementSystem().getCarList();
        int time = model.getTime();
        Road currentOnRamp;
        DataWriter currentDataWriter;
        double distance = 0;
        for (int i = 0; i < onRamps.size(); i++) {
            currentDataWriter = datawritersOnRamp.get(i);
            currentOnRamp = onRamps.get(i);
            for (Car car :
                    cars) {
                if (car.getDriver().getRoute().isOnRampOnRoute(currentOnRamp)) {
                    distance = car.getDriver().getRoute().calculateDistanceToOnRamp(currentOnRamp);
                    currentDataWriter.updateFile(time,car.getId(), String.valueOf(distance), String.valueOf(car.getSpeed().getLength()));
                }
            }
        }
    }

    //End User methods
    public void update() {
        if (isPositionSpeedFile())
            updateFilePositionSpeed();
        if (isDistanceSpeedFile())
            updateFileOnRampDistanceSpeed();
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
