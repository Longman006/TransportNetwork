package tomek.szypula.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class CarParameters {
    /**
     * 
     */
    private DoubleProperty desiredSpeed = new SimpleDoubleProperty(0);


    /**
     *
     */
    private DoubleProperty timeGap = new SimpleDoubleProperty(0);
    /**
     *
     */
    private DoubleProperty minimumGap = new SimpleDoubleProperty(0);

    /**
     * Acceleration, the comfortable acceleration
     */
    private DoubleProperty acceleration = new SimpleDoubleProperty(0);    /**
     * Comfortable deceleration
     */
    private DoubleProperty deceleration = new SimpleDoubleProperty(0);    /**
     * the speed of reaching the desired speed
     */
    private DoubleProperty accelerationExponent = new SimpleDoubleProperty(0);    /**
     * 
     */
    private DoubleProperty size = new SimpleDoubleProperty(0);

    public CarParameters() {
        this(12.0, 2.0, 2.0, 3.0,1.5 ,4.0, 3);
    }

    /**
     *  @param desiredSpeed Desired speed in km/h
     * @param timeGap desired safe time gap (breaking time gap ) in s
     * @param minimumGap Minimum gap , saf distance in meters
     * @param acceleration Acceleration, the comfortable acceleration
     * @param deceleration Comfortable deceleration
     * @param accelerationExponent the speed of reaching the desired speed
     * @param size the length of car in meters
     */

    public CarParameters(double desiredSpeed, double timeGap, double minimumGap, double acceleration, double deceleration, double accelerationExponent, double size) {
        setDesiredSpeed(desiredSpeed);
        setTimeGap(timeGap);
        setMinimumGap(minimumGap);
        setAcceleration(acceleration);
        setDeceleration(deceleration);
        setAcceleration(accelerationExponent);
        setSize(size);
    }

    public double getDesiredSpeed() {
        return desiredSpeed.get();
    }

    public DoubleProperty desiredSpeedProperty() {
        return desiredSpeed;
    }

    public void setDesiredSpeed(double desiredSpeed) {
        this.desiredSpeed.set(desiredSpeed);
    }

    public double getTimeGap() {
        return timeGap.get();
    }

    public DoubleProperty timeGapProperty() {
        return timeGap;
    }

    public void setTimeGap(double timeGap) {
        this.timeGap.set(timeGap);
    }

    public double getMinimumGap() {
        return minimumGap.get();
    }

    public DoubleProperty minimumGapProperty() {
        return minimumGap;
    }

    public void setMinimumGap(double minimumGap) {
        this.minimumGap.set(minimumGap);
    }

    public double getAcceleration() {
        return acceleration.get();
    }

    public DoubleProperty accelerationProperty() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration.set(acceleration);
    }

    public double getDeceleration() {
        return deceleration.get();
    }

    public DoubleProperty decelerationProperty() {
        return deceleration;
    }

    public void setDeceleration(double deceleration) {
        this.deceleration.set(deceleration);
    }

    public double getAccelerationExponent() {
        return accelerationExponent.get();
    }

    public DoubleProperty accelerationExponentProperty() {
        return accelerationExponent;
    }

    public void setAccelerationExponent(double accelerationExponent) {
        this.accelerationExponent.set(accelerationExponent);
    }

    public double getSize() {
        return size.get();
    }

    public DoubleProperty sizeProperty() {
        return size;
    }

    public void setSize(double size) {
        this.size.set(size);
    }
}
