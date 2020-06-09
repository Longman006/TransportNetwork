package tomek.szypula.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import tomek.szypula.controller.Highlighted;
import tomek.szypula.math.Vector2D;
import tomek.szypula.models.Car;
import java.util.concurrent.Callable;
import java.util.jar.JarOutputStream;

public class CarUI implements CreateUI{
    private Car car;
    private Circle carShape;
    private double maxColor = 240 ;///LightGreen
    DropShadow dropShadow = new DropShadow();

    public CarUI(Car car) {
        this.car = car;
        carShape  = new Circle();
        carShape.setCenterX(car.getX());
        carShape.setCenterY(car.getY());
        carShape.setRadius(car.getSize());
        carShape.radiusProperty().bind(car.getParameters().sizeProperty());
        carShape.centerXProperty().bind(car.getXProperty());
        carShape.centerYProperty().bind(car.getYProperty());
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        carShape.setStroke(Color.BLACK);
        carShape.setEffect(dropShadow);
        carShape.setFill(Color.GREEN);
        ObjectBinding<Color> colorObjectBinding1 = Bindings.createObjectBinding(
                new Callable<Color>() {
                    @Override
                    public Color call() throws Exception {
                        Color color;
                        if (car.equals(Highlighted.getHighlightedCar())) {
                            color = Color.hsb(Math.min((maxColor+40 -360 ) *car.getSpeed().getLength()  / car.getParameters().getDesiredSpeed()+360, 360), 0.94, 0.94, 0.94);
                            dropShadow.setColor(Color.ROYALBLUE);
                        }
                        else
                            color = Color.hsb(Math.min(-maxColor *car.getSpeed().getLength()  / car.getParameters().getDesiredSpeed()+maxColor, 360),0.94,0.94,0.94);
                        dropShadow.setColor(Color.BLACK);
                        return color;
                    }
                },car.getSpeed().xProperty(),car.getSpeed().yProperty(),car.getParameters().desiredSpeedProperty(),Highlighted.isChangeCarProperty()
        );
        carShape.fillProperty().bind(colorObjectBinding1);

        EventHandler<MouseEvent> mouseEventEventHandler = new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent mouseEvent) {
                car.highlight();
                System.out.println("SD Index : "+car.getSpeedDensityIndex());
            }
        };

        carShape.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEventEventHandler);
    }

    public Car getCar(){return car;}

    public Circle getCarShape(){return carShape;}

    @Override
    public void createUI(Group parent) {
        parent.getChildren().add(carShape);
    }

    @Override
    public void remove(Group parent) {
        parent.getChildren().remove(carShape);
    }
}
