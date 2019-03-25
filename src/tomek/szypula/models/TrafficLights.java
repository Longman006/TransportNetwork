package tomek.szypula.models;

public class TrafficLights {
    boolean stop;

    public TrafficLights() {
        stop = false;
    }

    public void switchLights(){ stop = !stop ;}

    public void lightsOn(){ stop = false;}

    public void lightsOff(){ stop = true; }
}
