package tomek.szypula.controller;

import tomek.szypula.models.Car;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataManagementSystem {
    Model model;
    PrintWriter printWriterPositionSpeed;

    public DataManagementSystem(Model model) {
        //The date and time format for file naming
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        //filenames
        String filenamePositionSpeed = dateFormatter.format(new Date()).concat("xyv.txt");

        this.model = model;
        try {
            printWriterPositionSpeed = new PrintWriter(new FileWriter("TestFile.txt",false),true);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    //TODO Use Stringbuilder to concat doubles directly! No need for extra formatting. This is way over the top
        // StringBuilder creating the format string for printf to file
        StringBuilder builder = new StringBuilder();
        for(double item : doubles) {
            builder.append("%f\t");
        }
        builder.append("%d\n");
        String stringFormat = builder.toString();
        printWriter.printf(stringFormat, doubles,time);
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
