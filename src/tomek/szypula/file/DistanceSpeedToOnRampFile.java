package tomek.szypula.file;

import tomek.szypula.models.Car;
import tomek.szypula.models.Model;
import tomek.szypula.models.OnRamp;
import tomek.szypula.models.Road;

import java.util.ArrayList;
import java.util.List;

public class DistanceSpeedToOnRampFile extends FileManager {

    List<Road> onRamps = new ArrayList<>();
    Model model;
    Road onRamp;
    public DistanceSpeedToOnRampFile(Model model, Road onRamp) {
        super("t\tid\td\tv\t\n", onRamp.getId()+"dv.txt");
        this.model = model;
        this.onRamp = onRamp;
    }

    @Override
    void setupFile() {
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

    @Override
    void updateFile() {
        if (onRamp.getOnRamp().isOnRamp()) {
            List<Car> cars = model.getTrafficManagementSystem().getCarList();
            int time = model.getTime();
            double distance = 0;
            for (Car car :
                    cars) {
                if (car.getDriver().getRoute().isOnRampOnRoute(onRamp)) {
                    distance = car.getDriver().getRoute().calculateDistanceToOnRamp(onRamp);
                    dataWriter.updateFile(time, car.getId(), String.valueOf(distance), String.valueOf(car.getSpeed().getLength()));
                }
            }
        }
    }
}
