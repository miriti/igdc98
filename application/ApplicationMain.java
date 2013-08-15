package application;

import engine.Engine;
import engine.devices.LWJGLDevice;
import game.GameCore;
import game.states.MainMenu;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class ApplicationMain {

    public static ApplicationMain Instance;
    public final Engine engineInstance;
    private int wndWidth = 1280;
    private int wndHeight = 720;
    private static String[] appArgs;

    public static void main(String[] args) {
        appArgs = args;
        Instance = new ApplicationMain();
    }

    public ApplicationMain() {
        boolean fullscreen = false;

        // Обрабатываем параметры командной строки
        for (int i = 0; i < appArgs.length; i++) {
            switch (appArgs[i]) {
                case "-480p":
                    wndWidth = 854;
                    wndHeight = 480;
                    break;
                case "-720p":
                    wndWidth = 1280;
                    wndHeight = 720;
                    break;
                case "-1080p":
                    wndWidth = 1920;
                    wndHeight = 1080;
                    break;
                case "-fullscreen":
                    fullscreen = true;
                    break;
                default:
                    System.out.println("Unknown agrgument <" + appArgs[i] + ">");
            }
        }

        engineInstance = new Engine(new LWJGLDevice(), wndWidth, wndHeight, fullscreen);
        engineInstance.setTitle("IGDC #98 by KEFIR");
        engineInstance.setSceneSize(1920, 1080);
        engineInstance.setCurrentState(GameCore.getInstance());
        engineInstance.start();
    }
}
