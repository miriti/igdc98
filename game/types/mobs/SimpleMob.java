package game.types.mobs;

import engine.core.types.Color;
import engine.display.Quad;
import game.types.TimeObject;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class SimpleMob extends ControlledMob {

    protected Quad body;
    protected Gun gun;

    @Override
    protected void initMob() {
        body = new Quad(100, 100, Color.RED, true);
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

class Gun extends TimeObject {

    private final Quad gun;

    public Gun() {
        gun = new Quad(80, 50, Color.LIME, true);
        gun.rotation = 90f;
        addChild(gun);
    }
}