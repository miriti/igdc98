package game.types.mobs;

import engine.core.types.Color;
import engine.devices.input.GamePadInput;
import engine.display.Quad;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class ControlledMob extends Mob {

    protected final float CROSSHAIR_DISTANCE = 400;
    private Quad body;
    private Quad gun;
    private Quad crosshair;

    public ControlledMob() {
        super();

        body = new Quad(100, 100, Color.RED, true);
        addChild(body);

        gun = new Quad(80, 50, Color.LIME, true);
        addChild(gun);

        crosshair = new Quad(10, 10, Color.YELLOW, true);
        crosshair.setVisible(false);
        addChildAt(crosshair, 0, -CROSSHAIR_DISTANCE);
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        float lx = ((GamePadInput) getInput()).getData("x");
        float ly = ((GamePadInput) getInput()).getData("y");

        float rx = ((GamePadInput) getInput()).getData("rx");
        float ry = ((GamePadInput) getInput()).getData("ry");
        float gz = ((GamePadInput) getInput()).getData("z");

        if ((Math.abs(lx) > 0.2f) || (Math.abs(ly) > 0.2f)) {
            position.x += lx * 5;
            position.y += ly * 5;
            body.rotation = (float) ((float) Math.atan2(ly, lx) * (180f / Math.PI));
        }

        if ((Math.abs(rx) > 0.5f) || (Math.abs(ry) > 0.5f)) {
            crosshair.setVisible(true);
            crosshair.getPosition().set(rx * CROSSHAIR_DISTANCE, ry * CROSSHAIR_DISTANCE);
            gun.rotation = (float) ((float) Math.atan2(ry, rx) * (180f / Math.PI));
        } else {
            crosshair.setVisible(false);
        }

        if ((gz < -0.5) && (gz > -1.5)) {
            System.out.println("Fire");
        }
    }
}
