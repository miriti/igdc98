package game.types.mobs.player;

import engine.core.TextureManager;
import engine.display.DisplayObject;
import engine.display.Image;
import game.types.Collidable;
import game.types.TimeObject;
import game.types.mobs.ControlledMob;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Helicopter extends ControlledMob {

    private TimeObject body;
    private DisplayObject propeller;

    @Override
    protected void initMob() {
        body = new TimeObject() {
            {
                Image bodyImage = new Image(TextureManager.getTexture("data/sprites/helicopter/body.png"));
                addChildAt(bodyImage, -155f, -bodyImage.getHeight() / 2);
            }
        };

        addChild(body);

        propeller = new DisplayObject() {
            {
                Image propellerImage = new Image(TextureManager.getTexture("data/sprites/helicopter/propeller.png"));
                addChildAt(propellerImage, -propellerImage.getWidth() / 2, -propellerImage.getHeight() / 2);
            }

            @Override
            public void update(long deltaTime) {
                super.update(deltaTime);
                rotation += 20f;
            }
        };

        addChild(propeller);

        initHealth(100);
        initHealthBar(0, -80);
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        body.rotation = bodyRotation;
    }

    @Override
    public void collision(Collidable with) {
    }

    @Override
    public float getRadius() {
        return 35;
    }
}
