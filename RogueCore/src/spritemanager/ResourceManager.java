package spritemanager;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ResourceManager {

    private final HashMap<String, SpriteSheet> spritesheet;

    public ResourceManager()
    {
        spritesheet = new HashMap<>();

        loadSpriteSheets();
    }

    public BufferedImage[][] getSpritesheet(String sheet)
    {
        return spritesheet.get(sheet).getTileset();
    }

    private void loadSpriteSheets() {
        SpriteSheet dwarf = new SpriteSheet("resource/entities/dwarf/mhap_male_dwarf_03.png");
        spritesheet.put("dwarf", dwarf);

        SpriteSheet mascot = new SpriteSheet("resource/entities/chomb/chomb.png");
        spritesheet.put("mascot", mascot);

        SpriteSheet cave = new SpriteSheet("resource/maps/cave/RA_Caverns.png");
        spritesheet.put("cave", cave);

        //SpriteSheet elf = new SpriteSheet("resource/entities/elf/elf.png");
    }
}
