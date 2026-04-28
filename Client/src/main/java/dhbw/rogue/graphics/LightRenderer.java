package dhbw.rogue.graphics;

import dhbw.rogue.tiles.Tile;
import dhbw.rogue.utility.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class represents the static Light in the Game.
 * It renders a lightMap onto the Screen in order to get a "light" Effect.
 */
public class LightRenderer {

    private BufferedImage lightMap;

    /**
     * the constructor creates a lightMap and draws onto it with the exact locations of the Tiles.
     *
     * @param tile  the tileMap
     */
    public LightRenderer(Tile[][] tile) {

        if (tile == null) return;

        lightMap = new BufferedImage(
                tile.length * Settings.SCALED_TILE_SIZE
                , tile[0].length * Settings.SCALED_TILE_SIZE
                , BufferedImage.TYPE_INT_ARGB
        );

        drawLight(tile);
    }

    /**
     * This method actually draws the Light Map onto the screen.
     *
     * @param graphics             Graphics2D
     * @param discrepancyX  it lets you draw the difference on the x-axes
     * @param discrepancyY  it lets you draw the difference on the y-axes
     */
    public void renderLight(Graphics2D graphics, int discrepancyX, int discrepancyY) {
        graphics.drawImage(
                lightMap
                , (Settings.SCREEN_WIDTH / 2) - discrepancyX
                , (Settings.SCREEN_HEIGHT / 2) - discrepancyY
                , null
        );
    }

    /**
     * This method actually draws each Light onto the screen
     *
     * @param allTiles  the Map it needs as reference
     */
    private void drawLight(Tile[][] allTiles) {
        Graphics2D graphics = (Graphics2D) lightMap.getGraphics();

        graphics.setColor(new Color(0, 0, 0, 255));
        graphics.fillRect(
                0
                , 0
                , allTiles.length * Settings.SCALED_TILE_SIZE
                , allTiles[0].length * Settings.SCALED_TILE_SIZE
        );

        graphics.setComposite(AlphaComposite.DstOut);
        for (Tile[] allTile : allTiles) {
            for (Tile tile : allTile) {
                if (tile.getLight() != null) {
                    tile.getLight().render(graphics);
                }
            }
        }
        graphics.dispose();
    }

}
