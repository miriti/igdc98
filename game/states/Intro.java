package game.states;

import engine.core.TextureManager;
import engine.core.types.Color;
import engine.display.DisplayObject;
import engine.display.Image;
import engine.display.Quad;
import engine.easing.Tween;
import game.GameCore;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Intro extends DisplayObject {

    public Intro() {
        addChild(new Quad(1920, 1080, Color.BLACK));
        final Image logoImage = new Image(TextureManager.getTexture("data/hud/intro/igdc.jpg"));
        logoImage.getColor().a = 0;
        addChildAt(logoImage, (1920 - logoImage.getWidth()) / 2, (1080 - logoImage.getHeight()) / 2);

        Tween logoTween = new Tween(3000) {
            @Override
            protected void updateTarget(float phase) {
                logoImage.getColor().a = phase;
            }

            @Override
            protected void onFinish() {
                updateables.add(new Tween(3000, 3000) {
                    @Override
                    protected void updateTarget(float phase) {
                        logoImage.getColor().a = 1f - phase;
                    }

                    @Override
                    protected void onFinish() {
                        GameCore.getInstance().setState(new MainMenu());
                    }
                });
            }
        };
        updateables.add(logoTween);
    }
}
