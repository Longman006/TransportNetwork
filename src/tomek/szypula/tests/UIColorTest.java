package tomek.szypula.tests;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tomek.szypula.math.Vector2D;

import java.util.concurrent.Callable;

public class UIColorTest {

    Vector2D speed;
    Circle carShape = new Circle();

    public UIColorTest(Vector2D speed) {
        this.speed = speed;
        carShape.setCenterX(100);
        carShape.setCenterY(100);
        carShape.setRadius(10);
        ObjectBinding<Color> colorObjectBinding1 = Bindings.createObjectBinding(
                new Callable<Color>() {
                    @Override
                    public Color call() throws Exception {
                        double speedX = speed.getX();
                        double speedY = speed.getY();
                        System.out.println("speed : "+speed);
                        Color color = Color.hsb(Math.sqrt(speedX*speedX+speedY*speedY)*110/10,0.94,0.94,0.94);
                        return color;
                    }
                },this.speed.xProperty(),this.speed.yProperty()
        );
        carShape.fillProperty().bind(colorObjectBinding1);
    }
    public void createUI(Group parent){
        parent.getChildren().add(carShape);
    }
}
