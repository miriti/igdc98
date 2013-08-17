package game.types.weapons.bullets;

import engine.core.types.Color;
import engine.display.DisplayObject;
import engine.display.Quad;
import game.core.CollisionEngine;
import game.types.Collidable;
import game.types.TimeObject;
import game.types.mobs.Mob;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
abstract public class Bullet extends TimeObject implements Collidable {

    private final Quad image;
    private long liveTime = 0;
    protected Vector2f speedVector;
    protected float hitPower = 10;
    protected float speed = 20;
    protected long timeToLive = 2000;

    public Bullet() {
        image = new Quad(8, 8, Color.YELLOW, true);
        addChild(image);
    }

    public void launch(Vector2f vector) {
        speedVector = vector;
        speedVector.x *= speed;
        speedVector.y *= speed;
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        if (liveTime >= timeToLive) {
            parent.removeChild(this);
        } else {
            liveTime += deltaTime;
            position.x += speedVector.x;
            position.y += speedVector.y;
        }
    }

    abstract protected void hit(Mob hit);

    @Override
    public void collision(Collidable with) {
        ArrayList<String> collideWith = getCollideWith();
        if (collideWith.contains(with.getGroup())) {
            ((Mob) with).hit(hitPower);
            hit((Mob) with);
            parent.removeChild(this);
        }
    }

    @Override
    public float getRadius() {
        return 8f;
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
}
