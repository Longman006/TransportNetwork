package tomek.szypula.math;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Vector2D {

    private double x = 0;
    private double y = 0;

    //TODO Change doubles to propertiees
    private DoubleProperty xProperty = new SimpleDoubleProperty(0);
    private DoubleProperty yProperty = new SimpleDoubleProperty(0);

    public Vector2D(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Vector2D(final Vector2D v) {
        this(v.x, v.y);
    }

    public Vector2D(){this(0,0); }

    public Vector2D(Vector2D v1, Vector2D v2) {
        this(v2.x - v1.x, v2.y - v1.y);
    }
    public Vector2D normalize(){
        double normFactor = getNormFactor();
        setX(getX()/normFactor);
        setY(getY()/normFactor);
        return this;
    }
    private double getNormFactor(){ return Math.sqrt(getLengthSquare());
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public double getLengthSquare() {
        return getX()*getX() + getY()*getY();
    }

    public Vector2D addVector2D(Vector2D vector2D){
        this.setX(this.getX()+vector2D.getX());
        this.setY(this.getY()+vector2D.getY());
        return this;
    }
    public Vector2D equalsVector2D(Vector2D vector2D){
        this.setY(vector2D.getY());
        this.setX(vector2D.getX());
        return vector2D;
    }

    public Vector2D multiply(double multiplier) {
        this.setY(getY()*multiplier);
        this.setX(getX()*multiplier);
        return this;
    }

    public void setPosition(Vector2D vector2D) {
        setY(vector2D.getY());
        setX(vector2D.getX());
    }

    public DoubleProperty getXProperty() {
        return xProperty;

    }
}
