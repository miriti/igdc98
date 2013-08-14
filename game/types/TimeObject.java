package game.types;

import engine.Engine;
import engine.display.DisplayObject;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class TimeObject extends DisplayObject {

    protected long createTime;

    public TimeObject() {
        createTime = Engine.getCurrentTime();
    }

    @Override
    public void update(long deltaTime) {
        long shiftedTime = Engine.getCurrentTime() - createTime;
        super.update(deltaTime);
    }
}
