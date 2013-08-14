package game.states;

import engine.Engine;
import engine.core.TextureManager;
import engine.devices.input.GamePadInput;
import engine.devices.input.Input;
import engine.display.Button;
import engine.display.DisplayObject;
import engine.display.Image;
import game.GameCore;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class MainMenu extends DisplayObject {
    
    private final Button playButton;
    
    public MainMenu() {
        addChild(new Image(TextureManager.getTexture("data/menu_back.jpg"), 1920, 1080));
        playButton = new Button(new Image(TextureManager.getTexture("data/play_button.png"), 550, 250)) {
            @Override
            public void onExecute() {
                Engine.getInstance().setCurrentState(new GameRound());
            }
        };
        addChildAt(playButton, (1920f - 550f) / 2f, (1080f - 250f) / 2);
    }
    
    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        GamePadInput inp = (GamePadInput) getInput();
        if (inp.getData("0") == 1.0f) {
            GameCore.getInstance().setState(new GameRound());
        }
    }
}
