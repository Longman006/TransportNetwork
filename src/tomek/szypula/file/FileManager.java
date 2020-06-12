package tomek.szypula.file;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.controller.Updatable;
import tomek.szypula.deprecated.DataWriter;
import tomek.szypula.models.Model;

public abstract class FileManager implements Updatable {
    BooleanProperty saveToFile = new SimpleBooleanProperty();

    String fileNameSuffix;
    String fileHeader;
    DataWriter dataWriter;
    Model model;

    abstract void updateFile();

    FileManager(String fileHeader, String fileNameSuffix,Model model){
        this.fileHeader = fileHeader;
        this.fileNameSuffix = fileNameSuffix;
        this.model = model;

        saveToFileProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue)
                setupFile();
        });

    }

    public boolean isSaveToFile() {
        return saveToFile.get();
    }

    public BooleanProperty saveToFileProperty() {
        return saveToFile;
    }

    public String getFileNameSuffix() {
        return fileNameSuffix;
    }

    public String getFileHeader() {
        return fileHeader;
    }

    void setupFile() {
        dataWriter = new DataWriter(getFileHeader(), getFileNameSuffix());
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void update() {
        if (isSaveToFile())
            updateFile();
    }
}
