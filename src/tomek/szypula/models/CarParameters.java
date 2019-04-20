package tomek.szypula.models;

public class CarParameters {
    /**
     * 
     */
    private double desiredSpeed;

    /**
     *
     */
    private double timeGap;
    /**
     *
     */
    private double minimumGap;
    /**
     * Acceleration, the comfortable acceleration
     */
    private double acceleration;
    /**
     * Comfortable deceleration
     */
    private double deceleration;
    /**
     * the speed of reaching the desired speed
     */
    private double accelerationExponent;
    /**
     * 
     */
    private double size;

    public CarParameters() {
        this(15.0, 1.0, 2.0, 1.0,1.5 ,4.0, 7.5);
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
        this.desiredSpeed = desiredSpeed;
        this.timeGap = timeGap;
        this.minimumGap = minimumGap;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.accelerationExponent = accelerationExponent;
        this.size = size;
    }
    public double getDesiredSpeed() {
        return desiredSpeed;
    }

    public double getTimeGap() {
        return timeGap;
    }

    public double getMinimumGap() {
        return minimumGap;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getDeceleration() {
        return deceleration;
    }

    public double getAccelerationExponent() {
        return accelerationExponent;
    }

    public double getSize() {
        return size;
    }
}
