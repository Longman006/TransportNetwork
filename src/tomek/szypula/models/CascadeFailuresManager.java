package tomek.szypula.models;

import tomek.szypula.math.Dijkstra;
import tomek.szypula.math.Path;
import tomek.szypula.math.ShortestPaths;

import java.util.HashMap;
import java.util.Map.Entry;

public class CascadeFailuresManager {
    private final Model model;
    private final Dijkstra dijkstra;
    HashMap<Road, ShortestPaths> shortestPathsHashMap = new HashMap<>();
    HashMap<Road, Double> normalBetweennessHashMap = new HashMap<>();

    public CascadeFailuresManager(Model model) {
        this.model = model;
        dijkstra = new Dijkstra(model);
        calculateShortestPaths();
        calculateBetweenness();
    }

    private void calculateBetweenness() {
        int totalNumberOfShortestPaths = 0;
        double minBetweennes = Double.MAX_VALUE;
        double maxBetweennes = 0;
        /**
         * Initialize each value to zero
         */
        for (Road road :
                model.getRoadList()) {
            road.setBetweenness(0.0);
        }

        /**
         * Go through all the shortest paths and add to the appropriate bin
         */
        for (Entry<Road, ShortestPaths> entry :
                shortestPathsHashMap.entrySet()) {
            for (Path path:
            entry.getValue().getShortestPaths()) {
                for (Road roadOnPath :
                        path.getRoadsList()) {
                    roadOnPath.incrementBetweenness();
                    totalNumberOfShortestPaths++;
                }
            }
        }

        /**
         * Normalize betweennes
         */
        for (Road road :
                model.getRoadList()) {
            double betweenness = road.getBetweenness()/totalNumberOfShortestPaths; 
            if(minBetweennes > betweenness)
                minBetweennes = betweenness;
            if(maxBetweennes < betweenness)
                maxBetweennes = betweenness;
            
            road.setBetweenness(betweenness);
        }
        for (Road road :
                model.getRoadList()) {
            normalBetweennessHashMap.put(road, (road.getBetweenness() - minBetweennes)/(maxBetweennes - minBetweennes));
        }

    }

    private void calculateShortestPaths() {
        shortestPathsHashMap = dijkstra.getShortestPaths();
    }

    public HashMap<Road, ShortestPaths> getShortestPaths(){
        printShortestPaths();
        return shortestPathsHashMap;
    }

    private void printShortestPaths() {
        System.out.println("\nShortest Paths\n");
        for (Entry<Road, ShortestPaths> entry :
                shortestPathsHashMap.entrySet()) {
            System.out.println("Source Road : "+entry.getKey().getId()+"\n");
            for (Path path:
                 entry.getValue().getShortestPaths()) {
                System.out.println("Path to "+path.getEnd().getId());
                System.out.println(path.toString());
            }
        }
    }
}
