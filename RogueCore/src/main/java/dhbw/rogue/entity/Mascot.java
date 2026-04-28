package dhbw.rogue.entity;

import dhbw.rogue.spritemanager.ResourceManager;
import dhbw.rogue.utility.Settings;
import dhbw.rogue.utility.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Mascot extends Entity {

    private transient BufferedImage[][] images;

    public Mascot(int x, int y, ResourceManager resourceManager) {
        super(x, y, 1, 0, resourceManager);

        loadImages();
    }

    @Override
    public void draw(Graphics2D graphics) {
        for (int i = 0; i < images.length; i ++) {
            for (int j = 0; j < images[i].length; j++) {
                graphics.drawImage(
                        images[i][j]
                        , i*Settings.SCALED_TILE_SIZE
                        , j*Settings.SCALED_TILE_SIZE
                        , null
                );
            }
        }
    }

    @Override
    public void tick() {
        //TODO: animation :)
    }

    private void loadImages() {
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File("resource/entities/chomb/chomb.png"));
        } catch (IOException ex) {}

        if (originalImage != null) {
            images = Utility.getImages(originalImage, 32,32, Settings.SCALED_TILE_SIZE);
            System.out.println("[INFO]: Loaded " + Arrays.deepToString(images));
            System.out.println("[INFO]: Length: " + images.length);
        }

    }

    public BufferedImage[][] getImages() {
        return images;
    }
}
