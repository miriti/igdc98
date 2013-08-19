package game.types.mobs.enemy;

import engine.core.types.Color;
import engine.display.Quad;
import game.types.Collidable;
import java.util.ArrayList;

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
    public void collision(Collidable with) {
    }

    @Override
    public float getRadius() {
        return 100;
    }

    @Override
    public ArrayList<String> getCollideWith() {
        return null;
    }

    @Override
    protected void fire() {
    }
}
