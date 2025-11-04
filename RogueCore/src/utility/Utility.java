package utility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utility {

    private Utility(){}

    /**
     * This exists so the renderer doesn't have to rescale each time it draws the picture,
     * but we will resize the Picture beforehand and then we gucci
     */
    public static BufferedImage scaleImage(BufferedImage originalImage) {

        BufferedImage scaledImage = new BufferedImage(Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE, 2);
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE, null);
        graphics2D.dispose();

        return scaledImage;
    }


}
