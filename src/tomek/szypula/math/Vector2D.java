package tomek.szypula.math;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Iterator;

public class Vector2D implements Iterable<DoubleProperty>{

    private DoubleProperty x = new SimpleDoubleProperty(0);
    private DoubleProperty y = new SimpleDoubleProperty(0);

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
        return x.getValue();
    }

    public void setX(double x) {  this.x.setValue(x);   }

    public double getY() {
        return y.getValue();
    }

    public void setY(double y) {
        this.y.setValue(y);
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

    public DoubleProperty xProperty() {
        return x;
    }
    public DoubleProperty yProperty() {
        return y;
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
                    return xProperty();
                else
                    return yProperty();
            }
        };
    }

    public boolean equalValue(Vector2D isEqual) {
        double epsilon = Double.MIN_NORMAL;
        if (Math.abs(this.getY() - isEqual.getY()) < epsilon && Math.abs(this.getX() - isEqual.getX()) < epsilon)
            return true;
        return false;
    }

    public Vector2D subtractVector2D(Vector2D vector2D) {
        return this.addVector2D(new Vector2D(vector2D).multiply(-1));
    }
}
