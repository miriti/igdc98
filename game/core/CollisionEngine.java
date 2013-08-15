package game.core;

import game.types.Collidable;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class CollisionEngine {

    public static ArrayList<Collidable> collection;

    static {
        collection = new ArrayList<>();
    }

    public static void register(Collidable col) {
        collection.add(col);
    }

    public static void unregister(Collidable col) {
        collection.remove(col);
    }

    public static void exec() {
        Vector2f lengthMeter = new Vector2f();

        if (collection.size() >= 2) {
            for (int i = 0; i < collection.size() - 1; i++) {
                for (int j = i + 1; j < collection.size(); j++) {
                    Collidable c1 = collection.get(i);
                    Collidable c2 = collection.get(j);

                    lengthMeter.set(c2.getPosition().x - c1.getPosition().x, c2.getPosition().y - c1.getPosition().y);

                    if (Math.abs(lengthMeter.length()) < c1.getRadius() + c2.getRadius()) {
                        c1.collision(c2);
                        c2.collision(c1);
                    }
                }
            }
        }
    }
}
