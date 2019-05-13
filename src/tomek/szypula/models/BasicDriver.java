package tomek.szypula.models;

import javafx.beans.property.DoubleProperty;
import tomek.szypula.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class BasicDriver extends Driver {

    public BasicDriver(Vector2D destination, Road startingRoad, Car car) {
        super(destination,startingRoad,car);
    }

    @Override
    public Road nextRoad(Road currentRoad) {
        List<Road> roadList = currentRoad.getRoadList();
        if(roadList.isEmpty()){
            return null;
        }
        else if (roadList.size() == 1){
            return roadList.get(0);
        }
        else {
            double shortestDistance = Double.MAX_VALUE;
            int indexOfShortestDistance = -1;
            for (Road road : roadList) {
                double distance = road.getLineSegment().getShortestDistanceToPointSquared(getDestination());
                if (distance < shortestDistance){
                    shortestDistance = distance;
                    indexOfShortestDistance = roadList.indexOf(road);
                }
            }
            return roadList.get(indexOfShortestDistance);
        }
    }
}
