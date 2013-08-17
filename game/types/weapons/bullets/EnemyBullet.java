package game.types.weapons.bullets;

import game.types.mobs.Mob;
import java.util.ArrayList;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class EnemyBullet extends Bullet {

    private final static ArrayList<String> collisionWith;

    static {
        collisionWith = new ArrayList<>();
        collisionWith.add("player");
    }

    @Override
    protected void hit(Mob hit) {
    }

    @Override
    public String getGroup() {
        return "enemyBullet";
    }

    @Override
    public ArrayList<String> getCollideWith() {
        return collisionWith;
    }
}
