package game;

import engine.Engine;
import engine.devices.input.GamePadInput;
import engine.display.DisplayObject;
import game.states.MainMenu;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class GameCore extends DisplayObject {

    private static GameCore instance;
    private DisplayObject currentState;

    public GameCore() {
        if (instance != null) {
            throw new Error("Singletone");
        }
        if (GamePadInput.available()) {
            Engine.getInstance().getDevice().setInput(new GamePadInput());
        }
        setState(new MainMenu());
    }

    public void setState(DisplayObject state) {
        if (currentState != null) {
            removeChild(currentState);
        }
        currentState = state;
        addChild(currentState);
    }

    public static GameCore getInstance() {
        if (instance == null) {
            instance = new GameCore();
        }
        return instance;
    }
}
