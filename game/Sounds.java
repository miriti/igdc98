package game;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Sounds implements Runnable {

    private static Sounds instance;
    private boolean loaded = false;
    public Audio sndMusic;

    @Override
    public void run() {
        if (!loaded) {
            try {
                sndMusic = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("data/sound/music.ogg"));
            } catch (IOException ex) {
                Logger.getLogger(Sounds.class.getName()).log(Level.SEVERE, null, ex);
            }
            loaded = true;
        }
    }

    public boolean isLoaded() {
        return loaded;
    }
    
    public static Sounds getInstance() {
        if (instance == null) {
            instance = new Sounds();
        }
        return instance;
    }
}
