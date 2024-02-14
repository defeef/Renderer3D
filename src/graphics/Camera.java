package graphics;

import math.Vector3;

public class Camera {
    public Vector3 position;
    public double pitch; // up/down
    public double yaw; // left/right

    public Camera(Vector3 position, double pitch, double yaw) {
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Camera(Vector3 position) {
        this(position, 0, 0);
    }

    public Camera(double yaw, double pitch) {
        this(Vector3.ZERO, yaw, pitch);
    }

    public Camera() {
        this(Vector3.ZERO, 0, 0);
    }

    @Override
    public String toString() {
        return "Camera{" +
                "position=" + position +
                ", pitch=" + pitch +
                ", yaw=" + yaw +
                '}';
    }
}
