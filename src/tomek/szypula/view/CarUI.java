package tomek.szypula.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import tomek.szypula.math.Vector2D;
import tomek.szypula.models.Car;

import java.util.concurrent.Callable;

public class CarUI implements CreateUI{
    private Car car;
    private Circle carShape;
    private double maxColor = 140 ;///LightGreen

    public CarUI(Car car) {
        this.car = car;
        carShape  = new Circle();
        carShape.setCenterX(car.getX());
        carShape.setCenterY(car.getY());
        carShape.setRadius(car.getSize());
        carShape.radiusProperty().bind(car.getParameters().sizeProperty());
        carShape.centerXProperty().bind(car.getXProperty());
        carShape.centerYProperty().bind(car.getYProperty());
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        carShape.setStroke(Color.BLACK);
        carShape.setEffect(dropShadow);
        carShape.setFill(Color.GREEN);
        ObjectBinding<Color> colorObjectBinding1 = Bindings.createObjectBinding(
                new Callable<Color>() {
                    @Override
                    public Color call() throws Exception {
                        double speedX = car.getSpeed().getX();
                        double speedY = car.getSpeed().getY();
                        Color color = Color.hsb(Math.sqrt(speedX*speedX+speedY*speedY)*maxColor/car.getParameters().getDesiredSpeed(),0.94,0.94,0.94);
                        return color;
                    }
                },car.getSpeed().xProperty(),car.getSpeed().yProperty(),car.getParameters().desiredSpeedProperty()
        );
        carShape.fillProperty().bind(colorObjectBinding1);
    }

    public Car getCar(){return car;}

    public Circle getCarShape(){return carShape;}

    @Override
    public void createUI(Group parent) {
        parent.getChildren().add(carShape);
    }
}
