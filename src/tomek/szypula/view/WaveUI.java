package tomek.szypula.view;

import javafx.scene.Group;
import tomek.szypula.models.Wave;

public class WaveUI implements CreateUI {

    Wave wave;
    WaveFrontUI waveFrontUI,waveEndUI;

    WaveUI(Wave wave){
        this.wave = wave;
        waveEndUI = new WaveFrontUI(wave.getWaveEnd(),wave.waveSizeProperty());
        waveFrontUI = new WaveFrontUI(wave.getWaveFront(),wave.waveSizeProperty());

    }

    @Override
    public void createUI(Group parent) {
        waveEndUI.createUI(parent);
        waveFrontUI.createUI(parent);

    }
    @Override
    public void remove(Group parent) {
        waveFrontUI.remove(parent);
        waveEndUI.remove(parent);
    }

    public Wave getWave() {
        return wave;
    }
}
