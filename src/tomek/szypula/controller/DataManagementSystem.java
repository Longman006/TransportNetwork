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

    //Properties
    BooleanProperty positionSpeedFile = new SimpleBooleanProperty(false);
    BooleanProperty distanceSpeedFile = new SimpleBooleanProperty(false);

    public BooleanProperty distanceSpeedFileProperty() { return distanceSpeedFile; }
    public BooleanProperty positionSpeedFileProperty() {
        return positionSpeedFile;
    }

    public boolean isPositionSpeedFile() { return positionSpeedFile.get(); }
    public boolean isDistanceSpeedFile() { return distanceSpeedFile.get(); }

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
        writerPositionSpeed = new DataWriter(positionSpeedHeader,filenamePositionSpeed);

    }

    //t x y v
    private void updateFilePositionSpeed() {
        int time = model.getTime();
        for (Road road :
                model.getRoadList()) {
            for (Car car :
                    road.getCarList()) {
                try {
                    writerPositionSpeed.updateFile(time, car.getPosition().getX(), car.getPosition().getY(), car.getSpeed().getLength());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //End User methods
    public void update(){
        if (isPositionSpeedFile()) 
            updateFilePositionSpeed();
        if (isDistanceSpeedFile())
            updateFileOnRampDistanceSpeed();
    }
    public void setModel(Model model) {
        this.model = model;
    }

    
    //This will be called only whenever the user decides to save to file through the UI. After that no changes to the OnRamps will take place.
    //Maybe I could try and freeze the UI onRamp sign during save. TOTHINK
    private void setupDistanceSpeedOnRampFiles(){
        List<Road> onRamps = new ArrayList<>();
        for (Road road : model.getRoadList()) {
            if (road.getOnRamp().isOnRamp())
                onRamps.add(road);
                datawritersOnRamp.add(new DataWriter(distanceToOnRampSpeedHeader,filenameDistanceSpeed));
        }
    }

    //t d v
    public void updateFileOnRampDistanceSpeed(){
        //TODO Need to think about implementation. How to best do this in order to avoid duplicate code. Also the problem of multiple OnRamps.
        //Multiple OnRamps solved. Just run calculateDistanceToOnRamp on each on Ramp for each car. Create a seperate file for each onRamp
        //In terms of duplicate code. I think I can go ahead and just implement a method from scratch for this specific scenario.
        //Probably could have created a more generic method to save. Maybe think later. TOTHINK
        for (:
             ) {
            
        }

    }

}
