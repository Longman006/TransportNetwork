package tomek.szypula.models;

import tomek.szypula.math.Line;
import tomek.szypula.math.LineSegment;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;

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

    TrafficLights trafficLightsEnd = new TrafficLights();
    OutOfService outOfService = new OutOfService();

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
        double distanceX = Math.pow(carList.get(indexOfCar).getX()-lineSegment.getStart().getX(),2);
        double distanceY = Math.pow(carList.get(indexOfCar).getY()-lineSegment.getStart().getY(),2);
        double distance = Math.sqrt(distanceX+distanceY)-carList.get(indexOfCar).getSize();
        if (distance < 0 )
            return 0;
        return distance;
    }

    public double getDistanceToStart(Car car){ return getDistanceToStart(carList.indexOf(car));}
    public double getDistanceToEnd(int indexOfCar){
        double distanceX = Math.pow(carList.get(indexOfCar).getX()-lineSegment.getEnd().getX(),2);
        double distanceY = Math.pow(carList.get(indexOfCar).getY()-lineSegment.getEnd().getY(),2);
        double distance = Math.sqrt(distanceX+distanceY)-carList.get(indexOfCar).getSize();
        if (distance < 0 )
            return 0;
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

    @Override
    public String toString() {
        return "Road{" +
                "\n"+
                getStart() + "\n"+
                getEnd() + "\n" ;
    }
    public int indexOf(Car car){
        return carList.indexOf(car);
    }
    

    private void setupInsertCarAtStart(Car car){
        car.setDirection(lineSegment.getDirection());
        //car.setSpeed(new Vector2D());
        car.setPosition(getStart());
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
        carList.add(car);
    }
    public void removeCar(Car car){
        carList.remove(car);
    }
    public boolean containsCar(Car car){
        return carList.contains(car);
    }

    public double getLength() {
        return Vector2DMath.distance(lineSegment.getStart(), lineSegment.getEnd());
    }

    public TrafficLights getTrafficLightsEnd() {
        return trafficLightsEnd;
    }
}
