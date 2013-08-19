package game.types.weapons.bullets;

import engine.core.TextureManager;
import engine.core.types.Color;
import engine.display.DisplayObject;
import engine.display.Image;
import game.Sounds;


/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
abstract public class Rocket extends Bullet {

    private Image image;
    private int exhaustDelay = 100;

    public Rocket() {
        speed = 15;
        hitPower = 50;
    }

    @Override
    protected void initBullet() {
        image = new Image(TextureManager.getTexture("data/sprites/rocket.png"));
        addChildAt(image, -image.getWidth() / 2, -image.getHeight() / 2);
    }

    @Override
    protected void playSound() {
        Sounds.getInstance().sndMissile.playAsSoundEffect(1, 1, false);
    }

    
    
    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        rotation = (float) (Math.atan2(speedVector.y, speedVector.x) * (180 / Math.PI));
        if (parent != null) {
            if (exhaustDelay >= 50) {
                parent.addChildAt(new RocketExhaust(), position.x, position.y);
                exhaustDelay = 0;
            } else {
                exhaustDelay += deltaTime;
            }
        }
    }
}
class RocketExhaust extends DisplayObject {

    private final Image image;
    private int ttl = 1000;

    public RocketExhaust() {
        image = new Image(TextureManager.getTexture("data/sprites/rocket-exhaust.png"));
        addChildAt(image, -image.getWidth() / 2, -image.getHeight() / 2);

        scaleX = scaleY = 0.1f;

        setColor(new Color(1, 1, 1));

        rotation = (float) (Math.random() * 360);
    }    

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        color.a = ttl / 1000;
        scaleX = scaleY += 0.025f;

        if (ttl <= 0) {
            parent.removeChild(this);
        }

        ttl -= deltaTime;
    }
}
