package tomek.szypula.file;

import javafx.scene.Scene;
import javafx.stage.FileChooser;
import tomek.szypula.math.LineSegment;
import tomek.szypula.math.Vector2D;
import tomek.szypula.models.Road;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadNetworkFromFile {


    String fileName;
    ReadFile readFile;
    Scene mainMenuScene;

    public ReadNetworkFromFile(Scene mainMenuScene) {
        this.fileName = "02-07-2020_19.29.03.Network";
        readFile = new ReadFile();
        this.mainMenuScene = mainMenuScene;
    }

    public String chooseFile(){
        //TODO
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Transport Network From File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files","*.*"),
                new FileChooser.ExtensionFilter("Text", "*.txt")
        );
        File file = fileChooser.showOpenDialog(mainMenuScene.getWindow());
        if (file != null){
            System.out.println(file.getPath());
            return file.getPath();
        }

        return "02-07-2020_19.29.03.Network";
    }

    public void loadNetwork(List<Road> roads) {
        roads.clear();
        roads.addAll(getRoadsFromFile(chooseFile()));
    }

    private List<Road> getRoadsFromFile(String fileName){

        //intup
        List<Integer> integerList = new ArrayList<>();
        List<Double> doubleList = new ArrayList<>();
        readFile.readFromFile(doubleList,integerList,fileName);

        //End results
        List<Vector2D> nodePositions = new ArrayList<>();
        List<Road> roads = new ArrayList<>();

        int tempCount = 0;
        Double[] tempPoint = new Double[2];

        //Retrieving nodes
        for (Double coordinate :
                doubleList) {
            tempPoint[tempCount] = coordinate;
            tempCount++;
            //We got two coordinates, now time to save and clear the cache :P
            if (tempCount == 2){
                nodePositions.add(new Vector2D(tempPoint[0],tempPoint[1]));
                tempCount = 0;
            }
        }
        //Create Roads
        List<Integer> oneRow = new ArrayList<>();
        int size = nodePositions.size();
        for (int i = 0; i <size ; i++) {
            oneRow = integerList.subList(i*size,(i+1)*size);
            createRoadsForOneNode(i,nodePositions,roads,oneRow);
        }
        //Create network
        for (Road road : roads ) {
            for (Road roadNext : roads){
                if (road.getEnd().equals(roadNext.getStart()))
                    road.addRoad(roadNext);
                else if ( road.getStart().equalValue(roadNext.getEnd()))
                    road.addPreviousRoad(roadNext);
            }
        }
        return roads;
    }

    private void createRoadsForOneNode(int nodeNumber, List<Vector2D> nodePositions, List<Road> roads,List<Integer> integers) {
        Vector2D beginningNode = nodePositions.get(nodeNumber);

        for (int i = 0; i < integers.size(); i++) {
            if (integers.get(i) == 1){
                roads.add(new Road(new LineSegment(beginningNode,nodePositions.get(i))));
            }
        }
    }
}
