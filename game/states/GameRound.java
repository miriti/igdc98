package game.states;

import engine.core.Scheduler;
import engine.core.SchedulerEvent;
import engine.core.TextureManager;
import engine.devices.input.PCInput;
import engine.display.DisplayObject;
import engine.display.Image;
import game.GameCore;
import game.core.CollisionEngine;
import game.types.Collidable;
import game.types.TimeObject;
import game.types.mobs.ControlledMob;
import game.types.mobs.enemy.AutoTower;
import game.types.mobs.enemy.BulletTower;
import game.types.mobs.enemy.EnemyMob;
import game.types.mobs.enemy.RocketTower;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class GameRound extends TimeObject {

    private static ControlledMob currentPlayer;
    public static final int MAP_SCALE = 100;
    public static final int INIT_MONEY = 200;

    static void resetAll() {
        instance = null;
        money = INIT_MONEY;
        preMobs = new ArrayList<>();
    }
    private Image ground;
    private ControlledMob player;
    private ArrayList<Collidable> collidables = new ArrayList<>();
    private static ArrayList<ControlledMob> preMobs = new ArrayList<>();
    private long frameNumber = 0;
    private static float money = INIT_MONEY;
    private static GameRound instance;
    private ArrayList<EnemyMob> activeEnemies = new ArrayList<>();
    private boolean win;

    public GameRound(ControlledMob playerMob) {
        instance = this;
        currentPlayer = player = playerMob;
        initEnemies();

        for (ControlledMob mob : preMobs) {
            mob.setHaveControll(false);
            addChild(mob);
        }

        addChild(player);


    }

    private void initEnemies() {
        try {
            BufferedImage mapImage = ImageIO.read(getClass().getResource("/data/maps/map.png"));
            ground = new Image(TextureManager.getTexture("data/sand-texture.jpg"), mapImage.getWidth() * 100, mapImage.getHeight() * 100);
            ground.setTiles(mapImage.getWidth() * 100 / 600, mapImage.getHeight() * 100 / 600);
            addChild(ground);
            for (int i = 0; i < mapImage.getWidth(); i++) {
                for (int j = 0; j < mapImage.getHeight(); j++) {
                    int pxColor = mapImage.getRGB(i, j);
                    int red = (pxColor & 0x00ff0000) >> 16;
                    int green = (pxColor & 0x0000ff00) >> 8;
                    int blue = pxColor & 0x000000ff;

                    EnemyMob enemy = null;

                    if ((red == 255) && (green == 0) && (blue == 0)) {
                        enemy = new BulletTower();
                    }

                    if ((red == 255) && (green == 106) && (blue == 0)) {
                        enemy = new AutoTower();
                    }

                    if ((red == 255) && (green == 216) && (blue == 0)) {
                        enemy = new RocketTower();
                    }

                    if (enemy != null) {
                        enemy.getPosition().set(i * MAP_SCALE, j * MAP_SCALE);
                        addChild(enemy);
                        activeEnemies.add(enemy);
                    }

                    if ((red == 255) && (green == 0) && (blue == 110)) {
                        player.getPosition().set(i * MAP_SCALE, j * MAP_SCALE);
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(GameRound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static GameRound getInstance() {
        return instance;
    }

    public static ControlledMob getCurrentPlayer() {
        return currentPlayer;
    }

    public void restart() {
        preMobs.add(player);

        for (int i = children.size() - 1; i >= 0; i--) {
            children.get(i).getParent().removeChild(children.get(i));
        }

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
        System.out.println("Money: +" + amout + "(" + money + ")");
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

    public boolean checkWinState() {
        for (EnemyMob aEnemy : activeEnemies) {
            if (!aEnemy.isDead()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        if (!win) {
            if (checkWinState()) {
                win = true;
                updateables.add(new Scheduler().addEvent(new SchedulerEvent(1000, false) {
                    @Override
                    public void execute() {
                        GameCore.getInstance().setState(new Win());
                    }
                }));
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                GameCore.getInstance().setState(new MainMenu());
                return;
            }

            for (ControlledMob m : preMobs) {
                m.playback(frameNumber);
            }

            player.record(frameNumber);

            frameNumber++;

            CollisionEngine.exec();
        }

        position.x = (1920 / 2) - (player.getPosition().x + player.getAimVector().x / 2);
        position.y = (1080 / 2) - (player.getPosition().y + player.getAimVector().y / 2);
    }
}
