package tomek.szypula.controller;

import javafx.scene.Group;
import javafx.scene.control.SplitPane;

import tomek.szypula.math.LineSegment;
import tomek.szypula.math.Vector2D;
import tomek.szypula.models.*;
import tomek.szypula.view.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Controller {

    Road stopRoad;
    List<Road> roads = new ArrayList<>();
    List<Road> startingRoads = new ArrayList<>();
    List<Car> carsList = new ArrayList<>();
    Model model;
    DataManagementSystem dataManagementSystem;
    private int x = 10;

    Group parent;


    public Controller(SplitPane splitPane) {
        createRoads();
        createRoadNetwork();
        createCars();
        model = new Model(roads);
        View view = new View(model,splitPane);
        dataManagementSystem = new DataManagementSystem(model);

    }


    private void createRoads(){
        Vector2D a,b,c,d,e,f,g,h,i;
        a = new Vector2D(20,20);
        b = new Vector2D(20,140);
        c = new Vector2D(20,280);
        d = new Vector2D(120,20);
        e = new Vector2D(120,140);
        f = new Vector2D(120,280);
        g = new Vector2D(500,280);
        h = new Vector2D(500,140);
        i = new Vector2D(500,20);

        a = new Vector2D(x,x);
        b = new Vector2D(x,7*x);
        c = new Vector2D(x,13*x);
        d = new Vector2D(6*x,x);
        e = new Vector2D(6*x,7*x);
        f = new Vector2D(6*x,13*x);
        g = new Vector2D(25*x,13*x);
        h = new Vector2D(25*x,7*x);
        i = new Vector2D(25*x,x);

        List<Vector2D> points = new ArrayList<>();
        points.add(a);
        points.add(b);
        points.add(c);
        points.add(d);
        points.add(e);
        points.add(f);
        points.add(g);
        points.add(h);
        points.add(i);
        for (Vector2D vector2D: points)
            vector2D.multiply(3);

        startingRoads.add(new Road(new LineSegment(b,a)));
        startingRoads.add(new Road(new LineSegment(b,c)));
        startingRoads.add( new Road(new LineSegment(b,e)));
        roads.addAll(startingRoads);
        roads.add(new Road(new LineSegment(a,d)));
        roads.add(new Road(new LineSegment(c,f)));
        roads.add(new Road(new LineSegment(f,e)));
        roads.add(new Road(new LineSegment(d,e)));
        roads.add(new Road(new LineSegment(g,f)));
        roads.add(new Road(new LineSegment(h,g)));
        roads.add(new Road(new LineSegment(h,i)));
        stopRoad = new Road(new LineSegment(e,h));
        roads.add(stopRoad);
        roads.add(new Road(new LineSegment(i,d)));


    }
    private void printRoads(){
        for (Road road : roads  ) {
            System.out.println(road.toString());
        }
    }
    private void createRoadNetwork(){
        for (Road road : roads ) {
            for (Road roadNext : roads){
                if (road.getEnd().equalValue(roadNext.getStart()))
                    road.addRoad(roadNext);
            }
        }
    }
    private void createCars(){
        for(int i = 0 ; i<roads.size()-8;i++){
            Car car = new Car(roads.get(i).getStart(),roads.get(i).getLineSegment().getDirection(),TrafficManagementSystem.getCarParameters());
            car.setDriver(new RandomDriver(new Vector2D(),roads.get(i),car));
            roads.get(i).insertCar(car);
            carsList.add(car);
        }
    }

    public void step(){

        model.updateSpeed();
//        try {
//            dataManagementSystem.updateFile("TestFile.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
