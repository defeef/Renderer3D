package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderLayer {
    private BufferedImage image;

    public RenderLayer(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void renderTo(Graphics g) {

    }
}
