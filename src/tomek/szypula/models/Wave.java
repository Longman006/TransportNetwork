package tomek.szypula.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import tomek.szypula.controller.UniqueId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wave implements UniqueId {

    IntegerProperty waveSize = new SimpleIntegerProperty();
    UUID uuid;

    WaveFront waveFront;
    WaveFront waveEnd;

    List<WaveFront> waveFrontList = new ArrayList<>();

    public List<WaveFront> getWaveFrontList() {
        return waveFrontList;
    }

    public Wave(Car car, Road road) {
        waveFront = new WaveFront(car,road);
        waveEnd = new WaveFront(car,road);
        waveFrontList.add(waveFront);
        waveFrontList.add(waveEnd);
        uuid = UUID.randomUUID();
    }

    public WaveFront getWaveFront() {
        return waveFront;
    }

    public WaveFront getWaveEnd() {
        return waveEnd;
    }
    public void setWaveFronts(Wave newWave){
        this.getWaveFront().setCar(newWave.getWaveFront().getCar(), newWave.getWaveFront().getCurrentRoad());
        this.getWaveEnd().setCar(newWave.getWaveEnd().getCar(),newWave.getWaveEnd().getCurrentRoad());
        this.setWaveSize(newWave.getWaveSize());
    }

    public int getWaveSize() {
        return waveSize.get();
    }

    public IntegerProperty waveSizeProperty() {
        return waveSize;
    }

    public void setWaveSize(int waveSize) {
        this.waveSize.set(waveSize);
    }

    public void incrementWaveSize() {
        this.waveSize.setValue(this.waveSize.get()+1);
    }
    @Override
    public String getId() {
        return uuid.toString();
    }

}
