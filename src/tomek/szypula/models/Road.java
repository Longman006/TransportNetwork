package tomek.szypula.models;

import tomek.szypula.math.Line;
import tomek.szypula.math.LineSegment;
import tomek.szypula.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Road {
    /**
     * The physical representation of the road
     */
    LineSegment lineSegment;
    /**
     * List of cars on the road from beginning to end
     */
    List<Car> carList = new ArrayList<>();;
    /**
     * List of possible roads at junction
     */
    List<Road> roadList = new ArrayList<>();;
    TrafficLights trafficLightsBeginning = new TrafficLights();
    TrafficLights trafficLightsEnd = new TrafficLights();

    public Road(LineSegment lineSegment) {
        this.lineSegment = lineSegment;
    }

    public Road(LineSegment lineSegment, List<Road> roadList) {
        this.lineSegment = lineSegment;
        this.roadList = roadList;
    }

    public Road(LineSegment lineSegment, List<Car> carList, List<Road> roadList) {
        this.lineSegment = lineSegment;
        this.carList = carList;
        this.roadList = roadList;
    }

    public Car getCar(int i){
        return carList.get(i);
    }
    public boolean isEmpty(){
        return carList.isEmpty();
    }
    public int numberOfCars(){
        return carList.size();
    }
    public double getDistanceToStart(int indexOfCar){
        //TODO test
        double distanceX = Math.pow(carList.get(indexOfCar).getX()-lineSegment.getStart().getX(),2);
        double distanceY = Math.pow(carList.get(indexOfCar).getY()-lineSegment.getStart().getY(),2);
        double distance = Math.sqrt(distanceX+distanceY);

        return distance;
    }

    public double getDistanceToStart(Car car){
        int index = carList.indexOf(car);
        return getDistanceToStart(index);
    }
    public double getDistanceToEnd(int indexOfCar){
        double distanceX = Math.pow(carList.get(indexOfCar).getX()-lineSegment.getEnd().getX(),2);
        double distanceY = Math.pow(carList.get(indexOfCar).getY()-lineSegment.getEnd().getY(),2);
        double distance = Math.sqrt(distanceX+distanceY);

        return distance;
    }
    public double getDistanceToEnd(Car car){
        int index = carList.indexOf(car);
        return getDistanceToEnd(index);
    }
    public Vector2D getStart() {
        return lineSegment.getStart();
    }
    public Vector2D getEnd(){
        return lineSegment.getEnd();
    }
}
