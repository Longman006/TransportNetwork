package tomek.szypula.models;

import tomek.szypula.math.Vector2D;

public abstract class Driver {

    protected abstract Road nextRoad(Road currentRoad);

    private Vector2D destination;
    private Route route;

    private Car car;

    Driver(Vector2D destination,Road startingRoad,Car car){
        this.car = car;
        this.destination=destination;
        route = new Route(startingRoad,car);
    }
    protected void updateRoute(Car car){
        while(!route.getCurrentRoad().containsCar(car)){
            route.removeRoad();
        }
        while(route.size() < Route.MAX_SIZE ){
            if (route.isLoop()){
                break;
            }
            if(route.getLastRoad() == null) {
                Road nextRoad = nextRoad(route.getBeforeLastRoad());
                if (nextRoad == null)
                    break;
                else {
                    route.removeRoad(route.size() - 1);
                    route.addRoad(nextRoad);
                }
            }
            else {
                route.addRoad(nextRoad(route.getLastRoad()));
            }

        }
    }
    public Route getRoute() {
        updateRoute(car);
        return route;
    }

    public Vector2D getDestination() {
        return new Vector2D(destination);
    }
}
