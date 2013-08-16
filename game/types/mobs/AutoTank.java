package game.types.mobs;

import engine.core.TextureManager;
import engine.display.Image;
import game.types.TimeObject;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class AutoTank extends ControlledMob {

    private TimeObject body;
    private TimeObject gun;

    @Override
    protected void initMob() {
        body = new TimeObject() {
            {
                Image bodyImage = new Image(TextureManager.getTexture("data/sprites/auto-tank/body.png"));
                addChildAt(bodyImage, -bodyImage.getWidth() / 2, -bodyImage.getHeight() / 2);
            }
        };
        addChild(body);

        gun = new TimeObject() {
            {
                Image gunImage = new Image(TextureManager.getTexture("data/sprites/auto-tank/head.png"));
                addChildAt(gunImage, -19f, -gunImage.getHeight() / 2);
            }
        };
        addChild(gun);

        initHealth(100);
        initHealthBar(0, -80);
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        body.rotation = bodyRotation;
        gun.rotation = gunRotation;
    }
}
