package game.types.mobs.enemy;

import engine.display.DisplayObject;
import game.core.CollisionEngine;
import game.states.GameRound;
import game.types.Collidable;
import game.types.mobs.Mob;
import game.types.weapons.bullets.EnemyBullet;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
abstract public class EnemyMob extends Mob implements Collidable {

    protected float reward = 0;
    protected float lethalRadius = 200;
    protected float angleToPlayer;
    protected Vector2f vectorToPlayer = new Vector2f();

    @Override
    public String getGroup() {
        return "enemy";
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        if (!GameRound.getCurrentPlayer().isDead()) {
            vectorToPlayer.set(GameRound.getCurrentPlayer().getPosition().x - position.x, GameRound.getCurrentPlayer().getPosition().y - position.y);
            angleToPlayer = (float) (Math.atan2(vectorToPlayer.y, vectorToPlayer.x) * (180 / Math.PI));

            if (vectorToPlayer.length() <= lethalRadius) {
                fire();
            }
        }
    }

    @Override
    protected void deathAction() {
        GameRound.addMoney(reward);
        super.deathAction();
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

    protected void produceBullet() {
        EnemyBullet bullet = new EnemyBullet();
        bullet.launch(vectorToPlayer);
        parent.addChildAt(bullet, position.x, position.y);
    }

    abstract protected void fire();
}
