package tomek.szypula.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;
import tomek.szypula.models.WaveFront;

import java.util.concurrent.Callable;

public class WaveFrontUI implements CreateUI{
    WaveFront wavefront;
    private final double height = 8;
    private Line lineShape = new Line();
    private Text text = new Text();

    public WaveFrontUI(WaveFront wavefront){
        this.wavefront = wavefront;
        ObjectBinding<Double> startxBinding = Bindings.createObjectBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                Vector2D normal = Vector2DMath.getNormalVector2D(wavefront.getSpeed()).normalize();
                double x = wavefront.getPosition().getX() + normal.getX()*-height;
                return x;
            }
        },wavefront.getPosition().xProperty());
        ObjectBinding<Double> startyBinding = Bindings.createObjectBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                Vector2D normal = Vector2DMath.getNormalVector2D(wavefront.getSpeed()).normalize();
                double y = wavefront.getPosition().getY() + normal.getY()*-height;
                return y;
            }
        },wavefront.getPosition().yProperty());
        ObjectBinding<Double> endyBinding = Bindings.createObjectBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                Vector2D normal = Vector2DMath.getNormalVector2D(wavefront.getSpeed()).normalize();
                double y = wavefront.getPosition().getY() + normal.getY()*height;
                return y;
            }
        },wavefront.getPosition().yProperty());
        ObjectBinding<Double> endxBinding = Bindings.createObjectBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                Vector2D normal = Vector2DMath.getNormalVector2D(wavefront.getSpeed()).normalize();
                double x = wavefront.getPosition().getX() + normal.getX()*height;
                return x;
            }
        },wavefront.getPosition().xProperty());

        lineShape.startXProperty().bind(startxBinding);
        lineShape.startYProperty().bind(startyBinding);
        lineShape.endXProperty().bind(endxBinding);
        lineShape.endYProperty().bind(endyBinding);
        lineShape.setStrokeWidth(3);

        ObjectBinding<String> speedValueBinding = Bindings.createObjectBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return String.format("%.2f",wavefront.getSpeed().getLength());
            }
        },wavefront.getSpeed().xProperty(),wavefront.getSpeed().yProperty());

        text.textProperty().bind(speedValueBinding);
        text.xProperty().bind(startxBinding);
        text.yProperty().bind(startyBinding);
    }
    @Override
    public void createUI(Group parent) {
        parent.getChildren().add(lineShape);
        parent.getChildren().add(text);

    }
    @Override
    public void remove(Group parent) {
        parent.getChildren().remove(text);
        parent.getChildren().remove(lineShape);
    }

    public WaveFront getWavefront() {
        return wavefront;
    }
}
