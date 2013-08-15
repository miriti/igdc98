package game.types.mobs.enemy;

import engine.core.types.Color;
import engine.display.DisplayObject;
import engine.display.Quad;
import game.core.CollisionEngine;
import game.types.Collidable;
import game.types.weapons.bullets.Bullet;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class BossEnemy extends EnemyMob {
    
    private Quad body;
    
    @Override
    protected void initMob() {
        body = new Quad(200, 200, Color.RED, true);
        addChild(body);
        
        initHealth(100);
        initHealthBar(0, -150);
    }
    
    @Override
    public void collision(Collidable with) {
        if (with instanceof Bullet) {
        }
    }
    
    @Override
    public float getRadius() {
        return 100;
    }
    
    @Override
    public void onAdded(DisplayObject toParent) {
        super.onAdded(toParent);
        CollisionEngine.register(this);
    }
    
    @Override
    public void onRemoved(DisplayObject fromParent) {
        super.onRemoved(fromParent);
        CollisionEngine.unregister(this);
    }
}
