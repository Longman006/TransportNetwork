package tomek.szypula.file;

import tomek.szypula.models.Car;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

import java.util.List;

public class DistanceToStartOfRoadFile extends FileManager {

    public DistanceToStartOfRoadFile(Model model) {
        super("Time\tRoadID\tCarID\tDistanceToStart\tSpeed\n","DistanceToStartOfRoad.txt",model);
    }

    @Override
    void updateFile() {
        List<Car> cars = model.getTrafficManagementSystem().getCars();
        int time = model.getTime();
        double distance = 0;
        for (Road road :
                model.getTrafficManagementSystem().getRoadList()) {
            for (Car car :
                    road.getCarList()) {
                    distance = road.getDistanceToStart(car);
                    dataWriter.updateFile(String.valueOf(time), road.getId(),car.getId(), String.valueOf(distance), String.valueOf(car.getSpeed().getLength()));
            }
        }
    }
}
