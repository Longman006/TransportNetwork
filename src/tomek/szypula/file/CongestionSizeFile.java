package tomek.szypula.file;

import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

public class CongestionSizeFile extends FileManager{

        String sumRoadID = "00000000-0000-0000-0000-000000000000";

        public CongestionSizeFile(Model model) {
            super("Time\tRoadID\tSize\tSizeNormalized\n", "CongestionSizeOnRoads", model);
        }

        @Override
        void updateFile() {
            int time = model.getTime();
            int sumSize = 0;
            int sumMax = 0;
            int localSize = 0;
            double localSizeNormalized = 0;
            for (Road road :
                    model.getRoadList()) {
                localSize = road.getTotalCongestionSizeOnRoad();
                localSizeNormalized = road.getTotalCongestionSizeOnRoadNormalized();
                sumSize+=localSize;
                sumMax+=road.getMaxNumberOfCars();
                dataWriter.updateFile(
                        String.valueOf(time),
                        road.getId(),
                        String.valueOf(localSize),
                        String.valueOf(localSizeNormalized)
                );
            }
            dataWriter.updateFile(
                    String.valueOf(time),
                    sumRoadID,
                    String.valueOf(sumSize),
                    String.valueOf((double)sumSize/sumMax)
            );
        }

}
