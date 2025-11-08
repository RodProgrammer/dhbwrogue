package entity;

import utility.Settings;

import java.awt.*;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Player extends Entity implements Serializable {

    private transient final Set<Direction> dirs;

    public Player(int x, int y) {
        super(x, y);
        dirs = ConcurrentHashMap.newKeySet(); //haha it will crash without it... I wanna commit not alive anymore
        name = String.valueOf(hashCode());
    }

    public void drawPlayer(Graphics2D g, int discrepancyX, int discrepancyY) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x - discrepancyX + (Settings.SCREEN_WIDTH / 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2), rectangle.width, rectangle.height);
        g.setColor(Color.RED);
        g.drawString(name, x - discrepancyX + (Settings.SCREEN_WIDTH / 2) - (name.length() * 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2) - 8);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2, rectangle.width, rectangle.height);
        g.setColor(Color.RED);
        g.drawString(name, (Settings.SCREEN_WIDTH / 2) - (name.length() * 2), (Settings.SCREEN_HEIGHT / 2) - 8);
    }

    @Override
    public void tick() {
        if (dirs.contains(Direction.UP)) this.y -= 5;
        if (dirs.contains(Direction.DOWN)) this.y += 5;
        if (dirs.contains(Direction.LEFT)) this.x -= 5;
        if (dirs.contains(Direction.RIGHT)) this.x += 5;
    }

    public void addDirection(Direction dir) {
        dirs.add(dir);
    }

    public void removeDirection(Direction dir) {
        dirs.remove(dir);
    }

    @Override
    public String toString() {
        return "Player(name: " + name +  ", x: " + x + ", y:" + y + ")";
    }
}
