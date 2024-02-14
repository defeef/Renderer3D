import graphics.Camera;
import graphics.Triangle;
import math.Vector3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class Main {
    private static final int[] SCREEN_SIZE = new int[]{640, 480};
    //private static final int[] SCREEN_SIZE = new int[]{5, 5};

    public static void main(String[] args) throws InterruptedException {

        //TODO: Set up render layers
        Camera camera = new Camera(new Vector3(0, -1, -1), -2, 0);

        Vector3 t1 = new Vector3(-1, 0, 2);
        Vector3 t2 = new Vector3(1, 0, 2);
        Vector3 t3 = new Vector3(0, 1, 3);
        Triangle triangle = new Triangle(t1, t2, t3);

        Renderer renderer = new Renderer(camera, triangle, SCREEN_SIZE, 1, .5, 120);

        JFrame frame = new JFrame("3D Renderer");
        InputManager inputManager = new InputManager(renderer);
        frame.addKeyListener(inputManager);
        renderer.inputManager = inputManager;
        //InputManager frame = new InputManager("3D Renderer", renderer);
        frameSetup(frame, renderer, SCREEN_SIZE);

        //while (true) {
            renderer.paintComponent(frame.getGraphics());
            //Thread.sleep(1000/60);
        //}

        //renderer.run();
    }

    public static void frameSetup(JFrame frame, Renderer renderer, int[] screen_size) {
        frame.setSize(screen_size[0], screen_size[1]);
        frame.setResizable(false);
        //frame.setTitle("3D Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.add(renderer);



        //frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}