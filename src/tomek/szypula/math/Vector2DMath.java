package tomek.szypula.math;

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


}
