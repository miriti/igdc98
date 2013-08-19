package game.types.mobs.player;

import engine.core.TextureManager;
import engine.display.DisplayObject;
import engine.display.Image;
import game.types.Collidable;
import game.types.TimeObject;
import game.types.mobs.ControlledMob;
import game.types.weapons.bullets.Bullet;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Helicopter extends ControlledMob {

    private TimeObject body;
    private DisplayObject propeller;

    public Helicopter() {

        automatedFire = true;
        fireDelay = 100;

        initHealth(500);
        initHealthBar(0, -80);
    }

    @Override
    protected void initMob() {
        body = new TimeObject() {
            {
                Image bodyImage = new Image(TextureManager.getTexture("data/sprites/player/helicopter/body.png"));
                addChildAt(bodyImage, -155f, -bodyImage.getHeight() / 2);
            }
        };

        addChild(body);

        propeller = new DisplayObject() {
            {
                Image propellerImage = new Image(TextureManager.getTexture("data/sprites/player/helicopter/propeller.png"));
                addChildAt(propellerImage, -propellerImage.getWidth() / 2, -propellerImage.getHeight() / 2);
            }

            @Override
            public void update(long deltaTime) {
                super.update(deltaTime);
                rotation += 20f;
            }
        };

        addChild(propeller);
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
    }

    @Override
    public void collision(Collidable with) {
    }

    @Override
    public float getRadius() {
        return 35;
    }
}
