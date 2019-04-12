package tomek.szypula.view;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineJoin;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;
import tomek.szypula.models.Road;

public class RoadUI implements CreateUI{
    @Override
    public void setColor(Color color) {
        lineShape.setFill(color);
    }

    private Road road;
    private Line lineShape;
    private Polygon triangle;
    private double triangleSize = 8;

    @Override
    public void createUI(Group parent) {
        parent.getChildren().add(lineShape);
        parent.getChildren().add(triangle);
    }

    public RoadUI(Road road) {
        this.road = road;
        lineShape = new Line(road.getStart().getX(),road.getStart().getY(),road.getEnd().getX(),road.getEnd().getY());
        lineShape.setSmooth(true);
        lineShape.setStrokeWidth(4.0);
        lineShape.setStrokeLineJoin(StrokeLineJoin.MITER);

        Vector2D direction = road.getLineSegment().getDirection();
        Vector2D startPoint = Vector2DMath.vector2DSum(road.getStart(),direction.normalize().multiply(triangleSize));
        Vector2D leftPoint = Vector2DMath.vector2DSum(Vector2DMath.getNormalVector2D(direction).normalize().multiply(triangleSize),road.getStart());
        Vector2D rightPoint = Vector2DMath.vector2DSum(Vector2DMath.getNormalVector2D(direction).normalize().multiply(-triangleSize),road.getStart());

        triangle = new Polygon();
        triangle.getPoints().addAll(
                new Double[]{
                        startPoint.getX(),startPoint.getY(),
                        leftPoint.getX(),leftPoint.getY(),
                        rightPoint.getX(),rightPoint.getY()
                });
        System.out.println(triangle);
        triangle.setStrokeWidth(8.0);
        triangle.setSmooth(true);
        triangle.setFill(Color.RED);
    }

}
