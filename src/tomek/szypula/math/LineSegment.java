package tomek.szypula.math;

public class LineSegment {
    private Line line ;
    private Vector2D beginPoint;
    private Vector2D endPoint;
    private Vector2D direction;

    public LineSegment(Vector2D beginPoint, Vector2D endPoint) {
        this.beginPoint = beginPoint;
        this.endPoint = endPoint;
        this.direction = Vector2DMath.vector2DSubtract(beginPoint,endPoint);
        this.line = new Line(beginPoint,endPoint);
    }

    public Vector2D getStart() {
        return beginPoint;
    }
    public Vector2D getEnd() {
        return endPoint;
    }
    public Vector2D getClosestPointOnLine(Vector2D vector2D){
        return line.getClosestPointOnLine(vector2D);
    }
    public double getShortestDistanceToPointSquared(Vector2D point){
        Vector2D pointOnLine = getClosestPointOnLine(point);
        return Vector2DMath.distanceSquared(point,pointOnLine);
    }

    public Vector2D getDirection() {
        return new Vector2D(direction);
    }
}
