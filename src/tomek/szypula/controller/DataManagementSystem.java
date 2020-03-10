package tomek.szypula.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.models.Car;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

import javax.xml.crypto.Data;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManagementSystem {
    Model model;
    List<Road> onRamps = new ArrayList<>();

    //Properties
    BooleanProperty positionSpeedFile = new SimpleBooleanProperty(false);
    BooleanProperty distanceSpeedFile = new SimpleBooleanProperty(false);

    public BooleanProperty distanceSpeedFileProperty() {
        return distanceSpeedFile;
    }

    public BooleanProperty positionSpeedFileProperty() {
        return positionSpeedFile;
    }

    public boolean isPositionSpeedFile() {
        return positionSpeedFile.get();
    }

    public boolean isDistanceSpeedFile() {
        return distanceSpeedFile.get();
    }

    //DataWriters
    List<DataWriter> datawritersOnRamp = new ArrayList<>();
    DataWriter writerPositionSpeed;

    //headers
    String positionSpeedHeader = "t\tx\ty\tv\n";
    String distanceToOnRampSpeedHeader = "t\td\tv\t\n";

    //filenames
    String filenamePositionSpeed = "xyv.txt";
    String filenameDistanceSpeed = "dv.txt";

    public DataManagementSystem(Model model) {
        this.model = model;
        writerPositionSpeed = new DataWriter(positionSpeedHeader, filenamePositionSpeed);
        distanceSpeedFileProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue)
                setupDistanceSpeedOnRampFiles();
        });

    }

    //t x y v
    private void updateFilePositionSpeed() {
        int time = model.getTime();
        for (Road road :
                model.getRoadList()) {
            for (Car car :
                    road.getCarList()) {
                writerPositionSpeed.updateFile(time, car.getPosition().getX(), car.getPosition().getY(), car.getSpeed().getLength());
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
                    currentDataWriter.updateFile(time, distance, car.getSpeed().getLength());
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
