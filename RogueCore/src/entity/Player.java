package entity;

import utility.Settings;
import utility.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Player extends Entity implements Serializable {

    private transient final Set<Direction> dirs;
    private transient BufferedImage[][] images;

    private transient int animTick;
    private transient int currImage;
    private transient int currDirectionImage;

    public Player(int x, int y) {
        super(x, y, 100, 100);
        dirs = ConcurrentHashMap.newKeySet(); //haha it will crash without it... I wanna commit not alive anymore
        name = String.valueOf(hashCode());

        animTick = 0;
        currImage = 0;
        currDirectionImage = 0;

        loadImages();
    }

    public void drawPlayer(Graphics2D g, int discrepancyX, int discrepancyY) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x - discrepancyX + (Settings.SCREEN_WIDTH / 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2), rectangle.width, rectangle.height);
        //g.drawImage(images[0][0], x - discrepancyX + (Settings.SCREEN_WIDTH / 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2), rectangle.width, rectangle.height);
        g.setColor(Color.RED);
        g.drawString(name, x - discrepancyX + (Settings.SCREEN_WIDTH / 2) - (name.length() * 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2) - 8);
    }

    @Override
    public void draw(Graphics2D g) {
        //g.setColor(Color.MAGENTA);
        //g.fillRect(Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2, rectangle.width, rectangle.height);
        g.drawImage(images[currImage][currDirectionImage], Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2, null);

        g.setColor(Color.RED);
        g.drawString(name, (Settings.SCREEN_WIDTH / 2) - (name.length() * 2), (Settings.SCREEN_HEIGHT / 2) - 8);
    }

    @Override
    public void tick() {
        if (dirs.contains(Direction.UP)) {
            this.y -= 5;
            currDirectionImage = Direction.UP.value;
        }
        if (dirs.contains(Direction.DOWN)) {
            this.y += 5;
            currDirectionImage = Direction.DOWN.value;
        }
        if (dirs.contains(Direction.LEFT)) {
            this.x -= 5;
            currDirectionImage = Direction.LEFT.value;
        }
        if (dirs.contains(Direction.RIGHT)) {
            this.x += 5;
            currDirectionImage = Direction.RIGHT.value;
        }

        if(dirs.contains(Direction.UP) && dirs.contains(Direction.DOWN) || dirs.contains(Direction.LEFT) && dirs.contains(Direction.RIGHT)) {
            currImage = 0;
            return;
        }

        if (dirs.isEmpty()) {
            currImage = 0;
            return;
        }

        animTick++;
        if (animTick >= 15) {
            animTick = 0;
            currImage++;

            if (currImage >= images.length) {
                currImage = 0;
            }
        }
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

    public void loadImages() {
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File("resource/entities/dwarf/mhap_male_dwarf_03.png"));
        } catch (IOException ex) {}

        if (originalImage != null) {
            images = Utility.getImages(originalImage, 16,16);
            System.out.println("[INFO]: Loaded Dwarf Images");
            System.out.println("[INFO]: Loaded " + Arrays.deepToString(images));
            System.out.println("[INFO]: Length: " + images.length);
        }
    }
}
