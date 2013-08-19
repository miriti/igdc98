package game.states;

import engine.core.TextureManager;
import engine.display.DisplayObject;
import engine.display.Image;
import game.GameCore;
import game.Sounds;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Loading extends DisplayObject {

    public Loading() {
        Image loadingImage = new Image(TextureManager.getTexture("data/hud/loading.png"));
        addChildAt(loadingImage, (1920 - loadingImage.getWidth()) / 2, (1080 - loadingImage.getHeight()) / 2);
    }

    @Override
    public void onAdded(DisplayObject toParent) {
        super.onAdded(toParent);
        Thread loadinThread = new Thread(Sounds.getInstance());
        loadinThread.start();
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        if (Sounds.getInstance().isLoaded()) {
            GameCore.getInstance().setState(new Intro());
            Sounds.getInstance().sndMusic.playAsMusic(1, 1, true);
        }
    }
}
