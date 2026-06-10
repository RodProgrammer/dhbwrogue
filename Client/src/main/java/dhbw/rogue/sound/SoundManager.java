package dhbw.rogue.sound;

import dhbw.rogue.spritemanager.ResourceManager;

public class SoundManager {

    private final ResourceManager resourceManager;

    public SoundManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public void playTitleMusic()
    {
        resourceManager.getSound("title_music");
    }

    public Sound getSound(String name) {
        return resourceManager.getSound(name);
    }
}
