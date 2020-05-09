package tomek.szypula.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineJoin;
import tomek.szypula.controller.Highlighted;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;
import tomek.szypula.models.Road;

import java.util.concurrent.Callable;

public class RoadUI implements CreateUI{


    private Road road;
    private Line lineShape;
    private Polygon triangle;
    private double width = 14;
    private TrafficLightsUI trafficLightsUI;
    private OutOfServiceUI outOfServiceUI;
    private OnRampUI onRampUI;

    public double getWidth() {
        return width;
    }

    @Override
    public void createUI(Group parent) {
        trafficLightsUI.createUI(parent);
        parent.getChildren().add(lineShape);
        parent.getChildren().add(triangle);
        outOfServiceUI.createUI(parent);
        onRampUI.createUI(parent);
    }

    @Override
    public void remove(Group parent) {
        trafficLightsUI.remove(parent);
        parent.getChildren().remove(lineShape);
        parent.getChildren().remove(triangle);
        outOfServiceUI.remove(parent);
        onRampUI.remove(parent);
    }

    public RoadUI(Road road) {
        this.road = road;
        trafficLightsUI = new TrafficLightsUI(road);
        lineShape = new Line(road.getStart().getX(),road.getStart().getY(),road.getEnd().getX(),road.getEnd().getY());
        lineShape.setSmooth(true);
        lineShape.setStrokeWidth( width);
        lineShape.setStrokeLineJoin(StrokeLineJoin.MITER);
        lineShape.setStroke(Color.LIGHTGRAY);

        Vector2D direction = road.getLineSegment().getDirection();
        Vector2D start = road.getStart().addVector2D(direction.multiply(road.getLength()/2));
        Vector2D startPoint = Vector2DMath.vector2DSum(start,direction.normalize().multiply(width/2));
        Vector2D leftPoint = Vector2DMath.vector2DSum(Vector2DMath.getNormalVector2D(direction).normalize().multiply(width/2),start);
        Vector2D rightPoint = Vector2DMath.vector2DSum(Vector2DMath.getNormalVector2D(direction).normalize().multiply(-width/2),start);

        triangle = new Polygon();
        triangle.getPoints().addAll(
                new Double[]{
                        startPoint.getX(),startPoint.getY(),
                        leftPoint.getX(),leftPoint.getY(),
                        rightPoint.getX(),rightPoint.getY()
                });
        triangle.setStrokeWidth(width);
        triangle.setSmooth(true);
        triangle.setFill(Color.RED);

        ObjectBinding<Color> colorHighlightBinding = Bindings.createObjectBinding(
                new Callable<Color>() {
                    @Override
                    public Color call() throws Exception {
                        Color color;
                        if (road.equals(Highlighted.getHighlightedRoad()))
                            color = Color.hsb(360, 0.64, 0.84, 0.84);
                        else
                            color = Color.LIGHTGRAY;
                        return color;
                    }
                },Highlighted.isChangeRoadProperty()
        );
        lineShape.strokeProperty().bind(colorHighlightBinding);


        onRampUI = new OnRampUI(road,this);
        outOfServiceUI = new OutOfServiceUI(road,this);

        EventHandler<MouseEvent> eventHandler = e -> {
            if (e.isControlDown()) {
                road.getOnRamp().switchValue();
            }
            else if (e.isAltDown()) {
                road.getOutOfService().switchValue();
            }
            else {
                road.highlight();
            }
            e.consume();
        };
        //Registering the event filter
        lineShape.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);


    }

    public Line getShape() {
        return lineShape;
    }

}
