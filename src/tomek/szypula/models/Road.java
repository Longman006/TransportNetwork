package tomek.szypula.models;

import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.controller.Highlightable;
import tomek.szypula.controller.Highlighted;
import tomek.szypula.controller.UniqueId;
import tomek.szypula.math.LineSegment;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Road implements UniqueId, Highlightable {
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

    /**
     * List of previous roads
     */
    List<Road> previousRoadList = new ArrayList<>();

    TrafficLights trafficLightsEnd = new TrafficLights();
    OutOfService outOfService = new OutOfService();
    OnRamp onRamp = new OnRamp();
    private UUID uuid = UUID.randomUUID();

    public OnRamp getOnRamp() { return onRamp; }

    public OutOfService getOutOfService() {
        return outOfService;
    }

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
    public void addRoad(Road road){ roadList.add(road);}
    public void addPreviousRoad(Road road){ previousRoadList.add(road);}
    public List<Road> getPreviousRoadList(){return previousRoadList;}

    public List<Car> getCarList(){return carList;}

    /**
     *
     * @param car the car searching for the next car along a given route
     * @return if the car is on this road, then if there is a car in front of him returns true
     * otherwise if road is not empty returns true
     *
     */
    public boolean hasNextCar(Car car){ return (carList.size()-1>carList.indexOf(car));}

    /**
     *
     * @param car
     * @return the next car on road. Unless the input car is already the last car on road, then we return the first car on road
     */
    public Car getNextCar(Car car){return carList.get(carList.indexOf(car)+1); }
    public boolean hasPreviousCar(Car car){    return (carList.indexOf(car)>0);    }
    public Car getPreviousCar(Car car){ return carList.get(carList.indexOf(car)-1);}
    public boolean isEmpty(){ return carList.isEmpty(); }
    public int numberOfCars(){ return carList.size(); }

    public double getDistanceToStart(int indexOfCar){
        //TODO test
        double distanceX = Math.pow(carList.get(indexOfCar).getX()-lineSegment.getStartCopy().getX(),2);
        double distanceY = Math.pow(carList.get(indexOfCar).getY()-lineSegment.getStartCopy().getY(),2);
        double distance = Math.sqrt(distanceX+distanceY)-carList.get(indexOfCar).getSize();
        if (distance < 0 )
            return 0;
        return distance;
    }

    public double getDistanceToStart(Car car){ return getDistanceToStart(carList.indexOf(car));}
    public double getDistanceToEnd(int indexOfCar){
        double distanceX = Math.pow(carList.get(indexOfCar).getX()-lineSegment.getEndCopy().getX(),2);
        double distanceY = Math.pow(carList.get(indexOfCar).getY()-lineSegment.getEndCopy().getY(),2);
        double distance = Math.sqrt(distanceX+distanceY)-carList.get(indexOfCar).getSize();
        if (distance < 0 )
            return 0;
        return distance;
    }
    public double getDistanceToEnd(Car car){ return getDistanceToEnd(carList.indexOf(car)); }
    public Vector2D getStartCopy() {
        return lineSegment.getStartCopy();
    }
    public Vector2D getEndCopy(){
        return lineSegment.getEndCopy();
    }
    public Vector2D getStart() { return lineSegment.getStart();}
    public Vector2D getEnd() { return lineSegment.getEnd(); }

    public LineSegment getLineSegment() {
        return lineSegment;
    }

    @Override
    public String toString() {
        return "Road{" +
                "\n"+
                getStartCopy() + "\n"+
                getEndCopy() + "\n" ;
    }

    public int indexOf(Car car){
        return carList.indexOf(car);
    }
    

    private void setupInsertCarAtStart(Car car){
        car.setDirection(lineSegment.getDirection());
        //car.setSpeed(new Vector2D());
        car.setPosition(getStartCopy());
        carList.add(0, car);
    }
    public boolean insertCar(Car car){
        if (carList.size()>0){
            Car carOnRoad = carList.get(0);
            if (getDistanceToStart(carOnRoad) > 2*car.getSize()) {
                setupInsertCarAtStart(car);
                return true;
            }
        }
        else{
            setupInsertCarAtStart(car);
            return true;
        }
        return false;
    }
    public void addCar(Car car){
        carList.add(0,car);
    }
    public void removeCar(Car car){
        carList.remove(car);
    }
    public boolean containsCar(Car car){
        return carList.contains(car);
    }


    public Car getLastCar(){
        return carList.get(carList.size()-1);
    }

    public double getLength() {
        return Vector2DMath.distance(lineSegment.getStartCopy(), lineSegment.getEndCopy());
    }
    public Vector2D getDirection() { return lineSegment.getDirection();}

    public TrafficLights getTrafficLightsEnd() {
        return trafficLightsEnd;
    }

    @Override
    public String getId() {
        return uuid.toString();
    }

    @Override
    public void highlight() {
        Highlighted.setHighlightedRoad(this);
    }

    @Override
    public SimpleBooleanProperty isChangeProperty() {
        return Highlighted.isChangeRoadProperty();
    }

    @Override
    public Object getHighlighted() {
        return Highlighted.getHighlightedRoad();
    }

}
