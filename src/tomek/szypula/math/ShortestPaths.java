package tomek.szypula.math;

import tomek.szypula.models.Road;

import java.util.ArrayList;
import java.util.List;

public class ShortestPaths {
    List<Path> shortestPaths = new ArrayList<>();
    Road sourceRoad;

    ShortestPaths(Road sourceRoad){
        this.sourceRoad = sourceRoad;
    }

    public Path addPath(){
        Path path = new Path(sourceRoad);
        shortestPaths.add(path);
        return path;
    }
    public Path addPath(Path path){
        shortestPaths.add(path);
        return path;
    }

    public Path getShortestPathEndingOnRoad(Road source) {
        for (Path path :
                shortestPaths) {
            if (path.getEnd().equals(source))
                return path;
        }
        return null;
    }

    public List<Path> getShortestPaths() {
        return shortestPaths;
    }
}
