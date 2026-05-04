package dhbw.rogue.entity;

import dhbw.rogue.effects.Effect;
import dhbw.rogue.spritemanager.ResourceManager;
import dhbw.rogue.utility.Settings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Serializable {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected ArrayList<Rectangle> collisionRectangles;

    protected int health;
    protected int maxHealth;
    protected int mana;
    protected int maxMana;

    protected int speed;

    protected String name;

    protected transient ResourceManager resourceManager;
    protected List<Effect> effects;

    public Entity(int x, int y, int maxHealth, int maxMana, ResourceManager resourceManager) {
        this.x = x;
        this.y = y;

        this.collisionRectangles = new ArrayList<>();

        this.width = Settings.SCALED_TILE_SIZE;
        this.height = Settings.SCALED_TILE_SIZE;

        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.health = maxHealth;
        this.mana = maxMana;
        this.speed = 5;

        this.resourceManager = resourceManager;

        this.effects = new ArrayList<>();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width, height);
        g.setColor(Color.RED);
        g.drawString(name, x - (name.length() * 2), y - 8);
    }

    public abstract void tick();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
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

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void addCollisionRectangle(Rectangle rectangle) {
        for (int i = 0; i < this.collisionRectangles.size(); i++) {
            Rectangle collisionRectangle = this.collisionRectangles.get(i);
            if (collisionRectangle.x == rectangle.x && collisionRectangle.width == rectangle.width) {
                rectangle = new Rectangle(
                        rectangle.x
                        , Math.min(rectangle.y, collisionRectangle.y)
                        , rectangle.width
                        , rectangle.height + collisionRectangle.height
                );
                this.collisionRectangles.remove(i);
                i--;
            } else if (collisionRectangle.y == rectangle.y && collisionRectangle.height == rectangle.y) {
                rectangle = new Rectangle(
                        Math.min(rectangle.x, collisionRectangle.x)
                        , rectangle.y
                        , rectangle.width + collisionRectangle.width
                        , rectangle.height
                );
                this.collisionRectangles.remove(i);
                i--;
            }
        }
        this.collisionRectangles.add(rectangle);
    }

    public void resolveCollisions() {
        for (Rectangle collisionRectangle : this.collisionRectangles) {
            if (collisionRectangle.width <= collisionRectangle.height) {
                if (collisionRectangle.x <= this.x) {
                    this.x += collisionRectangle.width;
                } else {
                    this.x -= collisionRectangle.width;
                }
            } else {
                if (collisionRectangle.y <= this.y) {
                    this.y += collisionRectangle.height;
                } else {
                    this.y -= collisionRectangle.height;
                }
            }
        }
        this.collisionRectangles = new ArrayList<>();
    }
}
