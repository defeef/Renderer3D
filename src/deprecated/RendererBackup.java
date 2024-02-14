package deprecated;

import graphics.Camera;
import graphics.Triangle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RendererBackup extends JFrame implements Runnable {
    //private final Thread thread;
    private boolean running;
    private BufferedImage image;
    //public int[] pixels;

    private final int[] SCREEN_SIZE;
    //private static final int[] size = new int[]{640, 480};

    // distance of view plane from screen
    private final double VIEW_PLANE_DIST;// = 1;
    private final double[] VIEW_PLANE_SIZE;// = new double[]{0.5, 0.5};
    private final double PIXEL_SIZE;
    private Camera camera;

    private Triangle testTri;

    public RendererBackup(Camera camera, Triangle test, int[] screen_size, double view_plane_dist, double view_plane_width) {
        SCREEN_SIZE = screen_size;
        VIEW_PLANE_DIST = view_plane_dist;
        VIEW_PLANE_SIZE = new double[2];
        PIXEL_SIZE = screen_size[0] / view_plane_width;
        VIEW_PLANE_SIZE[0] = view_plane_width;
        VIEW_PLANE_SIZE[1] = PIXEL_SIZE * screen_size[1];
        this.camera = camera;
        this.testTri = test;



        image = new BufferedImage(SCREEN_SIZE[0], SCREEN_SIZE[1], BufferedImage.TYPE_INT_RGB);

        setSize(SCREEN_SIZE[0], SCREEN_SIZE[1]);
        setResizable(false);
        setTitle("3D Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);

        setUndecorated(true);
        //TODO: Make panel and add to frame (from website tutorial)

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void render() {
        for (int i = 0; i < SCREEN_SIZE[0]; i++) {
            for (int j = 0; j < SCREEN_SIZE[1]; j++) {
                image.setRGB(i, j, new Color((int)((double)i/SCREEN_SIZE[0]*255), (int)((double)j/SCREEN_SIZE[1]*255), 0).getRGB());

                //getGraphics().drawRect(30, 30, 90, 90);
            }
        }
    }

    /*public void render() {
        //int px = 2; int py = 2;
        for (int x = 0; x < SIZE[0]; x++) {
            for (int y = 0; y < SIZE[1]; y++) {
                double px = x / 100;
                double py = y / 100;

                Vector3 m = new Vector3(PIXEL_SIZE * (px - SIZE[0] / 2.0), PIXEL_SIZE * (py - SIZE[1] / 2.0), VIEW_PLANE_DIST);
                m.roty(camera.yaw);
                m.rotx(camera.pitch);

                Plane trianglePlane = testTri.getPlane();
                Vector3 abcVector = new Vector3(trianglePlane.a, trianglePlane.b, trianglePlane.c);
                double lambda = (abcVector.dot(camera.position) + trianglePlane.k) / abcVector.dot(m);
                //System.out.println(lambda);

                if (lambda < 0.0) {
                    continue;
                }
                // intersection point
                Vector3 i = m.scalarMultiply(lambda).add(camera.position);
                //System.out.println(i);

                if (i.x > Math.min(testTri.v1.x, Math.min(testTri.v2.x, testTri.v3.x)) &&
                        i.x < Math.max(testTri.v1.x, Math.max(testTri.v2.x, testTri.v3.x)) &&
                        i.y > Math.min(testTri.v1.y, Math.min(testTri.v2.y, testTri.v3.y)) &&
                        i.y < Math.max(testTri.v1.y, Math.max(testTri.v2.y, testTri.v3.y)) &&
                        i.z > Math.min(testTri.v1.z, Math.min(testTri.v2.z, testTri.v3.z)) &&
                        i.z < Math.max(testTri.v1.z, Math.max(testTri.v2.z, testTri.v3.z))) {

                    // TODO: repeat for other two sides
                    Vector3 v1 = testTri.v2.subtract(testTri.v3);
                    Vector3 a1 = v1.cross(i.subtract(testTri.v3));
                    Vector3 b1 = v1.cross(testTri.v1.subtract(testTri.v3));
                    double c1 = a1.dot(b1);

                    Vector3 v2 = testTri.v1.subtract(testTri.v3);
                    Vector3 a2 = v2.cross(i.subtract(testTri.v3));
                    Vector3 b2 = v2.cross(testTri.v2.subtract(testTri.v3));
                    double c2 = a2.dot(b2);

                    Vector3 v3 = testTri.v1.subtract(testTri.v2);
                    Vector3 a3 = v3.cross(i.subtract(testTri.v2));
                    Vector3 b3 = v3.cross(testTri.v3.subtract(testTri.v2));
                    double c3 = a3.dot(b3);

                    //System.out.println(c1 + ", " + c2 + ", " + c3);
                    if (c1 >= 0 && c2 >= 0 && c3 >= 0) {
                        double distance = camera.position.distanceTo(i);
                        image.setRGB((int) px, (int) py, new Color((int)(1 / distance * 255), 0, 0).getRGB());
                    }
                }
            }
        }

        //Graphics g = bs.getDrawGraphics();
        getGraphics().drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        //show();
    }*/

    @Override
    public void run() {
        System.out.println("test1");
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0;//60 times per second
        double delta = 0;
        requestFocus();
        while(true) {
            //System.out.println("test2");
            long now = System.nanoTime();
            delta = delta + ((now-lastTime) / ns);
            lastTime = now;
            //System.out.println(delta);
            while (delta >= 1) // At the set fps (do animation/physics math)
            {

                delta--;
            }
            // As fast as possible (render here)
            //System.out.println("test3");
            render();
            // put rendered image on screen
            getGraphics().drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        }
    }

    /*private synchronized void start() {
        running = true;
        thread.start();
    }
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    /*public Renderer(Camera camera, int screenX, int screenY, double viewPlaneDist, double pixelSize) {
        this.camera = camera;
        SIZE = new int[]{screenX, screenY};
        VIEW_PLANE_DIST = viewPlaneDist;
        VIEW_PLANE_SIZE = new double[]{SIZE[0] * pixelSize, SIZE[1] * pixelSize};

        thread = new Thread(this);
        image = new BufferedImage(SIZE[0], SIZE[1], BufferedImage.TYPE_INT_RGB);
        //pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        setSize(SIZE[0], SIZE[1]);
        setResizable(false);
        setTitle("3D Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);
        setVisible(true);

        PIXEL_SIZE = VIEW_PLANE_SIZE[0] / SIZE[0];

        Vector3 v1 = new Vector3(-1, 0, 2);
        Vector3 v2 = new Vector3(1, 0, 2);
        Vector3 v3 = new Vector3(0, -1, 3);
        testTri = new Triangle(v1, v2, v3);
        //System.out.println(testTri.getPlane());

        //start();
    }*/
}
