package game.types.mobs.enemy;

import engine.core.types.Color;
import engine.display.DisplayObject;
import engine.display.Quad;
import game.core.CollisionEngine;
import game.states.GameRound;
import game.types.Collidable;
import game.types.weapons.bullets.EnemyBullet;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class BossEnemy extends EnemyMob {

    private Quad body;
    private int shotDelay = 0;

    public BossEnemy() {
        reward = 250;
    }

    @Override
    protected void initMob() {
        body = new Quad(200, 200, Color.RED, true);
        addChild(body);

        reward = 250;
        initHealth(1000);
        initHealthBar(0, -150);
    }

    @Override
    public void setHealth(float health) {
        super.setHealth(health);
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        if (shotDelay <= 0) {
            shot();
            shotDelay = 250;
        } else {
            shotDelay -= deltaTime;
        }
    }

    @Override
    public void collision(Collidable with) {
    }

    @Override
    public float getRadius() {
        return 100;
    }

    @Override
    public void onAdded(DisplayObject toParent) {
        super.onAdded(toParent);
        CollisionEngine.register(this);
    }

    @Override
    public void onRemoved(DisplayObject fromParent) {
        super.onRemoved(fromParent);
        CollisionEngine.unregister(this);
    }

    @Override
    public ArrayList<String> getCollideWith() {
        return null;
    }

    private void shot() {
        Vector2f vec = new Vector2f(GameRound.getCurrentPlayer().getPosition().x - position.x, GameRound.getCurrentPlayer().getPosition().y - position.y);

        if (Math.abs(vec.length()) < 1000) {
            float l = vec.length();
            vec.x /= l;
            vec.y /= l;

            EnemyBullet bullet = new EnemyBullet();
            bullet.launch(vec);
            parent.addChildAt(bullet, position.x, position.y);
        }
    }
}
