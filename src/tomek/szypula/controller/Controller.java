package tomek.szypula.controller;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import tomek.szypula.math.LineSegment;
import tomek.szypula.math.Vector2D;
import tomek.szypula.models.*;
import tomek.szypula.view.CarUI;
import tomek.szypula.view.CreateUI;
import tomek.szypula.view.RoadUI;

import java.awt.image.RasterOp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller {
    /**
     * TODO
     * Create seperate nodes for roads and cars
     * and maybe traffic lights in the future
     * and create a method to insert n new cars
     *
     */
    List<Road> roads = new ArrayList<>();
    List<CreateUI> UIelements = new ArrayList<>();
    List<Driver> drivers = new ArrayList<>();
    List<Car> carsList = new ArrayList<>();
    Model model;


    public Controller(Group parent) {
        createRoads();
        createRoadNetwork();
        createCars();
        initializeCars();
        //printRoads();


        model = new Model(roads);

        for (CreateUI createUI : UIelements){
            createUI.createUI(parent);
        }
    }
    private void createRoads(){
        Vector2D a,b,c,d,e,f,g,h,i;
        a = new Vector2D(10,10);
        b = new Vector2D(10,350);
        c = new Vector2D(10,700);
        d = new Vector2D(300,10);
        e = new Vector2D(300,350);
        f = new Vector2D(300,700);
        g = new Vector2D(1000,700);
        h = new Vector2D(1000,350);
        i = new Vector2D(1000,10);

        roads.add(new Road(new LineSegment(b,a)));
        roads.add(new Road(new LineSegment(b,c)));
        roads.add(new Road(new LineSegment(a,d)));
        roads.add(new Road(new LineSegment(b,e)));
        roads.add(new Road(new LineSegment(c,f)));
        roads.add(new Road(new LineSegment(f,e)));
        roads.add(new Road(new LineSegment(d,e)));
        roads.add(new Road(new LineSegment(g,f)));
        roads.add(new Road(new LineSegment(h,g)));
        roads.add(new Road(new LineSegment(h,i)));
        roads.add(new Road(new LineSegment(e,h)));
        roads.add(new Road(new LineSegment(i,d)));

        for(Road road : roads){
            UIelements.add(new RoadUI(road));
        }

    }
    private void printRoads(){
        for (Road road : roads  ) {
            System.out.println(road.toString());
            System.out.println(road.toStringNeighbours());
        }
    }
    private void createRoadNetwork(){
        /**
         * Try and rebuild this using iterators later
         * make sure this works
         */
        for (Road road : roads ) {
            for (Road roadNext : roads){
                if (road.getEnd().equals(roadNext.getStart()))
                    road.addRoad(roadNext);
            }
        }
    }
    private void createCars(){
        for(int i = 0 ; i<roads.size();i++){
            Car car = new Car(new CarParameters(),new RandomDriver(new Vector2D()));
            CarUI carUI = new CarUI(car,Color.hsb(360* (double) i/roads.size(),0.65,0.65));
            carsList.add(car);
            UIelements.add(carUI);
        }
    }
    private void initializeCars(){
        for(int i = 0 ; i<roads.size(); i++){
            roads.get(i).insertCar(carsList.get(i));

        }
    }
    public void step(){
        model.updateSpeed();

    }


    public void addCars(int n) {
        for (int i =0 ; i<n ; i++) {
            int index = (int) (Math.random() * roads.size());
            Road road = roads.get(index);

        }
    }
}
