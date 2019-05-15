package tomek.szypula.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;
import tomek.szypula.models.Road;

import java.util.concurrent.Callable;

public class TrafficLightsUI implements CreateUI {
    private Road road;
    private Circle trafficLightsShape;
    private double size = 4.0;
    private double shiftParallel = 25.0;
    private double shiftNormal = 15;
    private Rectangle trafficLightsRectangle;

    public TrafficLightsUI(Road road) {
        this.road = road;
        trafficLightsShape = new Circle();
        trafficLightsRectangle = new Rectangle();
        //TODO Finish a black rectangle around the circle
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        trafficLightsShape.setStroke(Color.BLACK);
        trafficLightsShape.setEffect(dropShadow);
        trafficLightsShape.setFill(Color.GREEN);

        Vector2D end = road.getEnd();
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
                        Color color = Color.BLACK;
                        if (road.getTrafficLightsEnd().isStop()){
                            color = Color.CRIMSON;
                        }
                        else {
                            color = Color.LIMEGREEN;
                        }
                        return color;
                    }
                },road.getTrafficLightsEnd().stopProperty()
        );
        trafficLightsShape.fillProperty().bind(colorObjectBinding);


    }

    @Override
    public void createUI(Group parent) {

        parent.getChildren().add(trafficLightsShape);
    }

    @Override
    public Paint getfill() {
        return trafficLightsShape.getFill();
    }

    @Override
    public ObjectProperty<Paint> getfillProperty() {
        return trafficLightsShape.fillProperty();    }
}
