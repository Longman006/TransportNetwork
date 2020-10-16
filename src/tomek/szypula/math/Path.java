package tomek.szypula.math;

import tomek.szypula.models.Road;

import java.util.ArrayList;
import java.util.List;

public class Path {

    List<Road> roadsList = new ArrayList<>();
    Road source, end;
    double length = 0;

    Path(Road sourceRoad){
        roadsList.add(sourceRoad);
        this.source = sourceRoad;
        this.end = sourceRoad;
    }
    Path(Path path){
        roadsList = new ArrayList<>(path.getRoadsList());
        this.source = path.getSource();
        this.end = path.getEnd();
    }

    public Road getSource() {
        return source;
    }

    public Road getEnd() {
        return end;
    }

    public void addRoad(Road road){
        length+=end.getLength();
        roadsList.add(road);
        end=road;
    }

    public double getLength() {
        return length;
    }

    public List<Road> getRoadsList() {
        return roadsList;
    }

    public Path setToPath(Path path) {
        this.roadsList = new ArrayList<>(path.getRoadsList());
        this.source = path.getSource();
        this.end = path.getEnd();
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Road road :
                getRoadsList()) {
            builder.append(" -> ");
            builder.append(road.getId());

        }
        builder.append("\n");
        return builder.toString();
    }
}
