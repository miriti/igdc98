package game.types.mobs;

import engine.core.types.Color;
import engine.display.DisplayObject;
import engine.display.Quad;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class MobHealthBar extends DisplayObject {

    private Quad back;
    private Quad bar;
    private float maxValue;
    private float barWidth;

    public MobHealthBar(float maxHealth) {
        initBar(80, 10, 0);
        maxValue = maxHealth;
        setColor(new Color(1, 1, 1, 1));
    }

    protected final void initBar(float barWidth, float barHeight, float borderWidth) {
        back = new Quad(barWidth, barHeight, Color.WHITE, true);
        addChild(back);

        bar = new Quad(barWidth - borderWidth, barHeight - borderWidth, Color.LIME);
        addChildAt(bar, -(barWidth - borderWidth) / 2, -(barHeight - borderWidth) / 2);

        this.barWidth = (barWidth - borderWidth);
    }

    public void updateValue(float newValue) {
        float fract = newValue / maxValue;

        color.a = 1;

        if (fract > 0.3) {
            if (fract > 0.6) {
                bar.setColor(Color.LIME);
            } else {
                bar.setColor(Color.YELLOW);
            }
        } else {
            bar.setColor(Color.RED);
        }

        bar.setWidth(barWidth * fract);
    }
}
