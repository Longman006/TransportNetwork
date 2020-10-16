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

    public CascadeFailuresManager(Model model) {
        this.model = model;
        dijkstra = new Dijkstra(model);
    }
    
    public HashMap<Road, ShortestPaths> getShortestPaths(){ 
        shortestPathsHashMap = dijkstra.getShortestPaths();
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
