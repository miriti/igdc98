package game.types.mobs.enemy;

import engine.core.TextureManager;
import engine.display.DisplayObject;
import engine.display.Image;
import game.types.Collidable;
import game.types.weapons.bullets.EnemyBullet;
import java.util.ArrayList;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class BulletTower extends EnemyMob {

    private Image body;
    private DisplayObject gun;
    private int fireDelay = 0;
    private final static int FIRE_DELAY = 500;

    public BulletTower() {
        reward = 10;
        lethalRadius = 1000;
        initHealth(50);
        initHealthBar(0, -40);
    }

    @Override
    protected void initMob() {
        body = new Image(TextureManager.getTexture("data/sprites/enemy/BulletTower/body.png"));
        addChildAt(body, -body.getWidth() / 2, -body.getHeight() / 2);

        gun = new DisplayObject() {
            {
                addChildAt(new Image(TextureManager.getTexture("data/sprites/enemy/BulletTower/head.png")), -23, -32);
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
        return 32;
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
