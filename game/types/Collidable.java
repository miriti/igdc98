package game.types;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public interface Collidable {
    
    void collision(Collidable with);
    
    float getRadius();
    
    Vector3f getPosition();
    
    String getGroup();
    
    ArrayList<String> getCollideWith();
}
