package dhbw.rogue.mapmanager.maps;

import dhbw.rogue.spritemanager.ResourceManager;
import dhbw.rogue.tiles.CaveGround;
import dhbw.rogue.tiles.TestLightTile;
import dhbw.rogue.utility.Settings;

public class SimpleTestMap extends Map {

    public SimpleTestMap(int width, int height, ResourceManager resourceManager) {
        super("Test Map :)", width, height, Difficulty.EASY, resourceManager);
    }

    @Override
    public void loadMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i % 8 == 2 && j % 8 == 2) {
                    map[i][j] = new TestLightTile(i* Settings.SCALED_TILE_SIZE, j*Settings.SCALED_TILE_SIZE);
                    continue;
                }
                map[i][j] = new CaveGround(i*Settings.SCALED_TILE_SIZE, j*Settings.SCALED_TILE_SIZE, resourceManager);
            }
        }
        map[0][0] = new TestLightTile(0, 0);
    }
}
