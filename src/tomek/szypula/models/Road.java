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
    public List<Road> getRoadList(){ return roadList; }
    public Car getCar(int i){
        return carList.get(i);
    }
    public List<Car> getCarList(){return carList;}
    public boolean hasNextCar(Car car){ return (carList.size()-1>carList.indexOf(car));}
    public Car getNextCar(Car car){return carList.get(carList.indexOf(car)+1);}
    public boolean isEmpty(){ return carList.isEmpty(); }
    public int numberOfCars(){ return carList.size(); }

    public double getDistanceToStart(int indexOfCar){
        //TODO test
        double distanceX = Math.pow(carList.get(indexOfCar).getX()-lineSegment.getStart().getX(),2);
        double distanceY = Math.pow(carList.get(indexOfCar).getY()-lineSegment.getStart().getY(),2);
        double distance = Math.sqrt(distanceX+distanceY);

        return distance;
    }

    public double getDistanceToStart(Car car){ return getDistanceToStart(carList.indexOf(car));}
    public double getDistanceToEnd(int indexOfCar){
        double distanceX = Math.pow(carList.get(indexOfCar).getX()-lineSegment.getEnd().getX(),2);
        double distanceY = Math.pow(carList.get(indexOfCar).getY()-lineSegment.getEnd().getY(),2);
        double distance = Math.sqrt(distanceX+distanceY);

        return distance;
    }
    public double getDistanceToEnd(Car car){ return getDistanceToEnd(carList.indexOf(car)); }
    public Vector2D getStart() {
        return lineSegment.getStart();
    }
    public Vector2D getEnd(){
        return lineSegment.getEnd();
    }

    public LineSegment getLineSegment() {
        return lineSegment;
    }
}
