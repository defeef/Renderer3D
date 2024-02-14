package math;

public class Plane {
    public double a;
    public double b;
    public double c;
    public double k;

    public Plane(double a, double b, double c, double k) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.k = k;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", k=" + k +
                '}';
    }
}
