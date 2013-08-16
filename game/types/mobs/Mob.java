package game.types.mobs;

import game.types.TimeObject;
import game.types.TimeObjectFrame;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
abstract public class Mob extends TimeObject {
    
    protected float health;
    protected float healthMax;
    protected boolean dead;
    private MobHealthBar healthBar;
    
    public Mob() {
        initMob();
    }
    
    @Override
    protected TimeObjectFrame timeFrameFactory() {
        return new MobTimeFrame();
    }
    
    @Override
    protected void fillOutFrame(TimeObjectFrame tof) {
        super.fillOutFrame(tof);
        ((MobTimeFrame) tof).health = health;
    }
    
    @Override
    protected void restoreFromFrame(TimeObjectFrame frame) {
        super.restoreFromFrame(frame);
        setHealth(((MobTimeFrame) frame).health);
    }
    
    protected void initHealth(float value) {
        health = healthMax = value;
    }
    
    protected void initHealthBar(float atX, float atY) {
        healthBar = new MobHealthBar(healthMax);
        addChildAt(healthBar, atX, atY);
    }
    
    public void hit(float hitPower) {
        setHealth(health - hitPower);
    }
    
    public void kill() {
        hit(health);
    }
    
    abstract protected void initMob();
    
    protected void death() {
        dead = true;
        deathAction();
    }
    
    protected void deathAction() {
        if (parent != null) {
            parent.removeChild(this);
        }
    }
    
    public boolean isDead() {
        return dead;
    }
    
    public float getHealth() {
        return health;
    }
    
    public void setHealth(float health) {
        this.health = health;
        if (health <= 0) {
            health = 0;
            if (!dead) {
                death();
            }
        } else {
            if (healthBar != null) {
                healthBar.updateValue(health);
            }
        }
    }
}
