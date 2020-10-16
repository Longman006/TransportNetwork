package tomek.szypula.math;

import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Dijkstra {

    private final Model model;

    public Dijkstra(Model model){
        this.model = model;
    }

    public HashMap<Road,ShortestPaths> getShortestPaths(){
        HashMap<Road,ShortestPaths> shortestPathsHashMap = new HashMap<>();

        for (Road source :
                model.getRoadList()) {
            ShortestPaths shortestPaths = calculateShortestPathsFromSource(source);
            shortestPathsHashMap.put(source,shortestPaths);
        }

        return shortestPathsHashMap;
    }

    private ShortestPaths calculateShortestPathsFromSource(Road source) {
        Set<Road> settledRoads = new HashSet<>();
        Set<Road> unsettledRoads = new HashSet<>();

        ShortestPaths shortestPathsFromSource = new ShortestPaths(source);
        shortestPathsFromSource.addPath();
        unsettledRoads.add(source);

        while (unsettledRoads.size() != 0){
            Road currentRoad = getLowestDistanceRoad(unsettledRoads,shortestPathsFromSource);
            unsettledRoads.remove(currentRoad);

            for (Road roadToEvaluate :
                    currentRoad.getRoadList()) {
                if (!settledRoads.contains(roadToEvaluate)){
                    calculateShortestDistanceBetweenRoads(currentRoad, roadToEvaluate,shortestPathsFromSource);
                    unsettledRoads.add(roadToEvaluate);
                }
            }
            settledRoads.add(currentRoad);
        }

        return shortestPathsFromSource;
    }

    private Road getLowestDistanceRoad(Set<Road> unsettledRoads, ShortestPaths shortestPathsFromSource) {
        Road lowestDistanceRoad = null;
        double lowestDistance = Double.MAX_VALUE;
        for (Road road :
                unsettledRoads) {
            double currentDistance = shortestPathsFromSource.getShortestPathEndingOnRoad(road).getLength();
            if ( currentDistance < lowestDistance){
                lowestDistance = currentDistance;
                lowestDistanceRoad = road;
            }
        }
        return lowestDistanceRoad;
    }

    private void calculateShortestDistanceBetweenRoads(Road previousRoad, Road roadToEvaluate, ShortestPaths shortestPathsFromSource) {
        Path shortestPathToPreviousRoad = shortestPathsFromSource.getShortestPathEndingOnRoad(previousRoad);
        double distanceToPreviousRoad = shortestPathToPreviousRoad.getLength();

        Path shortestPathToEvaluationRoad = shortestPathsFromSource.getShortestPathEndingOnRoad(roadToEvaluate);
        double distanceToEvaluationRoad;
        if (shortestPathToEvaluationRoad == null){
            shortestPathToEvaluationRoad = shortestPathsFromSource.addPath();
            distanceToEvaluationRoad = Double.MAX_VALUE;
        }
        else {
            distanceToEvaluationRoad = shortestPathToEvaluationRoad.getLength();
        }

        if (distanceToPreviousRoad + previousRoad.getLength() < distanceToEvaluationRoad){
            shortestPathToEvaluationRoad.setToPath(shortestPathToPreviousRoad);
            shortestPathToEvaluationRoad.addRoad(roadToEvaluate);

        }
    }

}
