package dhbw.rogue.particle;

import dhbw.rogue.utility.Settings;

import java.awt.*;

public class TextParticle extends Particle {

    private final String msg;
    private int time;

    private double randomX, randomY, randomZ;
    private double xx, yy, zz;

    public TextParticle(String msg, int x, int y) {
        super(x, y);

        this.msg = msg;

        xx = x;
        yy = y;
        zz = 8;

        randomX = random.nextGaussian() * 0.1;
        randomY = random.nextGaussian() * 0.1;
        randomZ = random.nextFloat() + 1;
    }

    @Override
    public void render(Graphics2D graphics, int discrepancyX, int discrepancyY) {
        if(!remove) {
            graphics.setColor(new Color(0, 0, 0));
            graphics.drawString(
                    msg
                    , x + 1 + (Settings.SCREEN_WIDTH / 2)  - discrepancyX
                    , y + 1 - (int) (zz) + (Settings.SCREEN_HEIGHT / 2) - discrepancyY
            );
            graphics.setColor(new Color(0, 255, 255));
            graphics.drawString(
                    msg
                    , x + (Settings.SCREEN_WIDTH / 2)  - discrepancyX
                    , y - (int) (zz) + (Settings.SCREEN_HEIGHT / 2) - discrepancyY
            );
        }
    }

    @Override
    public void tick() {

        if (remove) return;

        time++;
        if (time > 60) {
            remove();
            time = 0;
        }

        xx += randomX;
        yy += randomY;
        zz += randomZ;
        randomZ -= 0.1;

        if (zz < 0) {
            zz = 0;
            randomZ *= -0.5;
            randomX *= 0.6;
            randomY *= 0.6;
        }

        x = (int) xx;
        y = (int) yy;

    }

}
