package dhbw.rogue;


import dhbw.rogue.entity.Entity;
import dhbw.rogue.mapmanager.maps.Map;
import dhbw.rogue.mapmanager.maps.SimpleTestMap;
import dhbw.rogue.spritemanager.ResourceManager;
import dhbw.rogue.tiles.Tile;

import java.awt.*;

public class CollisionDetector {

    public static void checkEntityCollision(Entity entity) {
        ResourceManager resourceManager = new ResourceManager();
        Map simpleTestMap = new SimpleTestMap(32, 32, resourceManager);
        Tile[][] map = simpleTestMap.getMap();

        for (Tile[] row : map) {
            for (Tile tile : row) {
                if (!tile.getHasCollision()) continue;

                Rectangle overlap = entity.getRectangle().intersection(tile.getRectangle());

                if (overlap.width <= 0 || overlap.height <= 0) continue;
                System.out.println(overlap);

                entity.addCollisionRectangle(overlap);
            }
        }
    }

}
