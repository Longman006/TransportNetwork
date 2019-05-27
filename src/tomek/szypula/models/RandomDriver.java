package tomek.szypula.models;

import tomek.szypula.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class RandomDriver extends Driver{
    public RandomDriver(Vector2D destination,Road startingRoad,Car car) {
        super(destination,startingRoad,car);
    }

    @Override
    protected Road nextRoad(Road currentRoad) {
        if (currentRoad == null){
            return null;
        }
        List<Road> roads =  new ArrayList<>(currentRoad.getRoadList());
        int index = (int) (Math.random()*roads.size());
        Road nextRoad = roads.remove(index);
        while(nextRoad.getOutOfService().isOutOfService()){
            if (roads.size()==0)
                return null;
            index = (int) (Math.random()*roads.size());
            nextRoad = roads.remove(index);
        }
        return nextRoad;

    }
}
