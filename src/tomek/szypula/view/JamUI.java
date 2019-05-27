package tomek.szypula.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import tomek.szypula.math.Vector2D;
import tomek.szypula.math.Vector2DMath;
import tomek.szypula.models.Jam;

import java.util.concurrent.Callable;

public class JamUI implements CreateUI {

    private final double height = 3;
    private Line lineShape = new Line();
    private Jam jam;
    private Text text = new Text();
    Vector2D normal;


    public JamUI(Jam jam) {
        this.jam = jam;
        normal = Vector2DMath.getNormalVector2D(jam.getRoad().getLineSegment().getDirection()).normalize().multiply(height);

        Vector2D position =  jam.getPosition();


        ObjectBinding<Double> startxBinding = Bindings.createObjectBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                double x = jam.getPosition().getX() + normal.getX()*-height;
                return x;
            }
        },jam.getPosition().xProperty());
        ObjectBinding<Double> startyBinding = Bindings.createObjectBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                double y = jam.getPosition().getY() + normal.getY()*-height;
                return y;
            }
        },jam.getPosition().yProperty());
        ObjectBinding<Double> endyBinding = Bindings.createObjectBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                double y = jam.getPosition().getY() + normal.getY()*height;
                return y;
            }
        },jam.getPosition().yProperty());
        ObjectBinding<Double> endxBinding = Bindings.createObjectBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                double x = jam.getPosition().getX() + normal.getX()*height;
                return x;
            }
        },jam.getPosition().xProperty());

        lineShape.startXProperty().bind(startxBinding);
        lineShape.startYProperty().bind(startyBinding);
        lineShape.endXProperty().bind(endxBinding);
        lineShape.endYProperty().bind(endyBinding);
        lineShape.setStrokeWidth(3);

        text.setText("123");
        text.textProperty().bind(jam.massProperty().asString());
        text.xProperty().bind(position.xProperty().add(normal.getX()*-height));
        text.yProperty().bind(position.yProperty().add(normal.getY()*-height));

    }

    @Override
    public void createUI(Group parent) {
        //parent.getChildren().add(lineShape);
        parent.getChildren().add(text);

    }

    @Override
    public void remove(Group parent) {
        parent.getChildren().remove(text);
    }
}
