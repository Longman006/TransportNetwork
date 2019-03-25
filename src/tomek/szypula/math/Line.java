package tomek.szypula.math;

import java.util.Vector;

/**
 * Created by longman on 04.06.17.
 */
public class Line {

    /**
     * Współczynniki prostej Ax+By+C=0
     * tego tak naprawde używam
     */
    private double AA,BB,CC;
    /**
     * Vector noramlny
     */
    private Vector2D normal;

    /**
     * Postac parametryczna r = r0 +t*u
     */
    private Vector2D parallel;
    private Vector2D shift;

    private void setNormal(Vector2D normal) {
        this.normal = normal;
    }

    private void setParallel(Vector2D parallel) {
        this.parallel = parallel;
    }

    private void setShift(Vector2D shift) {
        this.shift = shift;
    }

    public Line(double AA, double BB, double CC) {
        this.AA = AA;
        this.BB = BB;
        this.CC = CC;
        setNormal(new Vector2D(AA,BB));
        setParallel(new Vector2D(-BB,AA));
        setShift(new Vector2D(0,-CC/BB));
    }

    public Line(Vector2D point1, Vector2D point2){
        this.AA = -point2.getY()+point1.getY();
        this.BB = point2.getX()-point1.getX();
        //this.CC = -BB*point1.getY()-AA*point1.getX();
        this.CC = AA*point1.getX()+BB*point1.getY();
        setNormal(new Vector2D(AA,BB));
        setParallel(new Vector2D(point2,point1));
        setShift(new Vector2D(point1));
    }

    public double getAA() {
        return AA;
    }

    public double getBB() {
        return BB;
    }

    public double getCC() {
        return CC;
    }

    public Vector2D getClosestPointOnLine(Vector2D point0){
        Line perpendicualar = new Line(-BB,AA,-BB*point0.getX()+AA*point0.getY());
        double norm = AA*AA + BB*BB;
        double xx = (CC*AA-perpendicualar.getCC()*BB)/norm;
        double yy = (getCC()*AA-CC*BB)/norm;
        return new Vector2D(xx,yy);



    }

    public String toString(){
        return "Line AA : "+AA+" BB : "+BB+" CC : "+CC;
    }

}
