package dhbw.rogue.sound;

import javax.sound.sampled.Clip;
import java.util.HashMap;

public class SoundManager {

    private HashMap<String, Clip> clips;

    public SoundManager() {
        clips = new HashMap<>();
        addAllSounds();
    }

    private Clip getClip(String name) {
        return clips.get(name);
    }

    private void addAllSounds() {
        //lets first get Music playing :D

    }
}
