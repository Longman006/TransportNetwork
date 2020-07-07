package tomek.szypula.file;

import tomek.szypula.models.Car;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;
import tomek.szypula.models.WaveFront;

import java.util.List;

public class WaveFrontDistanceSpeedToOnRamp extends FileManager{
    public WaveFrontDistanceSpeedToOnRamp(Model model) {
        super("Time\tWaveFrontID\tDistance\tSpeed\tOnRampID\n", "WaveFrontDistanceToOnRamp.txt", model);

    }

    @Override
    void updateFile() {
        List<WaveFront> waveFronts = model.getWaveFrontManager().getWaveFronts();
        int time = model.getTime();
        double distance = 0;
        for (Road onRamp :
                model.getTrafficManagementSystem().getOnRamps()) {
            for (WaveFront waveFront :
                    waveFronts) {
                Car car = waveFront.getCar();
                if (car.getDriver().getRoute().isOnRampOnRoute(onRamp)) {
                    distance = car.getDriver().getRoute().calculateDistanceToOnRamp(onRamp);
                    dataWriter.updateFile(String.valueOf(time), waveFront.getId(), String.valueOf(distance), String.valueOf(car.getSpeed().getLength()), onRamp.getId());
                }
            }
        }
    }
}
