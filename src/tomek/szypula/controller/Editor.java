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
        if (roads.size() != 0 )
            updateView();
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

    private void updateView() {
        for (Road road : roads) {
            createCircle(road.getStart().getX(),road.getStart().getY());
            createCircle(road.getEnd().getX(),road.getEnd().getY());
            createRoadUI(road);
        }
    }

    private void handleMouseClickOnPane(MouseEvent mouseEvent){
        if (startCircle == null){
            startCircle = createCircle(mouseEvent.getX(),mouseEvent.getY());
        }
        else if (endCircle == null){
            endCircle =createCircle(mouseEvent.getX(),mouseEvent.getY());
            createRoad(startCircle.getCenterX(),startCircle.getCenterY(),endCircle.getCenterX(),endCircle.getCenterY());
        }
    }

    private void createRoad(double x1, double y1, double x2, double y2) {
        Road road = new Road(new LineSegment(
                new Vector2D(
                        x1,
                        y1),
                new Vector2D(
                        x2,
                        y2)));
        roads.add(road);
        updateRoadNetwork(road);
        createRoadUI(road);
        startCircle = null;
        endCircle = null;
    }
    private void createRoadUI(Road road){
        RoadUI roadUI = new RoadUI(road);
        roadUI.createUI(lines);
        roadUIS.add(roadUI);
    }


    private void updateRoadNetwork(Road road) {
        for (Road roadNext : roads){
            if (road.getEnd().equalValue(roadNext.getStart()))
                road.addRoad(roadNext);
            else if ( road.getStart().equalValue(roadNext.getEnd()))
                road.addPreviousRoad(roadNext);
        }
        for (Road roadOld :
                roads) {
            if (roadOld.getEnd().equalValue(road.getStart()))
                roadOld.addRoad(road);
            else if ( roadOld.getStart().equalValue(road.getEnd()))
                roadOld.addPreviousRoad(road);
        }
    }

    private void handleMouseClickOnCircle(Circle circle){
        if (startCircle == null){
            startCircle = circle;
        }
        else if (endCircle == null && !circle.equals(startCircle)){
            endCircle = circle;
            createRoad(startCircle.getCenterX(),startCircle.getCenterY(),endCircle.getCenterX(),endCircle.getCenterY());

        }
    }
    private Circle createCircle(double x,double y){
        Circle circle = new Circle(radious, Color.RED);
        circle.setCenterX(x);
        circle.setCenterY(y);
        if (circles.contains(new Point2D(circle.getCenterX(),circle.getCenterY())))
            return null;

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

    public void clear() {
        for (RoadUI roadUI :
             roadUIS   ) {
            roadUI.remove(lines);
        }
        circles.getChildren().removeAll(circles.getChildren());
    }

    public void undo() {
        if (circles.getChildren().size() > 1){
            if (circles.getChildren().size()>roads.size())
                circles.getChildren().remove(circles.getChildren().size()-1);

            roads.remove(roads.size()-1);
            roadUIS.get(roadUIS.size()-1).remove(lines);
            roadUIS.remove(roadUIS.size()-1);

        }
        else if(circles.getChildren().size() == 1){
            circles.getChildren().remove(circles.getChildren().size()-1);
        }
    }
}
