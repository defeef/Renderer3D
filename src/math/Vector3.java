package math;

public class Vector3 {
    public static final Vector3 ZERO = new Vector3(0, 0, 0);
    public static final Vector3 FORWARD = new Vector3(0, 0, 1);
    public static final Vector3 BACKWARD = new Vector3(0, 0, -1);
    public static final Vector3 LEFT = new Vector3(-1, 0, 0);
    public static final Vector3 RIGHT = new Vector3(1, 0, 0);
    public static final Vector3 UP = new Vector3(0, 1, 0);
    public static final Vector3 DOWN = new Vector3(0, 1, 0);

    public double x;
    public double y;
    public double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 add(Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    public Vector3 subtract(Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    public Vector3 scalarMultiply(double scalar) {
        return new Vector3(x * scalar, y * scalar, z * scalar);
    }

    public Vector3 cross(Vector3 other) {
        return new Vector3(y * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x);
    }

    public double dot(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3 vectorTo(Vector3 other) {
        return other.subtract(this);
    }

    public Vector3 rotY(double theta) {
        return new Vector3(x * Math.cos(theta) + z * Math.sin(theta),
                y,
                -x * Math.sin(theta) + z * Math.cos(theta));
    }

    public Vector3 rotX(double theta) {
        return new Vector3(x,
                y * Math.cos(theta) - z * Math.sin(theta),
                y * Math.sin(theta) - z * Math.cos(theta));
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
