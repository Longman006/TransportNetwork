package tomek.szypula.models;

import java.awt.image.RasterOp;
import java.util.ArrayList;
import java.util.List;

public class Route {
    private List<Road> roadList = new ArrayList<>();

    public Route() {
    }
    public boolean addRoad(Road road){
        return roadList.add(road);
    }
    public boolean isLoop(){
        if (roadList.get(0).equals(roadList.get(roadList.size()-1)))
            return true;
        else
            return false;
    }

    /**
     * Should tak into account situations where the car might not be at the beginning of route, it could be a loop, and the same car could be the end car
     * and the next car could be on the same road but behind the current car
     * @param car
     * @param carNext
     * @return the distance along route
     */
    public double calculateDistanceAlongRoute(Car car, Car carNext){
        //TODO Take into account loop situation
        double distance = 0;
        boolean start = false;
        for (Road road : roadList){
            if(start){
                if (road.indexOf(carNext)<0)
                    distance+=road.getLength();
                else{
                    distance+=road.getDistanceToStart(car);
                    start=false;
                    break;
                }
            }
            if(road.indexOf(car) >= 0){
                start = true;
                distance+=road.getDistanceToEnd(car);
            }

        }
        return distance;
    }
    public Car getNextCarOnRoute(Car car){
        //TODO 
        return null;
    }
}
