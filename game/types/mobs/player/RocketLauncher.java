package game.types.mobs.player;

import engine.core.TextureManager;
import engine.display.Image;
import game.types.Collidable;
import game.types.TimeObject;
import game.types.mobs.ControlledMob;
import game.types.weapons.bullets.Bullet;
import game.types.weapons.bullets.PlayerRocket;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class RocketLauncher extends ControlledMob {

    private TimeObject body;
    private TimeObject gun;

    public RocketLauncher() {
        automatedFire = true;
        fireDelay = 500;
        initHealth(200);
        initHealthBar(0, -80);
    }

    @Override
    protected void initMob() {
        body = new TimeObject() {
            {
                Image bodyImage = new Image(TextureManager.getTexture("data/sprites/player/rocket-launcher/body.png"));
                addChildAt(bodyImage, -bodyImage.getWidth() / 2, -bodyImage.getHeight() / 2);
            }
        };
        addChild(body);

        gun = new TimeObject() {
            {
                Image gunImage = new Image(TextureManager.getTexture("data/sprites/player/rocket-launcher/head.png"));
                addChildAt(gunImage, -27f, -gunImage.getHeight() / 2);
            }
        };
        addChild(gun);
    }

    @Override
    protected Bullet produceBullet() {
        return new PlayerRocket();
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
