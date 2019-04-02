package tomek.szypula.view;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineJoin;
import tomek.szypula.models.Road;

public class RoadUI implements CreateUI{
    @Override
    public void setColor(Color color) {
        lineShape.setFill(color);
    }

    private Road road;
    private Line lineShape;

    @Override
    public void createUI(Group parent) {
        parent.getChildren().add(lineShape);
    }

    public RoadUI(Road road) {
        this.road = road;
        lineShape = new Line(road.getStart().getX(),road.getStart().getY(),road.getEnd().getX(),road.getEnd().getY());
        lineShape.setSmooth(true);
        lineShape.setStrokeWidth(8.0);
        lineShape.setStrokeLineJoin(StrokeLineJoin.MITER);
    }

}
