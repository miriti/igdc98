package game.states;

import engine.core.TextureManager;
import engine.display.DisplayObject;
import engine.display.Image;
import game.GameCore;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class GameOver extends DisplayObject {

    public GameOver() {
        Image gameover = new Image(TextureManager.getTexture("data/hud/gameover.png"), 1304, 253);
        addChildAt(gameover, (1920 - gameover.getWidth()) / 2, (1080 - gameover.getHeight()) / 2);
    }

    @Override
    public void update(long deltaTime) {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
            GameRound.resetAll();
            GameCore.getInstance().setState(new MainMenu());
        }
    }
}
