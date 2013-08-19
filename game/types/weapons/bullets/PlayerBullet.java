package game.types.weapons.bullets;

import java.util.ArrayList;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class PlayerBullet extends Bullet {

    private static final ArrayList<String> collideWith;

    static {
        collideWith = new ArrayList<>();
        collideWith.add("enemy");
    }

    @Override
    public String getGroup() {
        return "playerBullet";
    }

    @Override
    public ArrayList<String> getCollideWith() {
        return collideWith;
    }
}
