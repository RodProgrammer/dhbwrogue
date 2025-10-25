package entity;

import java.awt.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Player extends Entity implements Serializable {

    private final Set<Direction> dirs;

    public Player(int x, int y) {
        super(x, y);
        dirs = ConcurrentHashMap.newKeySet(); //haha it will crash without it... I wanna commit not alive anymore
        name = String.valueOf(hashCode());
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.setColor(Color.RED);
        g.drawString(name, x-16, y-8);
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
