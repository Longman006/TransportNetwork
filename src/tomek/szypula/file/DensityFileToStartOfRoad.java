package tomek.szypula.file;

import tomek.szypula.models.Car;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

public class DensityFileToStartOfRoad extends FileManager {

    public DensityFileToStartOfRoad(Model model) {
        super("Time\tRoadID\tCarID\tDistance\tDensity\tSpeed\n", "DensitySpeedToStartOfRoad", model);
    }

    @Override
    void updateFile() {
        int time = model.getTime();
        double distance =0;
        for (Road road :
                model.getRoadList()) {
            for (Car car :
                    road.getCarList()) {
                distance = road.getDistanceToStart(car);
                dataWriter.updateFile(time,road.getId(),car.getId(),String.valueOf(distance),String.valueOf(car.getSpeedDensityIndex()), String.valueOf(car.getSpeed().getLength()));
            }
        }
    }
}
