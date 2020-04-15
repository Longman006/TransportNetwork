package tomek.szypula.file;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.controller.Updatable;

public abstract class FileManager implements Updatable {
    BooleanProperty saveToFile = new SimpleBooleanProperty();

    String fileNameSuffix;
    String fileHeader;

    abstract void setupFile();
    abstract void updateFile();

    FileManager(String fileHeader, String fileNameSuffix){
        this.fileHeader = fileHeader;
        this.fileNameSuffix = fileNameSuffix;

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

    public void setSaveToFile(boolean saveToFile) {
        this.saveToFile.set(saveToFile);
    }

    public String getFileNameSuffix() {
        return fileNameSuffix;
    }

    public String getFileHeader() {
        return fileHeader;
    }

    @Override
    public void update() {
        if (isSaveToFile())
            updateFile();
    }
}
