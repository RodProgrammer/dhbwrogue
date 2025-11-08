package tiles;

import utility.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    private final Rectangle rect;

    private transient BufferedImage image;

    public Tile(int x, int y) {
        rect = new Rectangle(x,y, Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE);
        rect.x = x;
        rect.y = y;
        rect.width = Settings.SCALED_TILE_SIZE;
        rect.height = Settings.SCALED_TILE_SIZE;
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
