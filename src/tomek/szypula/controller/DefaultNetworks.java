package tomek.szypula.controller;

import tomek.szypula.math.LineSegment;
import tomek.szypula.math.Vector2D;
import tomek.szypula.models.Road;

import java.util.ArrayList;
import java.util.List;

public class DefaultNetworks {
    private int x = 30;
    List<Road> roads = new ArrayList<>();

    public DefaultNetworks() {
        createRoads();
        createRoadNetwork();
    }

    public List<Road> loadDefaultNetwork(List<Road> roads) {
        roads.removeAll(roads);
        roads.addAll(this.roads);
        return roads;
    }

    private void createRoads(){
        Vector2D a,b,c,d,e,f,g,h,i;

        a = new Vector2D(x,x);
        b = new Vector2D(x,7*x);
        c = new Vector2D(x,13*x);
        d = new Vector2D(6*x,x);
        e = new Vector2D(6*x,7*x);
        f = new Vector2D(6*x,13*x);
        g = new Vector2D(25*x,13*x);
        h = new Vector2D(25*x,7*x);
        i = new Vector2D(25*x,x);

        roads.add(new Road(new LineSegment(b,a)));
        roads.add(new Road(new LineSegment(b, c)));
        roads.add( new Road(new LineSegment(b,e)));
        roads.add(new Road(new LineSegment(a,d)));
        roads.add(new Road(new LineSegment(c,f)));
        roads.add(new Road(new LineSegment(f,e)));
        roads.add(new Road(new LineSegment(d,e)));
        roads.add(new Road(new LineSegment(g,f)));
        roads.add(new Road(new LineSegment(h,g)));
        roads.add(new Road(new LineSegment(h,i)));
        roads.add(new Road(new LineSegment(e,h)));
        roads.add(new Road(new LineSegment(i,d)));
    }
    private void createRoadNetwork(){
        for (Road road : roads ) {
            for (Road roadNext : roads){
                if (road.getEnd().equalValue(roadNext.getStart()))
                    road.addRoad(roadNext);
                else if ( road.getStart().equalValue(roadNext.getEnd()))
                    road.addPreviousRoad(roadNext);
            }
        }
    }
}
