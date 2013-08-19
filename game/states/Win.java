package game.states;

import engine.core.TextureManager;
import engine.display.DisplayObject;
import engine.display.Image;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Win extends DisplayObject {

    public Win() {
        Image image = new Image(TextureManager.getTexture("data/hud/win.png"), 1059, 640);
        addChildAt(image, (1920 - image.getWidth()) / 2, (1080 - image.getHeight()) / 2);
    }
}
