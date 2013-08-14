package game.states;

import engine.display.DisplayObject;
import game.types.TimeObject;
import game.types.mobs.ControlledMob;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class GameRound extends TimeObject {

    public GameRound() {
        addChildAt(new ControlledMob(), 1920 / 2, 1080 / 2);
    }
}
