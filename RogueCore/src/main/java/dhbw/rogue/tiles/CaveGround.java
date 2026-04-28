package dhbw.rogue.tiles;

import dhbw.rogue.spritemanager.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CaveGround extends Tile {

    private final ResourceManager resourceManager;

    public CaveGround(int x, int y, ResourceManager resourceManager) {
        super(x, y);
        this.resourceManager = resourceManager;
        loadResources();
    }

    @Override
    public void draw(Graphics2D graphics, int x, int y) {
        graphics.drawImage(image, x, y, null);
    }

    @Override
    public void tick() {

    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    private void loadResources() {
        image =  resourceManager.getSpritesheet("cave")[6][0];
    }
}
