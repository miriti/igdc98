package game.types.mobs.enemy;

import engine.core.TextureManager;
import engine.display.DisplayObject;
import engine.display.Image;
import game.types.Collidable;
import java.util.ArrayList;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class AutoTower extends EnemyMob {

    private Image body;
    private DisplayObject gun;
    private int fireDelay;
    private static final int FIRE_DELAY = 250;

    public AutoTower() {
        reward = 25;
        lethalRadius = 750;
        initHealth(200);
        initHealthBar(0, -40);
    }

    @Override
    protected void initMob() {
        body = new Image(TextureManager.getTexture("data/sprites/enemy/AutoTower/body.png"));
        addChildAt(body, -body.getWidth() / 2, -body.getHeight() / 2);

        gun = new DisplayObject() {
            {
                Image gunImage = new Image(TextureManager.getTexture("data/sprites/enemy/AutoTower/head.png"));
                addChildAt(gunImage, -23, -32);
            }
        };
        addChild(gun);
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        gun.rotation = angleToPlayer;
        fireDelay += deltaTime;
    }

    @Override
    public void collision(Collidable with) {
    }

    @Override
    public float getRadius() {
        return 45;
    }

    @Override
    public ArrayList<String> getCollideWith() {
        return null;
    }

    @Override
    protected void fire() {
        if (fireDelay >= FIRE_DELAY) {
            produceBullet();
            fireDelay = 0;
        }
    }
}
