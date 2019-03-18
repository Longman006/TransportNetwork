package tomek.szypula.models;

import tomek.szypula.math.Line;
import tomek.szypula.math.LineSegment;

import java.util.List;

public class Road {
    /**
     * The physical representation of the road
     */
    LineSegment lineSegment;
    /**
     * List of cars on the road from beginning to end
     */
    List<Car> carList;
    /**
     * List of possible roads at junction
     */
    List<Road> roadList;
    TrafficLights trafficLightsBeginning;
    TrafficLights trafficLightsEnd;

    public Road(LineSegment lineSegment) {
        this.lineSegment = lineSegment;
    }

    
}
