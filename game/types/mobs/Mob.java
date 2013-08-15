package game.types.mobs;

import game.types.TimeObject;

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

    protected void initHealth(float value) {
        health = healthMax = value;
    }

    protected void initHealthBar(float atX, float atY) {
        healthBar = new MobHealthBar(healthMax);
        addChildAt(healthBar, atX, atY);
    }

    public void hit(float hitPower) {
        health -= hitPower;
        if (health <= 0) {
            health = 0;
            death();
        }else{
            if(healthBar != null){
                healthBar.updateValue(health);
            }
        }
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
}
