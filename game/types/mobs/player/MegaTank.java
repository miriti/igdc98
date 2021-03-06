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
public class MegaTank extends ControlledMob {

    private TimeObject body;
    private TimeObject head;

    public MegaTank() {
        automatedFire = true;
        fireDelay = 300;

        initHealth(1000);
        initHealthBar(0, -80);
    }

    @Override
    protected void initMob() {
        body = new TimeObject() {
            {
                Image bodyImage = new Image(TextureManager.getTexture("data/sprites/player/mega-tank/body.png"));
                addChildAt(bodyImage, -bodyImage.getWidth() / 2, -bodyImage.getHeight() / 2);
            }
        };
        addChild(body);

        head = new TimeObject() {
            {
                Image headImage = new Image(TextureManager.getTexture("data/sprites/player/mega-tank/head.png"));
                addChildAt(headImage, -40f, -headImage.getHeight() / 2);
            }
        };
        addChild(head);
    }

    @Override
    protected Bullet produceBullet() {
        return new PlayerRocket();
    }

    @Override
    protected void fireBullet() {
        Bullet leftBullet = produceBullet();
        Bullet rightBullet = produceBullet();

        leftBullet.getPosition().set(position.x + 25 * (float) Math.sin(gunRotation * (Math.PI / 180)), position.y - 25 * (float) Math.cos(gunRotation * (Math.PI / 180)));
        rightBullet.getPosition().set(position.x - 25 * (float) Math.sin(gunRotation * (Math.PI / 180)), position.y + 25 * (float) Math.cos(gunRotation * (Math.PI / 180)));

        leftBullet.launch(aimVector);
        rightBullet.launch(aimVector);

        parent.addChild(leftBullet);
        parent.addChild(rightBullet);
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        body.rotation = bodyRotation;
        head.rotation = gunRotation;
    }

    @Override
    public void collision(Collidable with) {
    }

    @Override
    public float getRadius() {
        return 80;
    }
}
