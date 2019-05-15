package tomek.szypula.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TrafficLights {
    BooleanProperty stop = new SimpleBooleanProperty();

    public TrafficLights() {
        stop.set(false);
    }

    public void switchLights(){ stop.set(!stop.get());}

    public void lightsOn(){ stop.set(true);}

    public void lightsOff(){ stop.set(false); }

    public boolean isStop(){
        return stop.get();
    }

    public BooleanProperty stopProperty() {
        return stop;
    }
}
