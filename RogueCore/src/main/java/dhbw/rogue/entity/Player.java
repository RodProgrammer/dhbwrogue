package dhbw.rogue.entity;

import dhbw.rogue.effects.Effect;
import dhbw.rogue.spritemanager.ResourceManager;
import dhbw.rogue.utility.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Player extends Entity implements Serializable {

    private final Set<Direction> directions;
    protected transient BufferedImage[][] images;

    private transient int animationTick;
    protected int currentImage;
    protected int currentDirectionImage;

    public Player(int x, int y, ResourceManager resourceManager) {
        super(x, y, 100, 100, resourceManager);
        directions = ConcurrentHashMap.newKeySet(); //it will crash without this
        name = String.valueOf(hashCode());

        animationTick = 0;
        currentImage = 0;
        currentDirectionImage = 0;

        loadImages();
    }

    public void drawPlayer(Graphics2D graphics, int discrepancyX, int discrepancyY) {
        int textWidth = graphics.getFontMetrics().stringWidth(name);

        graphics.drawImage(
                images[currentImage][currentDirectionImage]
                , x - discrepancyX + (Settings.SCREEN_WIDTH / 2)
                , y - discrepancyY + (Settings.SCREEN_HEIGHT / 2)
                , null
        );
        graphics.setColor(Color.RED);
        graphics.drawString(
                name
                , x - discrepancyX + (Settings.SCREEN_WIDTH / 2) + (Settings.SCALED_TILE_SIZE / 2) - (textWidth / 2)
                , y - discrepancyY + (Settings.SCREEN_HEIGHT / 2) - 24
        );

        for (Effect effect : effects) {
            effect.render(
                    graphics
                    , x - discrepancyX + (Settings.SCREEN_WIDTH / 2)
                    , y - discrepancyY + (Settings.SCREEN_HEIGHT / 2)
            );
        }

        graphics.setColor(Color.GREEN);
        graphics.fillRect(
                x - discrepancyX + (Settings.SCREEN_WIDTH / 2)
                , y - discrepancyY + (Settings.SCREEN_HEIGHT / 2) - 16
                , Settings.SCALED_TILE_SIZE
                , 8
        );
        graphics.setColor(Color.BLUE);
        graphics.fillRect(
                x - discrepancyX + (Settings.SCREEN_WIDTH / 2)
                , y - discrepancyY + (Settings.SCREEN_HEIGHT / 2) - 8
                , Settings.SCALED_TILE_SIZE
                , 8
        );

    }

    @Override
    public void draw(Graphics2D graphics) {
        int textWidth = graphics.getFontMetrics().stringWidth(name);

        graphics.drawImage(
                images[currentImage][currentDirectionImage]
                , Settings.SCREEN_WIDTH / 2
                , Settings.SCREEN_HEIGHT / 2
                , null
        );

        for(Effect effect : effects) {
            effect.render(graphics, Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
        }

        graphics.setColor(Color.RED);
        graphics.drawString(
                name
                , (Settings.SCREEN_WIDTH / 2) + (Settings.SCALED_TILE_SIZE / 2) - (textWidth / 2)
                , (Settings.SCREEN_HEIGHT / 2) - 8
        );

    }

    @Override
    public void tick() {

        for (Effect effect : effects) {
            effect.tick();
        }

        if (directions.contains(Direction.UP)) {
            this.y -= speed;
            currentDirectionImage = Direction.UP.value;
        }
        if (directions.contains(Direction.DOWN)) {
            this.y += speed;
            currentDirectionImage = Direction.DOWN.value;
        }
        if (directions.contains(Direction.LEFT)) {
            this.x -= speed;
            currentDirectionImage = Direction.LEFT.value;
        }
        if (directions.contains(Direction.RIGHT)) {
            this.x += speed;
            currentDirectionImage = Direction.RIGHT.value;
        }
        if (
                directions.contains(Direction.UP) && directions.contains(Direction.DOWN)
                || directions.contains(Direction.LEFT) && directions.contains(Direction.RIGHT)
        ) {
            currentImage = 0;
            return;
        }

        if (directions.isEmpty()) {
            currentImage = 0;
            return;
        }

        animationTick++;
        if (animationTick >= 15) {
            animationTick = 0;
            currentImage++;

            if (currentImage >= images.length) {
                currentImage = 0;
            }
        }
    }

    public void updatePlayer(Player player) {
        this.x = player.x;
        this.y = player.y;
        this.currentImage = player.currentImage;
        this.currentDirectionImage = player.currentDirectionImage;
        this.speed = player.speed;
        this.effects = player.effects;
        for(Effect effect : effects) {
            effect.setResourceManager(resourceManager);
            effect.loadEffect();
        }
    }

    public void addDirection(Direction direction) {
        directions.add(direction);
    }

    public void removeDirection(Direction direction) {
        directions.remove(direction);
    }

    @Override
    public String toString() {
        return "Player(name: " + name +  ", x: " + x + ", y:" + y + ")";
    }

    public void loadImages() {
        return;
    }

    public int getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(int currentImage) {
        this.currentImage = currentImage;
    }

    public int getCurrentDirectionImage() {
        return currentDirectionImage;
    }

    public void setCurrentDirectionImage(int currentDirectionImage) {
        this.currentDirectionImage = currentDirectionImage;
    }
}
