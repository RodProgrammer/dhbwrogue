package dhbw.rogue.sound;

import dhbw.rogue.utility.Utility;import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    private Clip clip;
    private Clip loopClip;

    private FloatControl volumeControl;

    private int currentPercentage;

    public Sound(String sound) {
        try {
            AudioInputStream startStream = AudioSystem.getAudioInputStream(new File(sound));
            Clip startClip = AudioSystem.getClip();
            startClip.open(startStream);
            clip = startClip;

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            }

        } catch (Exception e) {
            System.err.println("[ERROR] SOUND: " + e.getMessage());
        }

        currentPercentage = 100;
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

                        if (loopClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                            volumeControl = (FloatControl) loopClip.getControl(FloatControl.Type.MASTER_GAIN);
                            changeVolume(currentPercentage);
                        }

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

    public void changeVolume(int percentage) {
        if (volumeControl != null) {
            currentPercentage = percentage;
            float linearVolume = percentage / 100f;

            float dB;
            if (linearVolume == 0f)
                dB = volumeControl.getMinimum();
            else dB = (float) (Math.log10(linearVolume) * 20.0);


            if (dB < volumeControl.getMinimum()) dB = volumeControl.getMinimum();
            if (dB > volumeControl.getMaximum()) dB = volumeControl.getMaximum();

            volumeControl.setValue(dB);
        }

    }

}
