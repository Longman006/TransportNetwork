package tomek.szypula.models;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class DataManagementSystem {
    Model model;

    public DataManagementSystem(Model model) {
        this.model = model;
    }

    public void updateFile(String filename) throws IOException {
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

}
