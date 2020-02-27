package tomek.szypula.view;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineJoin;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;
import tomek.szypula.models.Road;

import java.util.ArrayList;
import java.util.List;

public class OutOfServiceUI implements CreateUI{

    List<Line> lines = new ArrayList<>();

    public OutOfServiceUI(Road road,RoadUI roadUI) {

        double width = roadUI.getWidth();
        Vector2D start1,start2,end1,end2;
        Vector2D direction = road.getLineSegment().getDirection().multiply(width/2);
        Vector2D normal = Vector2DMath.getNormalVector2D(road.getLineSegment().getDirection()).multiply(width/2);
        Vector2D start = road.getStart();
        Vector2D end = road.getEnd();

        List<Vector2D> startingPoints = new ArrayList<>();
        List<Vector2D> endPoints = new ArrayList<>();

        start1 = Vector2DMath.vector2DSum(start,normal).addVector2D(direction);;
        end1 = Vector2DMath.vector2DSubtract(normal,start).addVector2D(direction);;
        start2 = Vector2DMath.vector2DSum(start1,direction).addVector2D(direction).addVector2D(direction);;
        end2 = Vector2DMath.vector2DSum(end1,direction).addVector2D(direction).addVector2D(direction);;

        startingPoints.add(start1);
        startingPoints.add(start2);
        endPoints.add(end1);
        endPoints.add(end2);


        //This is just for drawing the out of service sign
        for (Vector2D startPoint : startingPoints){
            for (Vector2D endPoint : endPoints){
                Line lineShape = new Line(startPoint.getX(),startPoint.getY(),endPoint.getX(),endPoint.getY());
                lineShape.setSmooth(true);
                lineShape.setStrokeWidth( width/6);
                lineShape.setStrokeLineJoin(StrokeLineJoin.MITER);
                lineShape.setStroke(Color.CRIMSON);
                lineShape.setOpacity(0.0);
                lines.add(lineShape);
            }
        }
        //Creating the mouse event handler
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                road.getOutOfService().switchOutOfService();
                boolean outOfService = road.getOutOfService().isOutOfService();
                for (Line line : lines){
                    double opacity = outOfService ? 1.0 : 0.0;
                    line.setOpacity(opacity);
                }
            }
        };
        //Registering the event filter
        for (Line line: lines) {
            line.addEventFilter(MouseEvent.MOUSE_CLICKED,eventHandler);

        }
        //roadUI.getShape().addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }


    @Override
    public void createUI(Group parent) {
        parent.getChildren().addAll(lines);
    }

    @Override
    public void remove(Group parent) {
        parent.getChildren().remove(lines);
    }
}
