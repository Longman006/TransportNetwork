package tomek.szypula.controller;

import tomek.szypula.models.Car;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class DataManagementSystem {
    Model model;

    public DataManagementSystem(Model model) {
        this.model = model;
    }

    public void updateFilePositionSpeed(String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filename,true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        int time = model.getTime();
        for (Road road :
                model.getRoadList()) {
            for (Car car :
                    road.getCarList()) {
                printWriter.printf("%f  %f  %f  %d\n", car.getPosition().getX(), car.getPosition().getY(),car.getSpeed().getLength(),time);
            }
        }
        printWriter.flush();
        printWriter.close();
        
    }
    public void updateFileWaveFronts(String filename) throws IOException {

    }
    //private void updateFile(String filename, int... integers, double... doubles)

}
