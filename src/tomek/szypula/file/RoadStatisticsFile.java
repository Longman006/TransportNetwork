package tomek.szypula.file;

import tomek.szypula.models.Model;
import tomek.szypula.models.Road;
import tomek.szypula.models.TrafficManagementSystem;

public class RoadStatisticsFile extends FileManager {
    String sumRoadID = "00000000-0000-0000-0000-000000000000";

    public RoadStatisticsFile(Model model) {
        super("Time\tRoadID\tAverageSpeed\tAverageTime\tAverageSpeedNormalized\tAverageTimeNormalized\n", "RoadStatisticsFile", model);
    }

    @Override
    void updateFile() {
        int time = model.getTime();
        double roadLength = 0;
        double maxSpeed = TrafficManagementSystem.getCarParameters().getDesiredSpeed();
        double minTime = 0;
        int numberOfCars = 0;
        int totalNumberOfCars = 0;

        double totalAverageTime = 0;
        double totalAverageSpeed = 0;
        double totalAverageTimeNormalized = 0;
        double totalAverageSpeedNormalized =0;

        double localAverageTime = 0;
        double localAverageSpeed = 0;
        double localAverageSpeedNormalized = 0;
        double localAverageTimeNormalized = 0;

        for (Road road :
                model.getRoadList()) {
            numberOfCars = road.getCarList().size();
            totalNumberOfCars+=numberOfCars;
            roadLength = road.getLength();
            minTime = roadLength/maxSpeed;

            localAverageSpeed = road.getAverageSpeedOnRoad();
            localAverageTime = roadLength/localAverageSpeed;
            System.out.println("local average time : "+localAverageTime);
            localAverageSpeedNormalized = localAverageSpeed/maxSpeed;
            localAverageTimeNormalized = localAverageTime/minTime;

            dataWriter.updateFile(
                    String.valueOf(time),
                    road.getId(),
                    String.valueOf(localAverageSpeed),
                    String.valueOf(localAverageTime),
                    String.valueOf(localAverageSpeedNormalized),
                    String.valueOf(localAverageTimeNormalized)
            );
            totalAverageSpeed+=localAverageSpeed*numberOfCars;
            totalAverageTime+=localAverageTime*numberOfCars;
            totalAverageTimeNormalized+=totalAverageTime*numberOfCars/minTime;
        }
        
        if (totalNumberOfCars == 0)
            return;
        totalAverageSpeed=totalAverageSpeed/totalNumberOfCars;
        totalAverageTime=totalAverageTime/totalNumberOfCars;
        totalAverageSpeedNormalized=totalAverageSpeed/maxSpeed;

        dataWriter.updateFile(
                String.valueOf(time),
                sumRoadID,
                String.valueOf(totalAverageSpeed),
                String.valueOf(totalAverageTime),
                String.valueOf(totalAverageSpeedNormalized),
                String.valueOf(totalAverageTimeNormalized)
        );

    }

}
