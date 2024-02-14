package math;

public class Ray {
    public Vector3 origin;
    public Vector3 slope; //

    public Ray(Vector3 origin, Vector3 slope) {
        this.origin = origin;
        this.slope = slope;
    }

    public Vector3 getAtLambdaValue(double lambda) {
        return origin.add(slope.scalarMultiply(lambda));
    }

    @Override
    public String toString() {
        return "Ray{" +
                "origin=" + origin +
                ", slope=" + slope +
                '}';
    }
}
