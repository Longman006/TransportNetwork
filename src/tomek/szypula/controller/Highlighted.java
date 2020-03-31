package tomek.szypula.controller;

import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.math.Vector2D;
import tomek.szypula.models.Car;
import tomek.szypula.models.CarParameters;

public final class Highlighted {
    private Highlighted(){}
    private static Car highlightedCar = new Car(new Vector2D(),new Vector2D(),new CarParameters());
    private static SimpleBooleanProperty isChange = new SimpleBooleanProperty(false);

    public static void setIsChange(boolean isChange) {
        Highlighted.isChange.set(isChange);
    }

    public static boolean isIsChange() {
        return isChange.get();
    }

    public static SimpleBooleanProperty isChangeProperty() {
        return isChange;
    }

    public static void switchHighlightCar(Car car){
        if (highlightedCar == car){
            car.switchHighlighted();
        }
        else{
            highlightedCar.setHighlighted(false);
            highlightedCar=car;
            highlightedCar.setHighlighted(true);
        }
        isChange.setValue(true);
    }
    public static Car getHighlightedCar() {
        return highlightedCar;
    }
}
