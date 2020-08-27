package tomek.szypula.file;

import tomek.szypula.models.*;

import java.util.List;

public class WaveFrontDistanceSpeedToOnRamp extends FileManager{
    public WaveFrontDistanceSpeedToOnRamp(Model model) {
        super("Time\tWaveFrontID\tDistance\tSpeed\tOnRampID\n", "WaveFrontDistanceToOnRamp.txt", model);

    }

    @Override
    void updateFile() {
        List<Wave> waves = model.getWaveFrontManager().getWaves();
        int time = model.getTime();
        double distance = 0;
        for (Road onRamp :
                model.getTrafficManagementSystem().getOnRamps()) {
            for (Wave wave :
                    waves) {
                for (WaveFront waveFront :
                        wave.getWaveFrontList()) {
                    Car carFront = waveFront.getCar();
                    if (carFront.getDriver().getRoute().isOnRampOnRoute(onRamp)) {
                        distance = carFront.getDriver().getRoute().calculateDistanceToOnRamp(onRamp);
                        dataWriter.updateFile(String.valueOf(time), waveFront.getId(), String.valueOf(distance), String.valueOf(carFront.getSpeed().getLength()), onRamp.getId());
                    }
                }
            }
        }
    }
}
