package tomek.szypula.file;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.controller.Updatable;
import tomek.szypula.models.Car;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

import java.io.File;
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
