package tomek.szypula.controller;

import tomek.szypula.math.Vector2D;
import tomek.szypula.models.Car;
import tomek.szypula.models.CarParameters;

public final class Highlighted {
    private Highlighted(){}
    private static Car highlightedCar = new Car(new Vector2D(),new Vector2D(),new CarParameters());

    public static void switchHighlightCar(Car car){
        if (highlightedCar == car){
            car.switchHighlighted();
        }
        else{
            highlightedCar.setHighlighted(false);
            highlightedCar=car;
            highlightedCar.setHighlighted(true);
        }
    }

    public static void setHighlightedCar(Car car){
        if (highlightedCar == null || highlightedCar==car)
            highlightedCar=car;
        else
            switchHighlightCar(car);
    }
    public static Car getHighlightedCar() {
        return highlightedCar;
    }
}
