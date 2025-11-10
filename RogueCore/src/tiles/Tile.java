package tiles;

import utility.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    private final Rectangle rect;

    private transient BufferedImage image;

    public Tile(int x, int y) {
        rect = new Rectangle(x,y, Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE);
        rect.x = x;
        rect.y = y;
        rect.width = Settings.SCALED_TILE_SIZE;
        rect.height = Settings.SCALED_TILE_SIZE;

        image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.MAGENTA);
        g2d.drawRect(x, y, rect.width, rect.height);
    }

    public void render(Graphics2D g) {
        g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
    }

    public void tick() {
        //TODO: possible animation?
    }

    public Rectangle getRectangle() {
        return rect;
    }

}
