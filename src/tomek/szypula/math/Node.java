package tomek.szypula.math;

import java.util.ArrayList;
import java.util.List;

public class Node {
    List<Edge> edgesIn = new ArrayList<>();
    List<Edge> edgesOut = new ArrayList<>();

    Vector2D position;

    public Node(Vector2D position){
        this.position = position;
    };

    private void addEdgeIn(Edge edgeIn){
        edgesIn.add(edgeIn);
    }
    private void addEdgeOut(Edge edgeOut){
        edgesOut.add(edgeOut);
    }
    public void addEdge(Edge edge){
        if (edge.getSourceNode().equals(this)){
            addEdgeOut(edge);
        }
        else if(edge.getTargetNode().equals(this)){
            addEdgeIn(edge);
        }
    }
    public void removeEdge(Edge edge){
        if(!edgesOut.remove(edge)){
            edgesIn.remove(edge);
        }
    }

    public Vector2D getPosition() {
        return position;
    }

    public int getNodeDegreeIn(){
        return edgesIn.size();
    }
    public int getNodeDegreeOut(){
        return edgesOut.size();
    }

}
