package dhbw.rogue;

import java.awt.*;
import java.util.Random;

public class MapRenderer {

    private final int[][] map;

    public MapRenderer() {
        map = new int[32][32];

        Random rand = new Random();

        for(int i = 0; i < 32; i++) {
            for(int j = 0; j < 32; j++) {
                map[i][j] = rand.nextInt(5);
            }
        }
    }

    public void render(Graphics2D g, int discrepancyX, int discrepancyY) {
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                g.setColor(new Color(10 * map[i][j] + 20, 20 * map[i][j] + 20,  30 * map[i][j] + 10));
                g.fillRect((i*32) + 640 - discrepancyX, (j*32) + 360 - discrepancyY, 32, 32);
            }
        }
        g.setColor(Color.WHITE);
    }

    public void tick() {

    }

}
