package dhbw.rogue.tiles;

import dhbw.rogue.utility.Settings;

import java.awt.*;
import java.util.Random;

public class TestLightTile extends Tile {
    public TestLightTile(int x, int y) {
        super(x, y);

        hasCollision = true;

        light = new Light(x, y, Settings.SCALED_TILE_SIZE * 2, new Random().nextInt(10, 101));
    }

    @Override
    public void draw(Graphics2D graphics, int x, int y) {
        graphics.setColor(Color.MAGENTA);
        graphics.fillRect(x, y, Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE);
    }

    @Override
    public void tick() {

    }

    public void reloadLight() {
        light = new Light(
                x
                , y
                , Settings.SCALED_TILE_SIZE * 2
                , new Random().nextInt(10, 101)
        );
    }
}
