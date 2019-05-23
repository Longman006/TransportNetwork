package tomek.szypula.models;

import java.util.ArrayList;
import java.util.List;

public class TrafficManagementSystem {

    private List<Road> roadList;
    private List<Jam> jamList = new ArrayList<>();
    private static CarParameters defaultCarParameters = new CarParameters();

    public TrafficManagementSystem(List<Road> roadList) {
        this.roadList = roadList;
        createJams();
    }

    public static CarParameters getCarParameters() {
        return defaultCarParameters;
    }

    private void createJams() {
        for (Road road : roadList) {
            jamList.add(new Jam(road));
        }
    }


    public void update() {
        for (Jam jam : jamList){
            jam.updateJam();
        }
    }

    public List<Road> getRoadList() {
        return roadList;
    }
    public List<Car> getCarList(){
        List<Car> carList = new ArrayList<>();
        for (Road road : roadList)
            carList.addAll(road.getCarList());
        return carList;
    }

    public List<Jam> getJamList() {
        return jamList;
    }
}
