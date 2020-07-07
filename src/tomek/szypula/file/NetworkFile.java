package tomek.szypula.file;

import tomek.szypula.math.Vector2D;
import tomek.szypula.models.Model;
import tomek.szypula.models.Road;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NetworkFile extends FileManager {

    //This file is supposed to be updated only once on trigger of file save by the user, leaving update method empty
    NetworkFile(Model model) {
        super("", "Network", model);
    }

    @Override
    void setupFile() {
        super.setupFile();
        List<Road> roadList = model.getRoadList();
        //Adding all nodes to a set to eliminate duplicates
        Set<Vector2D> vector2DSet = new HashSet<>();

        for (Road road :
                roadList) {
            vector2DSet.add(road.getStart());
            vector2DSet.add(road.getEnd());
        }

        //List of all the nodes, Saving the positions and separating by an empty line from the neighboorhood matrix
        List<Vector2D> vector2DList = new ArrayList<>(vector2DSet);

        for (Vector2D node :
                vector2DList) {
            dataWriter.updateFile(String.valueOf(node.getX()),String.valueOf(node.getY()));
        }
        dataWriter.updateFile("");

        //Creating the neighboorhood matrix
        for (Vector2D node :
                vector2DList) {
            List<String> matrixRow = new ArrayList<>();
            for (Vector2D nextNode :
                    vector2DList) {
                for (int i = 0; i < roadList.size(); i++) {
                    Road road = roadList.get(i);
                    if (road.getStart().equals(node) && road.getEnd().equals(nextNode)){
                        matrixRow.add(String.valueOf(1));
                        break;
                    }
                    if (i == roadList.size()-1)
                        matrixRow.add(String.valueOf(0));
                }
            }
            //Writing to file one row of the neighboorhood matrix
            dataWriter.updateFile(matrixRow.toArray(new String[0]));
        }

    }

    @Override
    void updateFile() {

    }
}
