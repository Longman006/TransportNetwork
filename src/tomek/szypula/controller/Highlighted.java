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

    public static void setHighlightedCar(Car highlightedCar) {
        Highlighted.highlightedCar = highlightedCar;
        isChangeCar.setValue(true);
    }

    private static SimpleBooleanProperty isChangeCar = new SimpleBooleanProperty(false);

    private static WaveFront highlightedWaveFront = new WaveFront(new Car(new Vector2D(),new Vector2D(),new CarParameters()),new Road(new LineSegment(new Vector2D(),new Vector2D()),new ArrayList<>()));
    private static SimpleBooleanProperty isChangeWaveFront = new SimpleBooleanProperty(false);

    public static WaveFront getHighlightedWaveFront() {
        return highlightedWaveFront;
    }

    public static void setHighlightedWaveFront(WaveFront highlightedWaveFront) {
        Highlighted.highlightedWaveFront = highlightedWaveFront;
        isChangeWaveFront.setValue(true);
    }

    public static boolean isIsChangeWaveFront() {
        return isChangeWaveFront.get();
    }

    public static SimpleBooleanProperty isChangeWaveFrontProperty() {
        return isChangeWaveFront;
    }

    public static void setIsChangeWaveFront(boolean isChangeWaveFront) {
        Highlighted.isChangeWaveFront.set(isChangeWaveFront);
    }

    public static void setIsChangeCar(boolean isChangeCar) {
        Highlighted.isChangeCar.set(isChangeCar);
    }

    public static SimpleBooleanProperty isChangeCarProperty() {
        return isChangeCar;
    }

    public static Car getHighlightedCar() {
        return highlightedCar;
    }
}
