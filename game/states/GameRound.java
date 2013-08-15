package game.states;

import engine.core.TextureManager;
import engine.display.DisplayObject;
import engine.display.Image;
import game.GameCore;
import game.core.CollisionEngine;
import game.types.Collidable;
import game.types.TimeObject;
import game.types.mobs.ControlledMob;
import game.types.mobs.SimpleMob;
import game.types.mobs.enemy.BossEnemy;
import java.util.ArrayList;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class GameRound extends TimeObject {

    private final Image ground;
    private ControlledMob player;
    private ArrayList<Collidable> collidables = new ArrayList<>();
    private static ArrayList<ControlledMob> preMobs = new ArrayList<>();
    private long frameNumber = 0;
    private static boolean r_pressed = false;

    public GameRound() {
        ground = new Image(TextureManager.getTexture("data/grass-texture.jpg"), 6000, 6000);
        ground.setTiles(10, 10);
        //addChildAt(ground, -3000, -3000);

        System.out.println(preMobs.size());
        for (ControlledMob mob : preMobs) {
            mob.setHaveControll(false);
            mob.playback(0);
            addChild(mob);
        }

        player = new SimpleMob();
        addChildAt(player, 1920 / 2, 1080 / 2);

        addChildAt(new BossEnemy(), 0, 0);
    }

    public void restart() {
        preMobs.add(player);
        GameCore.getInstance().setState(new GameRound());
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        for (ControlledMob m : preMobs) {
            m.playback(frameNumber);
        }

        player.record(frameNumber);

        frameNumber++;

        CollisionEngine.exec();

        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            if (!r_pressed) {
                r_pressed = true;
                restart();
            }
        } else {
            r_pressed = false;
        }

        position.x = (1920 / 2) - player.getPosition().x;
        position.y = (1080 / 2) - player.getPosition().y;
    }
}
