package mapmanager.maps;

import spritemanager.ResourceManager;
import tiles.Tile;

public abstract class Map {

    protected final Tile[][] map;
    protected transient ResourceManager resourceManager;

    protected Difficulty difficulty;

    protected String mapName;

    public Map(String mapName, int width, int height, Difficulty difficulty, ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.difficulty = difficulty;
        this.mapName = mapName;

        map = new Tile[width][height];

        loadMap();
    }

    public Tile[][] getMap() {
        return map;
    }

    public abstract void loadMap();

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public String toString() {
        return mapName + " [" + difficulty + "]";
    }
}
