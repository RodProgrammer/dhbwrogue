package particle;

import utility.Settings;

import java.awt.*;

public class TextParticle extends Particle {

    private final String msg;
    private int time;

    private double xa, ya, za;
    private double xx, yy, zz;

    public TextParticle(String msg, int x, int y) {
        super(x, y);

        this.msg = msg;

        xx = x;
        yy = y;
        zz = 8;

        xa = rand.nextGaussian() * 0.1;
        ya = rand.nextGaussian() * 0.1;
        za = rand.nextFloat() + 1;
    }

    @Override
    public void render(Graphics2D g, int discrepancyX, int discrepancyY) {
        if(!remove) {
            g.setColor(new Color(0, 0, 0));
            g.drawString(msg, x + 1 + (Settings.SCREEN_WIDTH / 2)  - discrepancyX, y + 1 - (int) (zz) + (Settings.SCREEN_HEIGHT / 2) - discrepancyY);
            g.setColor(new Color(0, 255, 255));
            g.drawString(msg, x + (Settings.SCREEN_WIDTH / 2)  - discrepancyX, y - (int) (zz) + (Settings.SCREEN_HEIGHT / 2) - discrepancyY);
        }

        System.out.println(this.x);
        System.out.println(this.y);
    }

    @Override
    public void tick() {

        if (remove) return;

        time++;
        if (time > 60) {
            remove();
            time = 0;
        }

        xx += xa;
        yy += ya;
        zz += za;
        za -= 0.1;

        if (zz < 0) {
            zz = 0;
            za *= -0.5;
            xa *= 0.6;
            ya *= 0.6;
        }

        x = (int) xx;
        y = (int) yy;

    }

}
