package tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    private final Rectangle rect;

    private BufferedImage image;

    public Tile(int x, int y) {
        rect = new Rectangle(x,y,32,32);
        rect.x = x;
        rect.y = y;
        rect.width = 32;
        rect.height = 32;
    }

    public void render(Graphics2D g) {
        g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
    }

    public void tick() {

    }

    public Rectangle getRect() {
        return rect;
    }

}
