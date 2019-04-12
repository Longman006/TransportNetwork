package tomek.szypula.math;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Iterator;

public class Vector2D implements Iterable<DoubleProperty>{

    private DoubleProperty xProperty = new SimpleDoubleProperty(0);
    private DoubleProperty yProperty = new SimpleDoubleProperty(0);

    public Vector2D(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }

    public Vector2D(final Vector2D v) {
        this(v.getX(), v.getY());
    }

    public Vector2D(){this(0,0); }

    public Vector2D(Vector2D v1, Vector2D v2) {
        this(v2.getX() - v1.getX(), v2.getY() - v1.getY());
    }
    public Vector2D normalize(){
        if(!isZero()) {
            double normFactor = getNormFactor();
            setX(getX() / normFactor);
            setY(getY() / normFactor);
        }
        return this;
    }
    private double getNormFactor(){ return Math.sqrt(getLengthSquare());
    }
    public double getX() {
        return xProperty.getValue();
    }

    public void setX(double x) {  this.xProperty.setValue(x);   }

    public double getY() {
        return yProperty.getValue();
    }

    public void setY(double y) {
        this.yProperty.setValue(y);
    }


    public double getLengthSquare() {
        return getX()*getX() + getY()*getY();
    }
    public double getLength(){ return Math.sqrt(getLengthSquare());}

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

    public void setVector(Vector2D vector2D) {
        setY(vector2D.getY());
        setX(vector2D.getX());
    }

    public DoubleProperty getXProperty() {
        return xProperty;
    }
    public DoubleProperty getYProperty() {
        return yProperty;
    }
    public boolean isZero(){
        return (getX() == 0 && getY() == 0);
    }

    @Override
    public Iterator<DoubleProperty> iterator() {
        return new Iterator<DoubleProperty>() {
            private int current = 0 ;

            @Override
            public boolean hasNext() {
                return current<2;
            }

            @Override
            public DoubleProperty next() {
                current++;
                if(current == 1)
                    return getXProperty();
                else
                    return getYProperty();
            }
        };
    }

}
