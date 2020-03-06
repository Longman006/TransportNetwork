package tomek.szypula.models;

import tomek.szypula.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Route {

    //The maximum number of roads stored (both past and future)
    public static final int MAX_SIZE = 2 ;
    private final Car car;
    //index 0 corresponds to the current road.
    private List<Road> roadList = new ArrayList<>();
    //index 0 corresponds to the "latest" road.
    private List<Road> pastRoadList = new ArrayList<>();

    public Route(Road startingRoad,Car car) {
        this.car = car;
        addRoad(startingRoad);
    }
    private void addRoadToPastRoads(Road road){
        pastRoadList.add(0,road);
        while (pastRoadList.size() > MAX_SIZE)
            pastRoadList.remove(pastRoadList.size()-1);
    }
    public boolean addRoad(Road road){
        return roadList.add(road);
    }
    public boolean removeRoad(){
        return removeRoad(0);
    }

    //Since the first (0) road is always the one containing the car,
    //Whenever the first road is removed from the list the car has passed on to the next road.
    public boolean removeRoad(int index) {
        if (roadList.size() > index) {
            Road roadToRemove = roadList.remove(index);
            if (index == 0)
                addRoadToPastRoads(roadToRemove);
            return true;
        } else
            return false;
    }
    public Road getNextRoad(){
        return roadList.get(1);
    }
    public Road getLastRoad(){
        return roadList.get(roadList.size()-1);
    }
    public Road getBeforeLastRoad(){
        return roadList.get(roadList.size()-2);
    }
    public Road getCurrentRoad(){
        return roadList.get(0);
    }
    public boolean isLoop(){
        if (roadList.size() > 2 && roadList.get(0).equals(roadList.get(roadList.size()-1)))
            return true;
        else
            return false;
    }
    public int size(){
        return roadList.size();
    }

    /**
     * Should take into account situations where,
     * it could be a loop,
     * the same car could be the end car,
     * the next car could be on the same road but behind the current car
     * @param carNext
     * @return the distance along route
     */
    public double calculateDistanceAlongRoute(Car carNext){
        double distance = 0;
        /**
         * The first road is always the starting road, that is, the one that contains the car
         */
        for (int i = 0 ; i < roadList.size() ; i++){
            Road road = roadList.get(i);
            /**
             * If the next road is blocked then it is null
             */
            if (road == null)
                break;
            /**
             * Start, in case carNext is also car
             */
            if(i==0){
                if (road.indexOf(carNext)>road.indexOf(car)) {
                    distance += car.getDistanceToCar(carNext);
                    break;
                }
                else
                    distance+=road.getDistanceToEnd(car);
            }
            else if (road.indexOf(carNext)<0)
                distance+=road.getLength()-2*car.getSize();
            else{
                distance+=road.getDistanceToStart(carNext);
                break;
            }
            if(road.getTrafficLightsEnd().isStop()) {
                break;
            }

        }
        return distance;
    }
    public boolean isOnRampOnRoute(Road road){
        return roadList.contains(road) || pastRoadList.contains(road);
    }
    //Assuming that the user will first check if the onRamp is OnRoute.
    public double calculateDistanceToOnRamp(Road onRampRoad){
        if (isOnRampInFrontOfCar(onRampRoad)){
            return calculateDistanceToOnRampInFront(onRampRoad);
        }
        else
            return calculateDistanceToOnRampBehind(onRampRoad);
    }
    //Private method to check if the OnRamp is in front of or behind the current position
    private boolean isOnRampInFrontOfCar(Road road){
        if (roadList.contains(road))
            return true;
        else if (pastRoadList.contains(road))
            return false;
        else
            return false;
    }
    //Private method to help organize calculateDistanceToOnRamp
    private double calculateDistanceToOnRampBehind(Road onRampRoad) {
        double result = 0;
        result += getCurrentRoad().getDistanceToStart(car);
        for (Road road :
                roadList) {
            if (road.equals(onRampRoad))
                break;
            result += road.getLength();
        }
        return result;
    }

    //Private method to help organize calculateDistanceToOnRamp
    private double calculateDistanceToOnRampInFront(Road onRampRoad) {
        double result = 0;
        for (Road road :
                roadList) {
            if (road.containsCar(car))
                result += road.getDistanceToEnd(car);
            else
                result += road.getLength();

            if (road.equals(onRampRoad))
                break;
        }
        return result;
    }
    // No comments
    public Car getNextCarOnRoute() {
        for (int i = 0; i < roadList.size(); i++) {
            Road road = roadList.get(i);
            if (road == null)
                return new Car(new Vector2D(), new Vector2D(), TrafficManagementSystem.getCarParameters());
            if (road.hasNextCar(car)) {
                return road.getNextCar(car);
            } else if (i == roadList.size() - 1 && isLoop() && road.hasPreviousCar(car)) {
                return road.getPreviousCar(car);
            } else if (road.getTrafficLightsEnd().isStop()) {
                return new Car(new Vector2D(), new Vector2D(), TrafficManagementSystem.getCarParameters());
            }
        }
        return car;
    }

    public List<Road> getRoadList() {
        return roadList;
    }
}
