package game.types.mobs;

import engine.core.types.Color;
import engine.devices.input.GamePadInput;
import engine.display.Quad;
import game.types.TimeObjectFrame;
import game.types.weapons.bullets.Bullet;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
abstract public class ControlledMob extends Mob {

    protected final float CROSSHAIR_DISTANCE = 400;
    protected Vector2f aimVector = new Vector2f();
    protected Quad crosshair;
    protected float bodyRotation = 0;
    protected float gunRotation = 0;
    protected boolean haveControll = true;
    private boolean _fire;
    private boolean fireOn;

    public ControlledMob() {
        super();
        initCrosshair();
    }

    @Override
    protected TimeObjectFrame timeFrameFactory() {
        return new ControlledMobTimeFrame();
    }

    @Override
    protected void fillOutFrame(TimeObjectFrame tof) {
        super.fillOutFrame(tof);
        ((ControlledMobTimeFrame) tof).fire = fireOn;
        ((ControlledMobTimeFrame) tof).aimVector = new Vector2f(aimVector);
    }

    @Override
    protected void restoreFromFrame(TimeObjectFrame frame) {
        super.restoreFromFrame(frame);
        fireOn = ((ControlledMobTimeFrame) frame).fire;
        aimVector.set(((ControlledMobTimeFrame) frame).aimVector);
    }

    private void initCrosshair() {
        if (haveControll) {
            crosshair = new Quad(10, 10, Color.YELLOW, true);
            addChildAt(crosshair, 0, -CROSSHAIR_DISTANCE);
        }
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        if (haveControll) {
            float lx = ((GamePadInput) getInput()).getData("x");
            float ly = ((GamePadInput) getInput()).getData("y");

            float rx = ((GamePadInput) getInput()).getData("rx");
            float ry = ((GamePadInput) getInput()).getData("ry");
            float gz = ((GamePadInput) getInput()).getData("z");

            if ((Math.abs(lx) > 0.2f) || (Math.abs(ly) > 0.2f)) {
                position.x += lx * 5;
                position.y += ly * 5;
                bodyRotation = (float) ((float) Math.atan2(ly, lx) * (180f / Math.PI));
            }

            if ((Math.abs(rx) > 0.5f) || (Math.abs(ry) > 0.5f)) {
                aimVector.set(rx, ry);
                float l = aimVector.length();
                aimVector.x /= l;
                aimVector.y /= l;
                crosshair.getPosition().set(aimVector.x * CROSSHAIR_DISTANCE, aimVector.y * CROSSHAIR_DISTANCE);
                gunRotation = (float) ((float) Math.atan2(ry, rx) * (180f / Math.PI));
            }

            if (crosshair.isVisible()) {
                if ((gz < -0.5) && (gz > -1.5)) {
                    fireOn = true;
                    fire();
                } else {
                    fireOn = false;
                    if (_fire) {
                        _fire = false;
                        fireFinished();
                    }
                }
            }
        } else {
            if (fireOn) {
                fire();
            } else {
                if (_fire) {
                    _fire = false;
                    fireFinished();
                }
            }
        }
    }

    private void fire() {
        if (!_fire) {
            _fire = true;
            fireStart();
        } else {
            fireProcess();
        }
    }

    protected void fireFinished() {
    }

    protected void fireStart() {
        Bullet bullet = new Bullet();
        bullet.launch(new Vector2f(aimVector.x, aimVector.y));
        parent.addChildAt(bullet, position.x, position.y);
    }

    protected void fireProcess() {
    }

    public boolean isHaveControll() {
        return haveControll;
    }

    public void setHaveControll(boolean haveControll) {
        this.haveControll = haveControll;
        if (!haveControll) {
            if (crosshair != null) {
                crosshair.setVisible(false);
            }
        }
    }
}
