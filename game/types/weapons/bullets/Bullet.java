package game.types.weapons.bullets;

import engine.core.TextureManager;
import engine.display.DisplayObject;
import engine.display.Image;
import game.Sounds;
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

    private Image image;
    protected Vector2f speedVector;
    protected long liveTime = 0;
    protected float hitPower = 10;
    protected float speed = 40;
    protected long timeToLive = 2000;
    protected float maxDistance = 1000;
    private Vector2f flewDistance = new Vector2f();

    public Bullet() {
        initBullet();
    }

    protected void initBullet() {
        image = new Image(TextureManager.getTexture("data/sprites/bullet.png"));
        addChildAt(image, -image.getWidth() / 2, -image.getHeight() / 2);
    }

    public void launch(Vector2f vector) {
        speedVector = new Vector2f();
        speedVector.set(vector);
        float l = speedVector.length();
        speedVector.x /= l;
        speedVector.y /= l;

        speedVector.x *= speed;
        speedVector.y *= speed;

        playSound();
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        if ((liveTime >= timeToLive) || (flewDistance.length() >= maxDistance)) {
            parent.removeChild(this);
        } else {
            liveTime += deltaTime;
            position.x += speedVector.x;
            position.y += speedVector.y;
            flewDistance.x += speedVector.x;
            flewDistance.y += speedVector.y;
        }
    }

    protected void playSound() {
        Sounds.getInstance().sndShot.playAsSoundEffect(1, 1, false);
    }

    protected void hit(Mob hit) {
        hit.hit(hitPower);
    }

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
        return 5f;
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
