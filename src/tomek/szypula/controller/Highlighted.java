package tomek.szypula.controller;

import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.math.LineSegment;
import tomek.szypula.math.Vector2D;
import tomek.szypula.models.Car;
import tomek.szypula.models.CarParameters;
import tomek.szypula.models.Road;
import tomek.szypula.models.WaveFront;

import java.util.ArrayList;

public final class Highlighted {
    private Highlighted(){}

    private static Car highlightedCar = new Car(new Vector2D(),new Vector2D(),new CarParameters());
    private static WaveFront highlightedWaveFront = new WaveFront(new Car(new Vector2D(),new Vector2D(),new CarParameters()),new Road(new LineSegment(new Vector2D(),new Vector2D()),new ArrayList<>()));
    private static Road highlightedRoad = new Road(new LineSegment(new Vector2D(),new Vector2D()));

    private static SimpleBooleanProperty isChangeCar = new SimpleBooleanProperty(false);
    private static SimpleBooleanProperty isChangeWaveFront = new SimpleBooleanProperty(false);
    private static SimpleBooleanProperty isChangeRoad = new SimpleBooleanProperty(false);


    public static void setHighlightedWaveFront(WaveFront highlightedWaveFront) {
        Highlighted.highlightedWaveFront = highlightedWaveFront;
        isChangeWaveFront.setValue(true);
    }
    public static void setHighlightedCar(Car highlightedCar) {
        Highlighted.highlightedCar = highlightedCar;
        isChangeCar.setValue(true);
    }
    public static void setHighlightedRoad(Road highlightedRoad){
        Highlighted.highlightedRoad = highlightedRoad;
        isChangeRoad.setValue(true);
    }

    public static SimpleBooleanProperty isChangeWaveFrontProperty() {
        return isChangeWaveFront;
    }
    public static SimpleBooleanProperty isChangeCarProperty() {
        return isChangeCar;
    }
    public static SimpleBooleanProperty isChangeRoadProperty() { return isChangeRoad; }

    public static Car getHighlightedCar() {
        return highlightedCar;
    }
    public static WaveFront getHighlightedWaveFront() {
        return highlightedWaveFront;
    }
    public static Road getHighlightedRoad() { return highlightedRoad; }
}
