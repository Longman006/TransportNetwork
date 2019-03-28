package tomek.szypula.models;

import tomek.szypula.math.Vector2D;

public abstract class Driver {

    public abstract Road nextRoad(Road currentRoad);
    private Vector2D destination;

    Driver(Vector2D destination){
        this.destination=destination;
    }

    public Vector2D getDestination() {
        return destination;
    }
}
