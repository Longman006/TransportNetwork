package tomek.szypula.math;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by longman on 11.06.17.
 */
public final class Vector2DMath {
    private Vector2DMath() {
    }
    public static double scalarProduct(Vector2D v1,Vector2D v2){
        return v1.getY()*v2.getY()+v1.getX()*v2.getX();
    }
    public static Vector2D vector2DSum(Vector2D v1,Vector2D v2){
        return new Vector2D(v1.getX()+v2.getX(),v1.getY()+v2.getY());
    }
    public static Vector2D vector2DSubtract(Vector2D v1,Vector2D v2){
        return new Vector2D(-v1.getX()+v2.getX(),-v1.getY()+v2.getY());
    }
    public static Vector2D multiplyVector2D(Vector2D vector2D,double multiplier){
        return new Vector2D(vector2D).multiply(multiplier);
    }
    public static double distanceSquared(Vector2D vector2D1, Vector2D vector2D2){
        return (vector2D2.getY()-vector2D1.getY())*(vector2D2.getY()-vector2D1.getY())+(vector2D2.getX()-vector2D1.getX())*(vector2D2.getX()-vector2D1.getX());
    }
    public static double distance(Vector2D vector2D1, Vector2D vector2D2){
        return Math.sqrt(distanceSquared(vector2D1,vector2D2));
    }
    public static Vector2D getNormalVector2D(Vector2D vector2D){
        return new Vector2D(vector2D.getY(),-1*vector2D.getX());
    }
    public static boolean equalValue(double value1,double value2) {
        double epsilon = Double.MIN_NORMAL;
        if (Math.abs(value1 - value2) < epsilon )
            return true;
        return false;
    }
    public static double valueDifference(Vector2D v1, Vector2D v2){
        return v2.getLength()-v1.getLength();
    }

    // @return a list of anchors to modify points in the format [x1, y1, x2, y2...]
    public static ObservableList<Vector2D> createControlVectorsForPoints(final ObservableList<Double> points) {
        ObservableList<Vector2D> anchors = FXCollections.observableArrayList();

        for (int i = 0; i < points.size(); i+=2) {
            final int idx = i;
            Vector2D anchor = new Vector2D(points.get(i),points.get(i + 1));

            anchor.xProperty().addListener((ov, oldX, x) -> points.set(idx, (double) x));

            anchor.yProperty().addListener((ov, oldY, y) -> points.set(idx + 1, (double) y));

            anchors.add(anchor);
        }

        return anchors;
    }

}
