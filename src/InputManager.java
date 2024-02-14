import math.Vector3;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Dictionary;
import java.util.Hashtable;

public class InputManager implements KeyListener {
    public Renderer renderer;

    public Dictionary<Integer, Boolean> isHeld;

    public InputManager(Renderer renderer) {
        this.renderer = renderer;
        isHeld = new Hashtable<>();

        isHeld.put(KeyEvent.VK_A, false);
        isHeld.put(KeyEvent.VK_D, false);
        isHeld.put(KeyEvent.VK_W, false);
        isHeld.put(KeyEvent.VK_S, false);
        isHeld.put(KeyEvent.VK_SPACE, false);
        isHeld.put(KeyEvent.VK_SHIFT, false);

        isHeld.put(KeyEvent.VK_UP, false);
        isHeld.put(KeyEvent.VK_DOWN, false);
        isHeld.put(KeyEvent.VK_LEFT, false);
        isHeld.put(KeyEvent.VK_RIGHT, false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isHeld.get(e.getKeyCode()) != null) {
            isHeld.put(e.getKeyCode(), true);
        }

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            renderer.camera.position = new Vector3(0, -1, -1);
            renderer.camera.pitch = -2;
            renderer.camera.yaw = 0;
        }

        if (e.getKeyCode() == KeyEvent.VK_C) {
            System.out.println(renderer.camera);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (isHeld.get(e.getKeyCode()) != null) {
            isHeld.put(e.getKeyCode(), false);
        }

        /*if (e.getKeyCode() == KeyEvent.VK_Q) {
            renderer.camera.position = new Vector3(0, -1, -1);
            renderer.camera.pitch = -2;
            renderer.camera.yaw = 0;
        }*/
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.println("keyTyped");
    }
}
