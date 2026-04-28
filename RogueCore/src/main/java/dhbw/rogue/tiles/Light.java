package dhbw.rogue.tiles;

import dhbw.rogue.utility.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Light {

    private final int x;
    private final int y;
    private int radius;
    private int luminosity;

    private BufferedImage light;

    public Light(int x, int y, int radius, int luminosity) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.luminosity = luminosity;

        light = new BufferedImage(radius * 2, radius * 2, BufferedImage.TYPE_INT_ARGB);
        drawLight();
    }

    public void render(Graphics2D graphics) {
        graphics.drawImage(light
                , x - radius + (Settings.SCALED_TILE_SIZE / 2)
                , y - radius + (Settings.SCALED_TILE_SIZE / 2)
                , null
        );
    }

    public void drawLight() {
        Graphics2D graphics = (Graphics2D) light.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(new Color(0, 0, 0, luminosity));
        int iterations = radius / Settings.LIGHT_STEP;
        for (int i = 0; i < iterations; i++) {
            graphics.fillOval(
                    radius - (i * Settings.LIGHT_STEP)
                    , radius - (i * Settings.LIGHT_STEP)
                    , i * Settings.LIGHT_STEP * 2
                    , i * Settings.LIGHT_STEP * 2
            );
        }
        graphics.dispose();
    }
}
