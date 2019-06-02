package tomek.szypula.models;

import tomek.szypula.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Route {

    public static final int MAX_SIZE = 5 ;
    private final Car car;
    private List<Road> roadList = new ArrayList<>();

    public Route(Road startingRoad,Car car) {
        this.car = car;
        addRoad(startingRoad);
    }
    public boolean addRoad(Road road){
        return roadList.add(road);
    }
    public boolean removeRoad(){
        if (roadList.size()>0){
            roadList.remove(0);
            return true;
        }
        return false;
    }
    public boolean removeRoad(int index){
        if (roadList.size()>index){
            roadList.remove(index);
            return true;
        }
        else
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
    public Road getFirstRoad(){
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
     * Should take into account situations where, it could be a loop, and the same car could be the end car
     * and the next car could be on the same road but behind the current car
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

    public void moveAlongRoute() {
        Vector2D carSpeed = car.getSpeedCopy();
        double carSpeedValue = carSpeed.getLength();
        for (int i =0 ; i< roadList.size(); i++){
            Road road = roadList.get(i);
            if(road.getDistanceToEnd(car) > carSpeedValue){
                car.setPosition(car.getPosition().addVector2D(road.getLineSegment().getDirection().multiply(carSpeedValue)));
                break;
            }
            else {
                double travelled = road.getDistanceToEnd(car);
                carSpeedValue-=travelled;
                if(!roadList.get(i+1).insertCar(car)){
//                    car.setPosition(road.getEnd());
//                    car.setSpeed(new Vector2D());
                }
            }

        }

    }
}
