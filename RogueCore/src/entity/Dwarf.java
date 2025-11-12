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

public class Dwarf extends Player implements Serializable {

    public Dwarf(int x, int y) {
        super(x, y);
    }

    @Override
    public void drawPlayer(Graphics2D g, int discrepancyX, int discrepancyY) {
        g.drawImage(images[super.currImage][super.currDirectionImage], x - discrepancyX + (Settings.SCREEN_WIDTH / 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2), null);
        g.setColor(Color.RED);
        g.drawString(name, x - discrepancyX + (Settings.SCREEN_WIDTH / 2) - (name.length() * 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2) - 8);
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(images[currImage][currDirectionImage], Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2, null);

        g.setColor(Color.RED);
        g.drawString(name, (Settings.SCREEN_WIDTH / 2) - (name.length() * 2), (Settings.SCREEN_HEIGHT / 2) - 8);
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
