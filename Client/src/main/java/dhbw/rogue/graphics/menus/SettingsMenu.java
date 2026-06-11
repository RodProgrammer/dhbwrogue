package dhbw.rogue.graphics.menus;

import dhbw.rogue.spritemanager.ResourceManager;
import dhbw.rogue.utility.Settings;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SettingsMenu {

    private int music;
    private int soundeffects;
    private int pointerX;
    private int pointerY;
    private final String menuText;
    private final ResourceManager resourceManager;

    public SettingsMenu(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;

        music = 100;
        soundeffects = 100;

        pointerY = 0;
        pointerX = 0;
        menuText = "Settings";
    }


    public void render(Graphics2D g) {
        int offset = 10;
        int x = (Settings.SCREEN_WIDTH / 2) - (Settings.SCALED_TILE_SIZE * 6);
        createMusicSetting(g, x, offset, 1, "Music", music);
        createMusicSetting(g, x, offset, 2, "Sound Effects", soundeffects);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString(menuText, (Settings.SCREEN_WIDTH / 2) - menuText.length(), (Settings.SCREEN_HEIGHT / 12) - 24);
        g.setColor(Color.BLUE);
        g.drawString(String.valueOf(music), x + (Settings.SCALED_TILE_SIZE * 13), (Settings.SCREEN_HEIGHT / 12) + (Settings.SCALED_TILE_SIZE / 2));
        g.drawString(String.valueOf(soundeffects), x + (Settings.SCALED_TILE_SIZE * 13), ((Settings.SCREEN_HEIGHT / 6)) + (Settings.SCALED_TILE_SIZE / 2));

        //now rendering the Pointer
        g.setColor(Color.RED);
        g.drawRect(x + Settings.SCALED_TILE_SIZE * pointerX + (offset * pointerX), (Settings.SCREEN_HEIGHT) * (pointerY + 1) / 12, Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE);
    }

    private void createMusicSetting(Graphics2D g, int x, int offset, int index, String name, int amount) {
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(name, x - (Settings.SCALED_TILE_SIZE * 2), ((Settings.SCREEN_HEIGHT * index) / 12) + (Settings.SCALED_TILE_SIZE / 2));
        g.fillRect(x, (Settings.SCREEN_HEIGHT * index) / 12, Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE);
        createText("-", g, x, (Settings.SCREEN_HEIGHT * index) / 12, 24);
        g.fillRect(x + Settings.SCALED_TILE_SIZE + offset, (Settings.SCREEN_HEIGHT * index) / 12, Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE);
        createText("+", g, x + Settings.SCALED_TILE_SIZE, (Settings.SCREEN_HEIGHT * index) / 12, 24);
        for(int i = 0; i < (amount / 10); i++) {
            g.fillRect(x + (Settings.SCALED_TILE_SIZE * 2) + (offset * 2) + (Settings.SCALED_TILE_SIZE * i), (Settings.SCREEN_HEIGHT * index) / 12, Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE);
        }
        g.drawRect(x + (Settings.SCALED_TILE_SIZE * 2) + (offset * 2), (Settings.SCREEN_HEIGHT * index) / 12, Settings.SCALED_TILE_SIZE * 10, Settings.SCALED_TILE_SIZE);
    }

    public void changePointer(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> pointerY--;
            case KeyEvent.VK_S -> pointerY++;
            case KeyEvent.VK_A -> pointerX--;
            case KeyEvent.VK_D -> pointerX++;
            case KeyEvent.VK_ENTER -> updateValue();
        }

        if (pointerY < 0){
            pointerY = 0;
        } else if (pointerY >= 2) {
            pointerY = 1;
        }

        if (pointerX < 0) {
            pointerX = 0;
        } else if (pointerX >= 2){
            pointerX = 1;
        }
    }

    private void updateValue() {
        int updatedPointerX = pointerX;
        if(updatedPointerX == 0) {
            updatedPointerX = -1;
        }

        switch (pointerY) {
            case 0 -> {
                music = music + (10 * updatedPointerX);

                if (music < 0) {
                    music = 0;
                } else if (music >= 100) {
                    music = 100;
                }

                resourceManager.getSound("title_music").changeVolume(music);
            }
            case 1 -> {
                soundeffects = soundeffects + (10 * updatedPointerX);

                if (soundeffects < 0) {
                    soundeffects = 0;
                } else if (soundeffects >= 100) {
                    soundeffects = 100;
                }
            }
        }
    }

    private void createText(String text, Graphics2D g, int x, int y, int size) {
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.PLAIN, size));
        g.drawString(text, x + (Settings.SCALED_TILE_SIZE / 2), y + (Settings.SCALED_TILE_SIZE / 2));
        g.setFont(null);
        g.setColor(Color.WHITE);
    }

}
