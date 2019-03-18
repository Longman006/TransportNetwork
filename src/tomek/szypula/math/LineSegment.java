package tomek.szypula.math;

public class LineSegment {
    private Line line ;
    private Vector2D beginPoint;
    private Vector2D endPoint;

    public LineSegment(Vector2D beginPoint, Vector2D endPoint) {
        this.beginPoint = beginPoint;
        this.endPoint = endPoint;
        this.line = new Line(beginPoint,endPoint);
    }
}
