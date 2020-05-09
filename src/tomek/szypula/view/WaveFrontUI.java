package tomek.szypula.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import tomek.szypula.controller.Highlighted;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;
import tomek.szypula.models.WaveFront;

import java.util.concurrent.Callable;

public class WaveFrontUI implements CreateUI{
    WaveFront wavefront;
    Polygon triangle;
    private final double height = 8;
    private Line lineShape = new Line();
    private Text text = new Text();

    private double maxColor = 240 ;///LightGreen
    DropShadow dropShadow = new DropShadow();

    ObservableList<Vector2D> anchorsForTriangleCoordinates;

    public WaveFrontUI(WaveFront wavefront){
        this.wavefront = wavefront;

        triangle = new Polygon();
        triangle.getPoints().addAll(
                new Double[]{
                        1.0,2.0,3.0,4.0,5.0,6.0
                });

        anchorsForTriangleCoordinates = Vector2DMath.createControlVectorsForPoints(triangle.getPoints());

        ChangeListener<Number> waveFrontChangeListener = (ChangeListener<Number>) (observableValue, number, t1) -> updateTriangle();

        wavefront.getPosition().yProperty().addListener(waveFrontChangeListener);
        wavefront.getPosition().xProperty().addListener(waveFrontChangeListener);

        ObjectBinding<Color> colorBinding = Bindings.createObjectBinding(
                new Callable<Color>() {
                    @Override
                    public Color call() throws Exception {
                        Color color;
                        if (wavefront.equals(Highlighted.getHighlightedWaveFront())) {
                            color = Color.hsb(360, 0.94, 0.94, 0.94);
                            dropShadow.setColor(Color.ROYALBLUE);
                        }
                        else
                            color = Color.hsb(Math.min(-maxColor *wavefront.getSpeed().getLength()  / wavefront.getCar().getParameters().getDesiredSpeed()+maxColor, 360),0.94,0.74,0.64);
                        dropShadow.setColor(Color.BLACK);
                        return color;
                    }
                },wavefront.getSpeed().xProperty(),wavefront.getSpeed().yProperty(),Highlighted.isChangeWaveFrontProperty()
        );
        triangle.fillProperty().bind(colorBinding);

        dropShadow.setRadius(5);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);

        ObjectBinding<String> speedValueBinding = Bindings.createObjectBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return String.format("%.1f",wavefront.getSpeed().getLength());
            }
        },wavefront.getSpeed().xProperty(),wavefront.getSpeed().yProperty());

        text.textProperty().bind(speedValueBinding);
        text.xProperty().bind(anchorsForTriangleCoordinates.get(1).xProperty());
        text.yProperty().bind(anchorsForTriangleCoordinates.get(1).yProperty());

        EventHandler<MouseEvent> mouseEventEventHandler = mouseEvent -> wavefront.highlight();

        triangle.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEventEventHandler);
    }

    private void updateTriangle() {
        double size = wavefront.getCar().getSize();
        Vector2D position = wavefront.getPosition();
        Vector2D direction = wavefront.getDirectionCopy().normalize().multiply(size*1.5);
        Vector2D normal = Vector2DMath.getNormalVector2D(direction).normalize();

        Vector2D point1 = Vector2DMath.vector2DSum(position,Vector2DMath.multiplyVector2D(normal,size));
        Vector2D point2 = Vector2DMath.vector2DSum(position,Vector2DMath.multiplyVector2D(normal,size*4)).addVector2D(direction);
        Vector2D point3 = Vector2DMath.vector2DSum(position,Vector2DMath.multiplyVector2D(normal,size*4)).addVector2D(direction.multiply(-1));

        ObservableList<Vector2D> points = FXCollections.observableArrayList();
        points.addAll(point1,point2,point3);

        for (int i = 0; i < 3; i++) {
            anchorsForTriangleCoordinates.get(i).setVector(points.get(i));
        }

    }

    @Override
    public void createUI(Group parent) {
        //parent.getChildren().add(lineShape);
        parent.getChildren().add(text);
        parent.getChildren().add(triangle);

    }
    @Override
    public void remove(Group parent) {
        parent.getChildren().remove(text);
        //parent.getChildren().remove(lineShape);
        parent.getChildren().remove(triangle);
    }

    public WaveFront getWavefront() {
        return wavefront;
    }
}
