package tomek.szypula.file;

import tomek.szypula.models.Car;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

import java.util.List;

public class DistanceSpeedToOnRampFile extends FileManager {

    public DistanceSpeedToOnRampFile(Model model) {
        super("Time\tCarID\tDistance\tSpeed\tOnRampID\n", "CarDistanceToOnRamp.txt",model);
    }


    @Override
    void updateFile() {
            List<Car> cars = model.getTrafficManagementSystem().getCars();
            int time = model.getTime();
            double distance = 0;
            for (Road onRamp :
                    model.getTrafficManagementSystem().getOnRamps()) {
                for (Car car :
                        cars) {
                    if (car.getDriver().getRoute().isOnRampOnRoute(onRamp)) {
                        distance = car.getDriver().getRoute().calculateDistanceToOnRamp(onRamp);
                        dataWriter.updateFile(time, car.getId(), String.valueOf(distance), String.valueOf(car.getSpeed().getLength()), onRamp.getId());
                    }
                }
            }
    }
}
