package game.types;

import engine.display.DisplayObject;
import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class TimeObject extends DisplayObject {

    protected HashMap<Long, TimeObjectFrame> timeline = new HashMap<>();
    protected boolean recording = false;

    public TimeObject() {
    }

    protected TimeObjectFrame getFrame() {
        TimeObjectFrame tof = new TimeObjectFrame();
        tof.position = new Vector3f(position);
        tof.rotation = rotation;

        return tof;
    }

    protected void restoreFromFrame(TimeObjectFrame frame) {
        position.set(frame.position);
        rotation = frame.rotation;
    }

    public void record(long frameNumber) {
        for (int i = 0; i < children.size(); i++) {
            DisplayObject c = children.get(i);
            if (c instanceof TimeObject) {
                ((TimeObject) c).record(frameNumber);
            }
        }
        timeline.put(frameNumber, getFrame());
    }

    public void playback(long frameNumber) {
        if (timeline.get(frameNumber) != null) {
            for (int i = 0; i < children.size(); i++) {
                DisplayObject c = children.get(i);
                if (c instanceof TimeObject) {
                    ((TimeObject) c).playback(frameNumber);
                }
            }

            restoreFromFrame(timeline.get(frameNumber));
        } else {
            setVisible(false);
        }
    }

    public boolean isRecording() {
        return recording;
    }

    public void setRecording(boolean recording) {
        this.recording = recording;
    }
}
