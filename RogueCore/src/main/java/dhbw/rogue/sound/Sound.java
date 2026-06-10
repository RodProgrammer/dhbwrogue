package dhbw.rogue.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    private Clip clip;

    public Sound(String sound) {
        try {
            AudioInputStream startStream = AudioSystem.getAudioInputStream(new File(sound));
            Clip startClip = AudioSystem.getClip();
            startClip.open(startStream);
            clip = startClip;
        } catch (Exception e) {}
    }

    public Sound(String start, String loop) {
        try {
            Clip startClip = createClip(start);

            //I know, not the best... but it does kinda make sense? :D
            try (Clip loopClip = createClip(loop)) {
                startClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        startClip.close();
                        loopClip.setFramePosition(0);
                        loopClip.loop(Clip.LOOP_CONTINUOUSLY);
                    }
                });
            }
            clip = startClip;
        } catch (Exception e) {}


    }

    public void playMusic() {
        clip.start();
    }

    private Clip createClip(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream startStream = AudioSystem.getAudioInputStream(new File(path));
        Clip clip = AudioSystem.getClip();
        clip.open(startStream);
        return clip;
    }

}
