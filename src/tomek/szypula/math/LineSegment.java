package tomek.szypula.math;

public class LineSegment {
    private Line line ;
    private final Vector2D beginPoint;
    private final Vector2D endPoint;
    private final Vector2D direction;

    public LineSegment(Vector2D beginPoint, Vector2D endPoint) {
        this.beginPoint = beginPoint;
        this.endPoint = endPoint;
        this.direction = Vector2DMath.vector2DSubtract(beginPoint,endPoint).normalize();
        this.line = new Line(beginPoint,endPoint);
    }

    public Vector2D getStartCopy() {
        return new Vector2D(beginPoint);
    }
    public Vector2D getEndCopy() {
        return new Vector2D(endPoint);
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

    public Vector2D getStart() {
        return beginPoint;
    }

    public Vector2D getEnd() {
        return endPoint;
    }
}
