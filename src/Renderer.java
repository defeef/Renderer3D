import graphics.Camera;
import graphics.Triangle;
import math.Plane;
import math.Ray;
import math.Utils;
import math.Vector3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.*;

public class Renderer extends JPanel {
    //private final Thread thread;
    //private boolean running;
    public BufferedImage image;
    //public int[] pixels;

    public final int[] SCREEN_SIZE;
    //private static final int[] size = new int[]{640, 480};

    // distance of view plane from screen
    public double view_plane_dist;// = 1;
    public final double[] VIEW_PLANE_SIZE;// = new double[]{0.5, 0.5};
    public final double PIXEL_SIZE;
    public Camera camera;
    public InputManager inputManager;

    public Triangle testTri;

    class DrawListener implements ActionListener {
        public DrawListener() {};

        @Override
        public void actionPerformed(ActionEvent e) {
            frame++;

            if (inputManager != null) {
                processKeys();
            }

            //camera.pitch = (Math.cos((double) frame / 90) * .99) * 2;// / 2 + .5);
            //System.out.println(camera);
            //view_plane_dist = Math.cos((double) frame / 60) * .9999 / 2 + .5;
            repaint();
        }
    }

    public Renderer(Camera camera, Triangle test, int[] screen_size, double view_plane_dist, double view_plane_width, int target_fps) {
        SCREEN_SIZE = screen_size;
        this.view_plane_dist = view_plane_dist;
        VIEW_PLANE_SIZE = new double[2];
        PIXEL_SIZE = view_plane_width / SCREEN_SIZE[0];
        VIEW_PLANE_SIZE[0] = view_plane_width;
        VIEW_PLANE_SIZE[1] = PIXEL_SIZE * SCREEN_SIZE[1];
        this.camera = camera;
        this.testTri = test;

        //setBorder(BorderFactory.createLineBorder(Color.BLACK));

        image = new BufferedImage(SCREEN_SIZE[0], SCREEN_SIZE[1], BufferedImage.TYPE_INT_RGB);

        /*setSize(SCREEN_SIZE[0], SCREEN_SIZE[1]);
        setResizable(false);
        setTitle("3D Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);

        setUndecorated(true);
        //TODO: Make panel and add to frame (from website tutorial)

        setLocationRelativeTo(null);
        setVisible(true);*/

        Timer timer = new Timer(1000/target_fps, new DrawListener());
        timer.start();
    }

