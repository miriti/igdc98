package game.types.mobs.enemy;

import engine.core.TextureManager;
import engine.display.DisplayObject;
import engine.display.Image;
import game.types.Collidable;
import game.types.weapons.bullets.EnemyBullet;
import game.types.weapons.bullets.EnemyRocket;
import java.util.ArrayList;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class RocketTower extends EnemyMob {

    private Image body;
    private DisplayObject gun;
    private int fireDelay = 1000;
    private static final int FIRE_DELAY = 1000;

    public RocketTower() {
        reward = 50;
        lethalRadius = 700;
        initHealth(250);
        initHealthBar(0, -50);
    }

    @Override
    protected void initMob() {
        body = new Image(TextureManager.getTexture("data/sprites/enemy/RocketTower/body.png"));
        addChildAt(body, -body.getWidth() / 2, -body.getHeight() / 2);

        gun = new DisplayObject() {
            {
                Image gunImage = new Image(TextureManager.getTexture("data/sprites/enemy/RocketTower/gun.png"));
                addChildAt(gunImage, -15, -16);
            }
        };
        addChildAt(gun, 0, -20);
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
        return 40;
    }

    @Override
    public ArrayList<String> getCollideWith() {
        return null;
    }

    @Override
    protected void produceBullet() {
        EnemyRocket bullet = new EnemyRocket();
        bullet.launch(vectorToPlayer);
        parent.addChildAt(bullet, position.x, position.y);
    }

    @Override
    protected void fire() {
        if (fireDelay >= FIRE_DELAY) {
            produceBullet();
            fireDelay = 0;
        }
    }
}
