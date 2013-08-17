package game.types.mobs.player;

import engine.core.TextureManager;
import engine.display.Image;
import game.types.Collidable;
import game.types.TimeObject;
import game.types.mobs.ControlledMob;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class MegaTank extends ControlledMob {

    private TimeObject body;
    private TimeObject head;

    @Override
    protected void initMob() {
        body = new TimeObject() {
            {
                Image bodyImage = new Image(TextureManager.getTexture("data/sprites/mega-tank/body.png"));
                addChildAt(bodyImage, -bodyImage.getWidth() / 2, -bodyImage.getHeight() / 2);
            }
        };
        addChild(body);

        head = new TimeObject() {
            {
                Image headImage = new Image(TextureManager.getTexture("data/sprites/mega-tank/head.png"));
                addChildAt(headImage, -40f, -headImage.getHeight() / 2);
            }
        };
        addChild(head);

        initHealth(100);
        initHealthBar(0, -80);
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        body.rotation = bodyRotation;
        head.rotation = gunRotation;
    }

    @Override
    public void collision(Collidable with) {
    }

    @Override
    public float getRadius() {
        return 80;
    }
}
