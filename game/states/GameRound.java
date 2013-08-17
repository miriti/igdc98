package game.states;

import engine.core.TextureManager;
import engine.devices.input.PCInput;
import engine.display.DisplayObject;
import engine.display.Image;
import game.GameCore;
import game.core.CollisionEngine;
import game.types.Collidable;
import game.types.TimeObject;
import game.types.mobs.ControlledMob;
import game.types.mobs.enemy.BossEnemy;
import java.util.ArrayList;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class GameRound extends TimeObject {

    private static ControlledMob currentPlayer;
    private final Image ground;
    private ControlledMob player;
    private ArrayList<Collidable> collidables = new ArrayList<>();
    private static ArrayList<ControlledMob> preMobs = new ArrayList<>();
    private long frameNumber = 0;
    private static float money = 5000;
    private static GameRound instance;

    public GameRound(ControlledMob playerMob) {
        instance = this;
        ground = new Image(TextureManager.getTexture("data/grass-texture.jpg"), 6000, 6000);
        ground.setTiles(10, 10);
        addChildAt(ground, -3000, -3000);

        addChildAt(new BossEnemy(), 0, 0);
        addChildAt(new BossEnemy(), 1000, 1000);
        addChildAt(new BossEnemy(), 2000, 0);

        for (ControlledMob mob : preMobs) {
            mob.setHaveControll(false);
            addChild(mob);
        }

        currentPlayer = player = playerMob;

        addChildAt(player, 1920 / 2, 1080 / 2);
    }

    public static GameRound getInstance() {
        return instance;
    }

    public static ControlledMob getCurrentPlayer() {
        return currentPlayer;
    }

    public void restart() {
        preMobs.add(player);
        GameCore.getInstance().setState(new Shop());
    }

    @Override
    public void onAdded(DisplayObject toParent) {
        if (getInput().getType().equals("pc")) {
            ((PCInput) getInput()).setMouseGrabbed(true);
        }
        super.onAdded(toParent);
    }

    public static float addMoney(float amout) {
        money += amout;
        System.out.println("Money: +" + amout);
        return money;
    }

    public static float getMoney() {
        return money;
    }

    @Override
    public void onRemoved(DisplayObject fromParent) {
        if (getInput().getType().equals("pc")) {
            ((PCInput) getInput()).setMouseGrabbed(false);
        }
        super.onRemoved(fromParent);
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

        position.x = (1920 / 2) - player.getPosition().x;
        position.y = (1080 / 2) - player.getPosition().y;
    }
}
