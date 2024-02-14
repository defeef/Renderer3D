package graphics;

import math.Vector3;

public class Triangle {
    public Vector3 t1;
    public Vector3 t2;
    public Vector3 t3;

    public Triangle(Vector3 t1, Vector3 t2, Vector3 t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
    }

    public boolean inBoundingBox(Vector3 point) {
        /*return point.x > Math.min(t1.x, Math.min(t2.x, t3.x)) &&
                point.x < Math.max(t1.x, Math.max(t2.x, t3.x)) &&
                point.y > Math.min(t1.y, Math.min(t2.y, t3.y)) &&
                point.y < Math.max(t1.y, Math.max(t2.y, t3.y));// &&
                //point.z > Math.min(t1.z, Math.min(t2.z, t3.z));// &&
                //point.z < Math.max(t1.z, Math.max(t2.z, t3.z));
        */
        return !(point.x < Math.min(t1.x, Math.min(t2.x, t3.x)) ||
                point.x > Math.max(t1.x, Math.max(t2.x, t3.x)) ||
                point.y < Math.min(t1.y, Math.min(t2.y, t3.y)) ||
                point.y > Math.max(t1.y, Math.max(t2.y, t3.y)) &&
                point.z < Math.min(t1.z, Math.min(t2.z, t3.z)) &&
                point.z > Math.max(t1.z, Math.max(t2.z, t3.z)));
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "t1=" + t1 +
                ", t2=" + t2 +
                ", t3=" + t3 +
                '}';
    }
}
