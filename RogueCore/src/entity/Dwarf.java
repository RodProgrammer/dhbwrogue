package entity;

import spritemanager.ResourceManager;
import utility.Settings;
import java.awt.*;
import java.io.Serializable;

public class Dwarf extends Player implements Serializable {

    public Dwarf(int x, int y, ResourceManager resourceManager) {
        super(x, y, resourceManager);
        
        loadImages();
    }

    @Override
    public void drawPlayer(Graphics2D g, int discrepancyX, int discrepancyY) {
        g.drawImage(images[super.currImage][super.currDirectionImage], x - discrepancyX + (Settings.SCREEN_WIDTH / 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2), null);
        g.setColor(Color.RED);
        g.drawString(name, x - discrepancyX + (Settings.SCREEN_WIDTH / 2) - (name.length() * 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2) - 24);

        g.setColor(Color.GREEN);
        g.fillRect(x - discrepancyX + (Settings.SCREEN_WIDTH / 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2) - 16, Settings.SCALED_TILE_SIZE, 8);
        g.setColor(Color.BLUE);
        g.fillRect(x - discrepancyX + (Settings.SCREEN_WIDTH / 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2) - 8, Settings.SCALED_TILE_SIZE, 8);
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(images[currImage][currDirectionImage], Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2, null);

        g.setColor(Color.RED);
        g.drawString(name, (Settings.SCREEN_WIDTH / 2) - (name.length() * 2), (Settings.SCREEN_HEIGHT / 2) - 8);
    }


    public void loadImages() {
        images = resourceManager.getSpritesheet("dwarf");
    }
}
