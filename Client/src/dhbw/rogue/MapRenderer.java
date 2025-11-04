package dhbw.rogue;

import utility.Settings;

import java.awt.*;
import java.util.Random;

public class MapRenderer {

    private final int[][] map;

    public MapRenderer() {
        map = new int[32][32];

        Random rand = new Random();

        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                map[i][j] = rand.nextInt(5);
            }
        }
    }

    public void render(Graphics2D g, int discrepancyX, int discrepancyY) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int x = (i* Settings.SCALED_TILE_SIZE) + (Settings.SCREEN_WIDTH/2) - discrepancyX;
                int y = (j*Settings.SCALED_TILE_SIZE) + (Settings.SCREEN_HEIGHT/2) - discrepancyY;
                if(!(x >= Settings.SCREEN_WIDTH || x <= -1 * Settings.SCALED_TILE_SIZE) && !(y >= Settings.SCREEN_HEIGHT || y <= -1 * Settings.SCALED_TILE_SIZE)) {
                    g.setColor(new Color(10 * map[i][j] + 20, 20 * map[i][j] + 20,  30 * map[i][j] + 10));
                    g.fillRect(x, y, Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE);
                }
            }
        }
        g.setColor(Color.WHITE);
    }

    public void tick() {

    }

}
