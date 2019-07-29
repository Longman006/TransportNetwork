package tomek.szypula.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;
import tomek.szypula.models.Wavefront;

import java.util.concurrent.Callable;

public class WaveFrontUI implements CreateUI{
    Wavefront wavefront;
    private final double height = 6;
    private Line lineShape = new Line();
    private Text text = new Text();

    public WaveFrontUI(Wavefront wavefront){
        this.wavefront = wavefront;
        ObjectBinding<Double> startxBinding = Bindings.createObjectBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                Vector2D normal = Vector2DMath.getNormalVector2D(wavefront.getSpeed());
                double x = wavefront.getPosition().getX() + normal.getX()*-height;
                return x;
            }
        },wavefront.getPosition().xProperty());
        ObjectBinding<Double> startyBinding = Bindings.createObjectBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                Vector2D normal = Vector2DMath.getNormalVector2D(wavefront.getSpeed());
                double y = wavefront.getPosition().getY() + normal.getY()*-height;
                return y;
            }
        },wavefront.getPosition().yProperty());
        ObjectBinding<Double> endyBinding = Bindings.createObjectBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                Vector2D normal = Vector2DMath.getNormalVector2D(wavefront.getSpeed());
                double y = wavefront.getPosition().getY() + normal.getY()*height;
                return y;
            }
        },wavefront.getPosition().yProperty());
        ObjectBinding<Double> endxBinding = Bindings.createObjectBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                Vector2D normal = Vector2DMath.getNormalVector2D(wavefront.getSpeed());
                double x = wavefront.getPosition().getX() + normal.getX()*height;
                return x;
            }
        },wavefront.getPosition().xProperty());

        lineShape.startXProperty().bind(startxBinding);
        lineShape.startYProperty().bind(startyBinding);
        lineShape.endXProperty().bind(endxBinding);
        lineShape.endYProperty().bind(endyBinding);
        lineShape.setStrokeWidth(3);

        ObjectBinding<Double> speedValueBinding = Bindings.createObjectBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return wavefront.getSpeed().getLength();
            }
        },wavefront.getSpeed().xProperty(),wavefront.getSpeed().yProperty());

        text.textProperty().bind(speedValueBinding.asString());
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

    public Wavefront getWavefront() {
        return wavefront;
    }
}
