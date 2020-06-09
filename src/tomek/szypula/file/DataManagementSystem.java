package tomek.szypula.file;

import tomek.szypula.controller.Updatable;
import tomek.szypula.models.Model;

import java.util.ArrayList;
import java.util.List;

public class DataManagementSystem implements Updatable {
    Model model;

    List<FileManager> fileManagers = new ArrayList<>();

    public DataManagementSystem(Model model) {
        this.model = model;
        fileManagers.add(new DistanceSpeedToOnRampFile(model));
        fileManagers.add(new PositionSpeedFile(model));
        fileManagers.add(new WaveFrontDistanceSpeedToOnRamp(model));
        fileManagers.add(new DistanceToStartOfRoadFile(model));
        fileManagers.add(new DensityFileToStartOfRoad(model));
    }

    //End User methods
    public void update() {
        for (Updatable file :
                fileManagers)
            file.update();
    }

    public List<FileManager> getFileManagers() {
        return fileManagers;
    }

    public void setModel(Model model) {
        this.model = model;
        for (FileManager fileManager :
                fileManagers) {
            fileManager.setModel(model);
        }
        
    }

}
