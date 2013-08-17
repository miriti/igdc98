package game.types.mobs.player;

import engine.core.TextureManager;
import engine.display.Image;
import game.types.Collidable;
import game.types.TimeObject;
import game.types.mobs.ControlledMob;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class RocketLauncher extends ControlledMob {

    private TimeObject body;
    private TimeObject gun;

    @Override
    protected void initMob() {
        body = new TimeObject() {
            {
                Image bodyImage = new Image(TextureManager.getTexture("data/sprites/rocket-launcher/body.png"));
                addChildAt(bodyImage, -bodyImage.getWidth() / 2, -bodyImage.getHeight() / 2);
            }
        };
        addChild(body);

        gun = new TimeObject() {
            {
                Image gunImage = new Image(TextureManager.getTexture("data/sprites/rocket-launcher/head.png"));
                addChildAt(gunImage, -27f, -gunImage.getHeight() / 2);
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

    @Override
    public void collision(Collidable with) {
    }

    @Override
    public float getRadius() {
        return 45;
    }
}
