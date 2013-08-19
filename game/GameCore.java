package game;

import engine.Engine;
import static engine.core.Logger.*;
import engine.core.types.Color;
import engine.devices.input.GamePadInput;
import engine.devices.input.PCInput;
import engine.display.DisplayObject;
import game.states.Loading;

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
        
        Engine.getInstance().getDevice().setClearColor(Color.WHITE);

        if (GamePadInput.available()) {
            trace("Gamepad found!");
            Engine.getInstance().getDevice().setInput(new GamePadInput());
        } else {
            ((PCInput) Engine.getInstance().getDevice().getInput()).setCursorVisible(false);
        }
        setState(new Loading());
    }

    public final void setState(DisplayObject state) {
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
