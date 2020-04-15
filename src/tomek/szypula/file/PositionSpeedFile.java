package tomek.szypula.file;

import tomek.szypula.models.Car;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

public class PositionSpeedFile extends FileManager {

    Model model;
    DataWriter dataWriter;

    PositionSpeedFile(Model model){
        super("xyv.txt", "t\tid\tx\ty\tv\n");
        this.model = model;
    }

    @Override
    void setupFile() {
        dataWriter = new DataWriter(getFileHeader(), getFileNameSuffix());
    }

    @Override
    void updateFile() {
        int time = model.getTime();
        for (Road road :
                model.getRoadList()) {
            for (Car car :
                    road.getCarList()) {
                dataWriter.updateFile(time,car.getId(), String.valueOf(car.getPosition().getX()), String.valueOf(car.getPosition().getY()), String.valueOf(car.getSpeed().getLength()));
            }
        }
    }
}
