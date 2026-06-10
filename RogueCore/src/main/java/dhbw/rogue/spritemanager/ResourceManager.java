package dhbw.rogue.spritemanager;

import dhbw.rogue.sound.Sound;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ResourceManager {

    private final HashMap<String, SpriteSheet> spritesheet;
    private final HashMap<String, Sound> sounds;

    public ResourceManager()
    {
        spritesheet = new HashMap<>();
        sounds = new HashMap<>();

        loadSpriteSheets();

        loadMusic();
    }

    public BufferedImage[][] getSpritesheet(String sheet)
    {
        return spritesheet.get(sheet).getTileset();
    }

    public Sound getSound(String name)
    {
        return sounds.get(name);
    }

    private void loadSpriteSheets() {
        SpriteSheet dwarf = new SpriteSheet("resource/entities/dwarf/mhap_male_dwarf_03.png");
        spritesheet.put("dwarf", dwarf);

        SpriteSheet mascot = new SpriteSheet("resource/entities/chomb/chomb.png");
        spritesheet.put("mascot", mascot);

        SpriteSheet cave = new SpriteSheet("resource/maps/cave/RA_Caverns.png");
        spritesheet.put("cave", cave);

        SpriteSheet basicCircler = new SpriteSheet(
                "resource/effects/circler/GandalfHardcore Circler Projectiles1.png"
                , 100
                , 100
        );

        spritesheet.put("basicCircler", basicCircler);

        SpriteSheet iconSheet = new SpriteSheet("resource/icons/Icons Sheet.png");
        spritesheet.put("iconSheet", iconSheet);

        //SpriteSheet elf = new SpriteSheet("resource/entities/elf/elf.png");
    }

    private void loadMusic() {
        Sound titleMusic = new Sound("resource/audio/music/TITLE_SCREEN_LOOPSTART.wav", "resource/audio/music/TITLE_SCREEN_LOOP.wav");
        sounds.put("title_music", titleMusic);
    }
}
