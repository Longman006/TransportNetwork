package tomek.szypula.models;

import tomek.szypula.math.Vector2D;

import java.util.List;

public class RandomDriver extends Driver{
    public RandomDriver(Vector2D destination) {
        super(destination);
    }

    @Override
    public Road nextRoad(Road currentRoad) {

        List<Road> roads = currentRoad.getRoadList();
        int index = (int) (Math.random()*roads.size());
        Road nextRoad = roads.get(index);
        return nextRoad;

    }
}
