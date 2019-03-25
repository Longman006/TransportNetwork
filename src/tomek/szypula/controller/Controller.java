package tomek.szypula.controller;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import tomek.szypula.math.LineSegment;
import tomek.szypula.math.Vector2D;
import tomek.szypula.models.Road;
import tomek.szypula.view.CreateUI;
import tomek.szypula.view.RoadUI;

import java.awt.image.RasterOp;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    List<Road> roads = new ArrayList<>();
    List<CreateUI> UIelements = new ArrayList<>();

    public Controller(Group parent) {
        roads.add(new Road(new LineSegment(new Vector2D(), new Vector2D(0,350))));
        roads.add(new Road(new LineSegment(new Vector2D(0,350), new Vector2D(0,700))));
        roads.add(new Road(new LineSegment(new Vector2D(0,350), new Vector2D(301,350))));
        roads.add(new Road(new LineSegment(new Vector2D(0,700), new Vector2D(300,700))));
        roads.add(new Road(new LineSegment(new Vector2D(300,350), new Vector2D(300,700))));
        roads.add(new Road(new LineSegment(new Vector2D(300,350), new Vector2D(300,0))));
        roads.add(new Road(new LineSegment(new Vector2D(300,0), new Vector2D(1000,0))));
        roads.add(new Road(new LineSegment(new Vector2D(300,700), new Vector2D(1000,700))));
        roads.add(new Road(new LineSegment(new Vector2D(1000,700), new Vector2D(1000,0))));

        for(Road road : roads){
            UIelements.add(new RoadUI(road));
        }

        for (CreateUI createUI : UIelements){
            createUI.createUI(parent);
        }
    }
}