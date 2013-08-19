package game.types.weapons.bullets;

import java.util.ArrayList;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class PlayerRocket extends Rocket {

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
