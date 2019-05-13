package tomek.szypula.models;

import tomek.szypula.math.Vector2D;

import java.util.List;

public class RandomDriver extends Driver{
    public RandomDriver(Vector2D destination,Road startingRoad,Car car) {
        super(destination,startingRoad,car);
    }

    @Override
    protected Road nextRoad(Road currentRoad) {
        List<Road> roads = currentRoad.getRoadList();
        if (roads.size() == 1)
            return roads.get(0);
        int index = (int) (Math.random()*roads.size());
        Road nextRoad = roads.get(index);
        return nextRoad;

    }
}