    int frame = -1;
    @Override
    public void paintComponent(Graphics g) {
        image = new BufferedImage(SCREEN_SIZE[0], SCREEN_SIZE[1], BufferedImage.TYPE_INT_RGB);

        double[] center = new double[]{(SCREEN_SIZE[0] - 1) / 2.0, (SCREEN_SIZE[1] - 1) / 2.0};

        Vector3 side_1 = testTri.t2.subtract(testTri.t1);
        Vector3 side_2 = testTri.t3.subtract(testTri.t1);
        //System.out.printf("%s %s\n", side_1, side_2);
        Vector3 cross = side_1.cross(side_2);
        Plane plane = new Plane(cross.x, cross.y, cross.z, -cross.dot(testTri.t1));
        //System.out.println(plane + "\n");

        //System.out.println(new Ray(camera.position, new Vector3(0, 0, 1).rotY(camera.yaw).rotX(camera.pitch)));

        for (int i = 0; i < SCREEN_SIZE[0]; i++) {
            for (int j = 0; j < SCREEN_SIZE[1]; j++) {
                //image.setRGB(i, j, new Color(0, 0, frame % 256).getRGB());
                //image.setRGB(i, j, new Color((int)((double)i/SCREEN_SIZE[0]*255), (int)((double)j/SCREEN_SIZE[1]*255), 0).getRGB());



                Vector3 slope = new Vector3(PIXEL_SIZE * (i - center[0]), PIXEL_SIZE * (j - center[1]), view_plane_dist);
                Ray ray = new Ray(camera.position, slope.rotY(camera.yaw).rotX(camera.pitch));

                if ((i == 0 && j == 0) || (i == SCREEN_SIZE[0]-1 && j == 0) || (i == 0 && j == SCREEN_SIZE[1]-1) || (i == SCREEN_SIZE[0]-1 && j == SCREEN_SIZE[1]-1)) {
                    //System.out.printf("(%d, %d) - %s\n", i, j, ray);
                }

                Vector3 abc = new Vector3(plane.a, plane.b, plane.c);
                double lambda = (abc.dot(ray.origin) + plane.k) / (abc).dot(ray.slope);

                Vector3 intersectionPoint = ray.origin.add(ray.slope.scalarMultiply(lambda));

                /*if (testTri.inBoundingBox(intersectionPoint)) {
                    System.out.printf("l: %f, bb: %d, i: %s\n", lambda, testTri.inBoundingBox(intersectionPoint) ? 1 : 0, intersectionPoint);
                }*/

                //System.out.println("i: " + intersectionPoint);
                //image.setRGB(i, j, new Color((int)intersectionPoint.x%256+128, (int)intersectionPoint.y%256+128, (int)intersectionPoint.z%256+128).getRGB());

                //TODO:image.setRGB(i, j, new Color(Math.min((int)camera.position.vectorTo(intersectionPoint).length() * 8, 255), 0, 0).getRGB());

                if (lambda >= 0) {// && testTri.inBoundingBox(intersectionPoint)) {
                    Vector3 v1 = testTri.t2.subtract(testTri.t3);
                    Vector3 a = v1.cross(intersectionPoint.subtract(testTri.t3));
                    Vector3 b = v1.cross(testTri.t1.subtract(testTri.t3));
                    double c1 = a.dot(b);

                    Vector3 v2 = testTri.t1.subtract(testTri.t3);
                    a = v2.cross(intersectionPoint.subtract(testTri.t3));
                    b = v2.cross(testTri.t2.subtract(testTri.t3));
                    double c2 = a.dot(b);

                    Vector3 v3 = testTri.t1.subtract(testTri.t2);
                    a = v3.cross(intersectionPoint.subtract(testTri.t2));
                    b = v3.cross(testTri.t3.subtract(testTri.t2));
                    double c3 = a.dot(b);

                    //System.out.printf("c1 %f, c2 %f, c3 %f\n", c1, c2, c3);

                    if (c1 > 0 && c2 > 0 && c3 > 0) {
                        c1 *= 8; c2 *= 8; c3 *= 8;
                        image.setRGB(i, j, Color.WHITE.getRGB());
                        //image.setRGB(i, j, new Color(255-(int)Utils.clamp(0, 255, camera.position.vectorTo(intersectionPoint).length() * 16), 0, 0).getRGB());
                    }
                }
            }
        }

        //image.setRGB(SCREEN_SIZE[0]-11, SCREEN_SIZE[1]-11, Color.WHITE.getRGB());

        //System.out.println(new Color(image.getRGB(SCREEN_SIZE[0]-1, SCREEN_SIZE[1]-1)));

        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    public void processKeys() {
        /*if (inputManager.isHeld.get(KeyEvent.VK_A)) {
            camera.position.x -= .1;
        } else if (inputManager.isHeld.get(KeyEvent.VK_D)) {
            camera.position.x += .1;
        } else if (inputManager.isHeld.get(KeyEvent.VK_W)) {
            camera.position.z += .1;
        } else if (inputManager.isHeld.get(KeyEvent.VK_S)) {
            camera.position.z -= .1;
        }*/
        if (inputManager.isHeld.get(KeyEvent.VK_A)) {
            camera.position = camera.position.add(Vector3.LEFT.rotY(camera.yaw).scalarMultiply(.1));
        } else if (inputManager.isHeld.get(KeyEvent.VK_D)) {
            camera.position = camera.position.add(Vector3.RIGHT.rotY(camera.yaw).scalarMultiply(.1));
        } else if (inputManager.isHeld.get(KeyEvent.VK_W)) {
            camera.position = camera.position.add(Vector3.FORWARD.rotY(camera.yaw).scalarMultiply(.1));
        } else if (inputManager.isHeld.get(KeyEvent.VK_S)) {
            camera.position = camera.position.add(Vector3.BACKWARD.rotY(camera.yaw).scalarMultiply(.1));
        } else if (inputManager.isHeld.get(KeyEvent.VK_SPACE)) {
            camera.position.y += .1;
        } else if (inputManager.isHeld.get(KeyEvent.VK_SHIFT)) {
            camera.position.y -= .1;
        }

        if (inputManager.isHeld.get(KeyEvent.VK_RIGHT)) {
            camera.yaw += .05;
        } else if (inputManager.isHeld.get(KeyEvent.VK_LEFT)) {
            camera.yaw -= .05;
        } else if (inputManager.isHeld.get(KeyEvent.VK_UP)) {
            camera.pitch += .025;
        } else if (inputManager.isHeld.get(KeyEvent.VK_DOWN)) {
            camera.pitch -= .025;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SCREEN_SIZE[0], SCREEN_SIZE[1]);
    }
}
