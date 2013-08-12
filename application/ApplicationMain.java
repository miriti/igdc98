package application;

import engine.Engine;
import game.GameCore;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class ApplicationMain {

    public static ApplicationMain Instance;

    public final Engine engineInstance;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Instance = new ApplicationMain();
    }
    
    public ApplicationMain() {
        engineInstance = new Engine(new GameCore(), 1024, 576);
        engineInstance.setTitle("IGDC #98 by KEFIR");
        engineInstance.start();
    }
}
