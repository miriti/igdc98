package game.types.mobs;

import engine.core.Scheduler;
import engine.core.SchedulerEvent;
import engine.core.TextureManager;
import engine.devices.input.GamePadInput;
import engine.display.DisplayObject;
import engine.display.Image;
import game.GameCore;
import game.core.CollisionEngine;
import game.states.GameOver;
import game.states.GameRound;
import game.types.Collidable;
import game.types.TimeObjectFrame;
import game.types.weapons.bullets.Bullet;
import game.types.weapons.bullets.PlayerBullet;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
abstract public class ControlledMob extends Mob implements Collidable {

    protected final float CROSSHAIR_DISTANCE = 400;
    protected final float PC_CROSSHAIR_DISTANCE = 700;
    protected Vector2f aimVector = new Vector2f(1, 0);
    public DisplayObject crosshair;
    protected float bodyRotation = 0;
    protected float gunRotation = 0;
    protected boolean haveControll = true;
    private boolean _fire;
    private boolean fireOn;
    protected int maxSpeed = 5;
    protected boolean automatedFire = false;
    protected int fireDelay = 200;
    private int fireTime = 0;

    public ControlledMob() {
        super();
    }

    @Override
    public void hit(float hitPower) {
        if (haveControll) {
            super.hit(hitPower);
        }
    }

    @Override
    protected void deathAction() {
        if (haveControll) {
            Scheduler sched = new Scheduler();

            SchedulerEvent ev;
            haveControll = false;

            if (GameRound.getMoney() >= 100) {
                ev = new SchedulerEvent(1000, false) {
                    @Override
                    public void execute() {
                        GameRound.getInstance().restart();
                    }
                };
            } else {
                ev = new SchedulerEvent(2000, false) {
                    @Override
                    public void execute() {
                        GameCore.getInstance().setState(new GameOver());
                    }
                };
            }

            sched.addEvent(ev);
            parent.updateables.add(sched);
        }

        super.deathAction();
    }

    @Override
    public String getGroup() {
        return "player";
    }

    @Override
    public ArrayList<String> getCollideWith() {
        return null;
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

    @Override
    public void onAdded(DisplayObject toParent) {
        super.onAdded(toParent);
        initCrosshair();
        CollisionEngine.register(this);
    }

    @Override
    public void onRemoved(DisplayObject fromParent) {
        super.onRemoved(fromParent);
        CollisionEngine.unregister(this);
    }

    private void initCrosshair() {
        if (haveControll) {
            crosshair = new DisplayObject() {
                {
                    Image image = new Image(TextureManager.getTexture("data/crosshair.png"));
                    addChildAt(image, -image.getWidth() / 2, -image.getHeight() / 2);
                }
            };
            addChildAt(crosshair, 0, -CROSSHAIR_DISTANCE);
        }
    }

    /**
     * Control mob with Gamepad
     *
     */
    private void controllGamePad() {
        float lx = ((GamePadInput) getInput()).getFloatData("x");
        float ly = ((GamePadInput) getInput()).getFloatData("y");

        float rx = ((GamePadInput) getInput()).getFloatData("rx");
        float ry = ((GamePadInput) getInput()).getFloatData("ry");
        float gz = ((GamePadInput) getInput()).getFloatData("z");

        if ((Math.abs(lx) > 0.2f) || (Math.abs(ly) > 0.2f)) {
            position.x += lx * maxSpeed;
            position.y += ly * maxSpeed;
            bodyRotation = (float) ((float) Math.atan2(ly, lx) * (180f / Math.PI));
        }

        if ((Math.abs(rx) > 0.5f) || (Math.abs(ry) > 0.5f)) {
            aimVector.set(rx * CROSSHAIR_DISTANCE, ry * CROSSHAIR_DISTANCE);
            crosshair.getPosition().set(aimVector.x, aimVector.y);
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
    }

    /**
     * Control mob with keyboard and mouse
     *
     */
    private void controllPC() {
        // TODO Заменить на getInput().isLeft() и т.д.
        boolean isLeft = getInput().getBoolData("a") || getInput().getBoolData("left");
        boolean isRight = getInput().getBoolData("d") || getInput().getBoolData("right");
        boolean isDown = getInput().getBoolData("s") || getInput().getBoolData("down");
        boolean isUp = getInput().getBoolData("w") || getInput().getBoolData("up");

        Vector3f prevPos = new Vector3f();
        prevPos.set(position);

        if (isLeft) {
            position.x -= maxSpeed;
        }

        if (isRight) {
            position.x += maxSpeed;
        }

        if (isUp) {
            position.y -= maxSpeed;
        }

        if (isDown) {
            position.y += maxSpeed;
        }

        if (isLeft || isRight || isDown || isUp) {
            bodyRotation = (float) (Math.atan2(position.y - prevPos.y, position.x - prevPos.x) * (180f / Math.PI));
        }

        crosshair.getPosition().x += getInput().getIntData("mousedx");
        crosshair.getPosition().y -= getInput().getIntData("mousedy");

        if (crosshair.getPosition().length() > PC_CROSSHAIR_DISTANCE) {
            float l = crosshair.getPosition().length();
            crosshair.getPosition().x /= l;
            crosshair.getPosition().y /= l;

            crosshair.getPosition().x *= PC_CROSSHAIR_DISTANCE;
            crosshair.getPosition().y *= PC_CROSSHAIR_DISTANCE;
        }


        aimVector.set(crosshair.getPosition().x, crosshair.getPosition().y);

        gunRotation = (float) ((float) Math.atan2(aimVector.y, aimVector.x) * (180f / Math.PI));

        boolean leftButton = getInput().getBoolData("button0");
        boolean rightButton = getInput().getBoolData("button1");

        if (leftButton) {
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

    private void controll() {
        if (getInput().getType().equals("pc")) {
            controllPC();
        } else {
            controllGamePad();
        }
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        fireTime -= deltaTime;
        if (fireTime < 0) {
            fireTime = 0;
        }

        if (haveControll) {
            controll();
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

    protected Bullet produceBullet() {
        return new PlayerBullet();
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
        fireTime = 0;
    }

    protected void fireStart() {
        fireBullet();
        if (automatedFire) {
            fireTime = fireDelay;
        }
    }

    protected void fireBullet() {
        Bullet bullet = produceBullet();
        bullet.launch(aimVector);
        parent.addChildAt(bullet, position.x, position.y);
    }

    protected void fireProcess() {
        if (automatedFire) {
            if (fireTime <= 0) {
                fireBullet();
                fireTime = fireDelay;
            }
        }
    }

    public boolean isHaveControll() {
        return haveControll;
    }

    public void setHaveControll(boolean haveControll) {
        this.haveControll = haveControll;
        if (!haveControll) {
            if (healthBar != null) {
                removeChild(healthBar);
                healthBar = null;
            }
            if (crosshair != null) {
                crosshair.setVisible(false);
            }
        }
    }

    public Vector2f getAimVector() {
        return aimVector;
    }
}
