package tomek.szypula.models;

import tomek.szypula.math.Edge;
import tomek.szypula.math.Node;
import tomek.szypula.math.Vector2D;

import java.util.*;

public class GraphManager {

    Set<Edge> edges = new HashSet<>();
    HashMap<Vector2D, Node> nodes = new HashMap<>();

    public void createGraphFromRoadList(List<Road> roadList){
        Set<Vector2D> nodePositions = new HashSet<>();
        edges = new HashSet<>();
        nodes = new HashMap<>();

        for (Road road :
                roadList) {
            nodePositions.add(road.getStart());
            nodePositions.add(road.getEnd());
        }

        for (Vector2D nodePosition:
             nodePositions) {
            nodes.put(nodePosition, new Node(nodePosition));
        }

        for (Road road :
                roadList) {
            Node source = nodes.get(road.getStart());
            Node end = nodes.get(road.getEnd());
            Edge edge = new Edge(source, end);
            edges.add(edge);
        }
    }
}
