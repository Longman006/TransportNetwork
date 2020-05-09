package tomek.szypula.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineJoin;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;
import tomek.szypula.models.OnRamp;
import tomek.szypula.models.RandomDriver;
import tomek.szypula.models.Road;

import java.util.ArrayList;
import java.util.List;

public class OnRampUI implements CreateUI {

    List<Line> lines = new ArrayList();
    private Line lineShape;

    public OnRampUI(Road road, RoadUI roadUI){

        double width = roadUI.getWidth();
        double length = road.getLength();
        Vector2D start1,start2,end1,end2;
        Vector2D direction = road.getLineSegment().getDirection();
        Vector2D normal = Vector2DMath.getNormalVector2D(road.getLineSegment().getDirection()).multiply(width/2);
        Vector2D start = road.getStart().addVector2D(Vector2DMath.multiplyVector2D(direction,length).subtractVector2D(Vector2DMath.multiplyVector2D(direction,width*2)));

        List<Vector2D> startingPoints = new ArrayList<>();
        List<Vector2D> endPoints = new ArrayList<>();

        start1 = Vector2DMath.vector2DSum(start,normal).addVector2D(direction);
        end1 = Vector2DMath.vector2DSubtract(normal,start).addVector2D(direction);
        direction.multiply(width/2);
        start2 = Vector2DMath.vector2DSum(start1,direction).addVector2D(direction).addVector2D(direction);
        end2 = Vector2DMath.vector2DSum(end1,direction).addVector2D(direction).addVector2D(direction);

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
                lineShape.setStroke(Color.YELLOWGREEN);
                lineShape.setOpacity(0.0);
                lines.add(lineShape);
            }
        }
        //Converting the BooleanProperty to Double to use as a binding with the opacity
        DoubleBinding opacityDoubleBinding = new DoubleBinding() {
            {
                super.bind(road.getOnRamp().onRampProperty());
            }
            @Override
            protected double computeValue() {
                return road.getOnRamp().isOnRamp() ? 0.8 : 0.0;
            }
        };
        for (Line line : lines){
            DoubleProperty opacity = line.opacityProperty();
            opacity.bind(opacityDoubleBinding);
        }

    }
    @Override
    public void createUI(Group parent) {
        parent.getChildren().addAll(lines);
    }

    @Override
    public void remove(Group parent) {
        parent.getChildren().removeAll(lines);
    }
}
