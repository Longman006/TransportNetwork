package tomek.szypula.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.models.Car;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManagementSystem {
    Model model;
    //Properties
    BooleanProperty positionSpeedFile = new SimpleBooleanProperty(false);

    public boolean isPositionSpeedFile() {
        return positionSpeedFile.get();
    }

    public BooleanProperty positionSpeedFileProperty() {
        return positionSpeedFile;
    }

    public void setPositionSpeedFile(boolean positionSpeedFile) {
        this.positionSpeedFile.set(positionSpeedFile);
    }

    //Printwriters
    PrintWriter printWriterPositionSpeed;
    List<PrintWriter> printWritersOnRamp = new ArrayList<>();


    //filenames, formatters, headers
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss");
    String positionSpeedHeader = "t\tx\ty\tv\n";
    String distanceToOnRampSpeedHeader = "t\td\tv\t\n";

    String filenamePositionSpeed = "xyv.txt";

    public DataManagementSystem(Model model) {
        this.model = model;
        setupPositionSpeedFile();
    }
    //Set up the formatter and the header of the file, clear file if it exists, autoflush set to true to avoid extra flushing at every update
    private void setupFile(PrintWriter printWriter, String dataType, String header){
        String filename = dateFormatter.format(new Date()).concat(dataType);
        try {
            printWriter = new PrintWriter(new FileWriter(filename,false),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.printf(header);
    }
    private void setupPositionSpeedFile(){
        setupFile(printWriterPositionSpeed,filenamePositionSpeed,positionSpeedHeader);
    }
    private void setupDistanceSpeedOnRampFile(Road onRamp){
        //setupFile();

    }

    //The actual implementation of every updatefile type method
    private void updateFile(PrintWriter printWriter,int time, double... doubles) throws IOException {
        // StringBuilder creating the string for printf
        StringBuilder builder = new StringBuilder();
        builder.append(time);
        builder.append("\t");
        for(double item : doubles) {
            builder.append(item);
            builder.append("\t");
        }
        builder.append("\n");
        printWriter.printf(builder.toString());
    }
    public void setModel(Model model) {
        this.model = model;
    }

    //End User methods

    //t x y v
    public void updateFilePositionSpeed() throws IOException {
        int time = model.getTime();
        for (Road road :
                model.getRoadList()) {
            for (Car car :
                    road.getCarList()) {
                updateFile(printWriterPositionSpeed,time, car.getPosition().getX(), car.getPosition().getY(),car.getSpeed().getLength());
            }
        }
    }
    //This will be called only whenever the user decides to save to file through the UI. After that no changes to the OnRamps will take place.
    //Maybe I could try and freeze the UI onRamp sign during save. TOTHINK
    public void setupDistanceSpeedOnRampFiles(onRamps){
        List<Road> onRamps = new ArrayList<>();
        for (Road road : model.getRoadList()) {
            if (road.getOnRamp().isOnRamp())
                onRamps.add(road);
        }
        distanceToOnRampSpeedFilenames
    }
    //t d v
    public updateFileOnRampDistanceSpeed(){
        //TODO Need to think about implementation. How to best do this in order to avoid duplicate code. Also the problem of multiple OnRamps.
        //Multiple OnRamps solved. Just run calculateDistanceToOnRamp on each on Ramp for each car. Create a seperate file for each onRamp
        //In terms of duplicate code. I think I can go ahead and just implement a method from scratch for this specific scenario.
        //Probably could have created a more generic method to save. Maybe think later. TOTHINK




    }

}
