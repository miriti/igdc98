package game.types.mobs.enemy;

import game.states.GameRound;
import game.types.Collidable;
import game.types.mobs.Mob;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
abstract public class EnemyMob extends Mob implements Collidable {

    protected float reward = 0;

    @Override
    public String getGroup() {
        return "enemy";
    }

    @Override
    protected void deathAction() {
        GameRound.addMoney(reward);
        super.deathAction();
    }
}
