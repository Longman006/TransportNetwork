package tomek.szypula.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;
import tomek.szypula.models.Road;

import java.util.concurrent.Callable;

public class TrafficLightsUI implements CreateUI {
    private Road road;
    private Circle trafficLightsShape;
    private double size = 7.0;
    private double shiftParallel = 25.0;
    private double shiftNormal = 15;
    public TrafficLightsUI(Road road) {
        this.road = road;
        trafficLightsShape = new Circle();

        //TODO Finish a black rectangle around the circle
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        trafficLightsShape.setStroke(Color.BLACK);
        trafficLightsShape.setEffect(dropShadow);
        trafficLightsShape.setFill(Color.GREEN);

        Vector2D end = road.getEndCopy();
        Vector2D direction = road.getLineSegment().getDirection().multiply(-1).multiply(shiftParallel);
        Vector2D offset = Vector2DMath.getNormalVector2D(direction).normalize().multiply(-1*shiftNormal);
        Vector2D lightsPosition = Vector2DMath.vector2DSum(end,direction).addVector2D(offset);
        trafficLightsShape.setCenterX(lightsPosition.getX());
        trafficLightsShape.setCenterY(lightsPosition.getY());
        trafficLightsShape.setRadius(size);

        ObjectBinding<Color> colorObjectBinding = Bindings.createObjectBinding(
                new Callable<Color>() {
                    @Override
                    public Color call() throws Exception {
                        Color color ;
                        if (road.getTrafficLightsEnd().isStop()){
                            color = Color.CRIMSON;
                            DropShadow rollOverColor = new DropShadow();
                            rollOverColor.setColor(Color.ORANGERED);
                            rollOverColor.setRadius(10);
                            trafficLightsShape.setEffect(rollOverColor);
                        }
                        else {
                            color = Color.LIMEGREEN;
                            DropShadow rollOverColor = new DropShadow();
                            rollOverColor.setColor(Color.GREEN);
                            rollOverColor.setRadius(10);
                            trafficLightsShape.setEffect(rollOverColor);
                        }
                        return color;
                    }
                },road.getTrafficLightsEnd().stopProperty()
        );
        trafficLightsShape.fillProperty().bind(colorObjectBinding);

        //Creating the mouse event handler
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                road.getTrafficLightsEnd().switchLights();
            }
        };
        //Registering the event filter
        trafficLightsShape.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);


    }

    @Override
    public void createUI(Group parent) {
        parent.getChildren().add(trafficLightsShape);
    }

    @Override
    public void remove(Group parent) {
        parent.getChildren().remove(trafficLightsShape);
    }
}
