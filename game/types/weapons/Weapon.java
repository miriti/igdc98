package game.types.weapons;

import engine.interfaces.Updateable;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Weapon implements Updateable {

    public void fireStart() {
    }

    public void fireFinished() {
    }

    public void fireProcess() {
    }

    protected void generateFire() {
    }

    @Override
    public void update(long deltaTime) {
    }

    @Override
    public boolean finished() {
        return false;
    }
}
