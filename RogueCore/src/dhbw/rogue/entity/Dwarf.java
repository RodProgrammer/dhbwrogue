package dhbw.rogue.entity;

import dhbw.rogue.effects.CirclerBasic;
import dhbw.rogue.spritemanager.ResourceManager;

import java.io.Serializable;

public class Dwarf extends Player implements Serializable {

    public Dwarf(int x, int y, ResourceManager resourceManager) {
        super(x, y, resourceManager);

        effects.add(new CirclerBasic(resourceManager));

        loadImages();
    }

    public void loadImages() {
        images = resourceManager.getSpritesheet("dwarf");
    }
}
