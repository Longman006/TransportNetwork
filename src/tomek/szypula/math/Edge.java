package tomek.szypula.math;

public class Edge {
    Node sourceNode, targetNode;

    public Edge(Node sourceNode, Node targetNode){
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        sourceNode.addEdge(this);
        targetNode.addEdge(this);
    }
    public Node getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(Node sourceNode) {
        this.sourceNode = sourceNode;
    }

    public Node getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(Node targetNode) {
        this.targetNode = targetNode;
    }
}
