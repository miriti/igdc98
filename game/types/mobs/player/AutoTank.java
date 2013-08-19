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
public class AutoTank extends ControlledMob {

    private TimeObject body;
    private TimeObject gun;

    public AutoTank() {
        automatedFire = true;
        fireDelay = 150;
    }

    @Override
    protected void initMob() {
        body = new TimeObject() {
            {
                Image bodyImage = new Image(TextureManager.getTexture("data/sprites/player/auto-tank/body.png"));
                addChildAt(bodyImage, -bodyImage.getWidth() / 2, -bodyImage.getHeight() / 2);
            }
        };
        addChild(body);

        gun = new TimeObject() {
            {
                Image gunImage = new Image(TextureManager.getTexture("data/sprites/player/auto-tank/head.png"));
                addChildAt(gunImage, -19f, -gunImage.getHeight() / 2);
            }
        };
        addChild(gun);

        initHealth(100);
        initHealthBar(0, -80);
    }

    @Override
    protected void fireBullet() {
        super.fireBullet();
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
        return 40;
    }
}
