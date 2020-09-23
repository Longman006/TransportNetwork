package tomek.szypula.file;

import tomek.szypula.models.Model;
import tomek.szypula.models.Wave;
import tomek.szypula.models.WaveFront;

public class SizeOfWavesFile extends FileManager {

    String sumWaveFrontSizeID = "00000000-0000-0000-0000-000000000000";

    public SizeOfWavesFile(Model model) {
        super("Time\tRoadID\tWaveFrontID\tSize\tSpeed\n", "WaveSizeSpeed", model);
    }

    @Override
    void updateFile() {
        int time = model.getTime();
        double sumSize = 0;
        for (Wave wave :
                model.getWaveFrontManager().getWaves()) {
            WaveFront waveFront = wave.getWaveFront();
            sumSize+=wave.getWaveSize();
            dataWriter.updateFile(
                    String.valueOf(time),
                    waveFront.getCurrentRoad().getId(),
                    waveFront.getId(),
                    String.valueOf(wave.getWaveSize()),
                    String.valueOf(waveFront.getSpeed().getLength())
            );
        }
        dataWriter.updateFile(
                String.valueOf(time),
                sumWaveFrontSizeID,
                sumWaveFrontSizeID,
                String.valueOf(sumSize),
                String.valueOf(0)
        );
    }

}
