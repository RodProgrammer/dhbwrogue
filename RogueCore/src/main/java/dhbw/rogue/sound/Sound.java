package dhbw.rogue.sound;

import dhbw.rogue.utility.Utility;import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    private Clip clip;
    private Clip loopClip;

    public Sound(String sound) {
        try {
            AudioInputStream startStream = AudioSystem.getAudioInputStream(new File(sound));
            Clip startClip = AudioSystem.getClip();
            startClip.open(startStream);
            clip = startClip;
        } catch (Exception e) {
            System.err.println("[ERROR] SOUND: " + e.getMessage());
        }
    }

    public Sound(String start, String loop) {
        try {
            Clip startClip = createClip(start);

            //I know, not the best... but it does kinda make sense? :D
            loopClip = createClip(loop);
            try {
                startClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP
                            && startClip.getFramePosition() >= startClip.getFrameLength()) {
                        startClip.close();
                        Utility.sleep(50);
                        loopClip.setFramePosition(0);
                        loopClip.loop(Clip.LOOP_CONTINUOUSLY);
                    }
                });
            } catch (Exception e) {
                System.err.println("[ERROR] SOUND: " + e.getMessage());
            }
            clip = startClip;
        } catch (Exception e) {
            System.err.println("[ERROR] SOUND: " + e.getMessage());
        }
    }

    public void playMusic() {
        if (clip != null) {
            clip.start();
        }
    }

    private Clip createClip(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream startStream = AudioSystem.getAudioInputStream(new File(path));
        Clip clip = AudioSystem.getClip();
        clip.open(startStream);
        return clip;
    }

}
