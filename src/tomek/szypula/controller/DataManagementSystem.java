package tomek.szypula.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.models.Car;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    //filenames, formatters
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss");
    String positionSpeedHeader = "t\tx\ty\tv\n";

    //

    public DataManagementSystem(Model model) {
        this.model = model;
        setupPositionSpeedFile();
    }
    private void setupPositionSpeedFile(){
        String filenamePositionSpeed = dateFormatter.format(new Date()).concat("xyv.txt");
        try {
            printWriterPositionSpeed = new PrintWriter(new FileWriter(filenamePositionSpeed,false),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriterPositionSpeed.printf(positionSpeedHeader);

    }

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
    public void updateFileWaveFronts(String filename) throws IOException {

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
}
