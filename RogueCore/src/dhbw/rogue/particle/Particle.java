package dhbw.rogue.particle;

import java.awt.*;
import java.util.Random;

public abstract class Particle {

    protected Random rand;
    protected boolean remove;

    protected int x;
    protected int y;

    public Particle(int x, int y) {
        rand = new Random();
        this.x = x;
        this.y = y;
    }

    public abstract void render(Graphics2D g, int discrepancyX, int discrepancyY);

    public abstract void tick();

    public void remove() {
        remove = true;
    }

    public boolean toRemove() {
        return remove;
    }
}
