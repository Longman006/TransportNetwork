package tomek.szypula.controller;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import tomek.szypula.math.LineSegment;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;
import tomek.szypula.models.Road;
import tomek.szypula.view.RoadUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Editor {

    Group lines = new Group();
    List<Road> roads = new ArrayList<>();
    List<RoadUI> roadUIS = new ArrayList<>();
    Pane pane ;
    Text text = new Text();
    List<Circle> nodes = new ArrayList<>();
    Circle startCircle = null;
    Circle endCircle = null;
    Group circles = new Group();
    Line tmpLine = new Line();
    int radious = 10;
    int strokeWidth = 2;

    public Editor(List<Road> roads, Pane pane) {
        this.roads = roads;
        this.pane = pane;
        pane.getChildren().addAll(tmpLine,lines,circles,text);
        pane.setOnMouseMoved(mouseEvent -> setCursorDisplay(mouseEvent.getX(),mouseEvent.getY()));
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Point2D mousePosition = new Point2D(mouseEvent.getX(),mouseEvent.getY());
                for (Circle circle :
                        nodes) {
                    if (circle.contains(mousePosition)){
                        handleMouseClickOnCircle(circle);
                        return;
                    }
                }
                handleMouseClickOnPane(mouseEvent);
            }
        });

    }
    private void handleMouseClickOnPane(MouseEvent mouseEvent){
        if (startCircle == null){
            startCircle = createCircle(mouseEvent);
            System.out.println("Creating startCircle on event");
        }
        else if (endCircle == null){
            endCircle =createCircle(mouseEvent);
            createRoad();
        }
    }
    private void createRoad(){
        Road road = new Road(new LineSegment(
                new Vector2D(
                        startCircle.getCenterX(),
                        startCircle.getCenterY()),
                new Vector2D(
                        endCircle.getCenterX(),
                        endCircle.getCenterY())));
        roads.add(road);
        RoadUI roadUI = new RoadUI(road);
        roadUI.createUI(lines);
        updateRoadNetwork(road);
        startCircle = null;
        endCircle = null;
    }

    private void updateRoadNetwork(Road road) {
        for (Road roadNext : roads){
            if (road.getEnd().equalValue(roadNext.getStart()))
                road.addRoad(roadNext);
            else if ( road.getStart().equalValue(roadNext.getEnd()))
                road.addPreviousRoad(roadNext);
        }
    }

    private void handleMouseClickOnCircle(Circle circle){
        if (startCircle == null){
            startCircle = circle;
            System.out.println("Creating startCircle on circle");
        }
        else if (endCircle == null && !circle.equals(startCircle)){
            endCircle = circle;
            createRoad();
        }
    }
    private Circle createCircle(MouseEvent mouseEvent){
        Circle circle = new Circle(radious, Color.RED);
        circle.setCenterX(mouseEvent.getX());
        circle.setCenterY(mouseEvent.getY());
        circle.setOnMouseEntered(mouseEvent1 -> circle.setRadius(radious*2));
        circle.setOnMouseExited(mouseEvent1 -> circle.setRadius(radious));
        circles.getChildren().add(circle);
        nodes.add(circle);
        return circle;
    }

    private void setCursorDisplay(double x, double y) {
        text.setText(x+" , "+y);
        text.setX(x+12);
        text.setY(y+20);
        if (startCircle != null){
            tmpLine.setVisible(true);
            tmpLine.setStartX(startCircle.getCenterX());
            tmpLine.setStartY(startCircle.getCenterY());
            tmpLine.setEndX(x);
            tmpLine.setEndY(y);
            if (Vector2DMath.equalValue(startCircle.getCenterX(), x ) || Vector2DMath.equalValue( startCircle.getCenterY() , y)){
                tmpLine.setStroke(Color.RED);
                tmpLine.setStrokeWidth(strokeWidth*3);
            }
            else {
                tmpLine.setStroke(Color.BLACK);
                tmpLine.setStrokeWidth(strokeWidth);
            }
        }
        else
            tmpLine.setVisible(false);
    }
}
