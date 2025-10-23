package entity;

import java.awt.*;
import java.io.Serializable;

public abstract class Entity implements Serializable {

    protected int x;
    protected int y;
    protected final Rectangle rectangle;

    protected String name;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        rectangle = new Rectangle(32, 32);

        rectangle.x = x;
        rectangle.y = y;
    }

    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;

        rectangle = new Rectangle(width, height);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, rectangle.width, rectangle.height);
    }

    public abstract void tick();

    public boolean intersect(Rectangle rect) {
        return rectangle.intersects(rect);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        rectangle.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        rectangle.y = y;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public String toString() {
        return "Entity{" + "x=" + x + ", y=" + y + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
