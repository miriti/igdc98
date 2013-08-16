package game.types.mobs;

import engine.core.TextureManager;
import engine.display.Image;
import game.types.TimeObject;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Tank extends ControlledMob {

    protected Body body;
    protected Gun gun;

    @Override
    protected void initMob() {
        body = new Body();
        addChild(body);

        gun = new Gun();
        addChild(gun);

        initHealth(100);
        initHealthBar(0, -80);
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        body.rotation = bodyRotation;
        gun.rotation = gunRotation;
    }
}

class Body extends TimeObject {

    private final Image body;

    public Body() {
        body = new Image(TextureManager.getTexture("data/sprites/tank/body.png"));
        addChildAt(body, -body.getWidth() / 2, -body.getHeight() / 2);
    }
}

class Gun extends TimeObject {

    private final Image gun;

    public Gun() {
        gun = new Image(TextureManager.getTexture("data/sprites/tank/gun.png"));
        addChildAt(gun, -8f, -gun.getHeight() / 2);
    }
}