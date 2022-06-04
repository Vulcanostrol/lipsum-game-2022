package gamejam.event.events;

import gamejam.event.Event;
import gamejam.event.EventType;

public abstract class MouseEvent implements Event {

    private final int xScreen;
    private final int yScreen;
    private final int button;

    public MouseEvent(int xScreen, int yScreen, int button) {
        this.xScreen = xScreen;
        this.yScreen = yScreen;
        this.button = button;
    }

    public int getScreenX() {
        return xScreen;
    }
    public int getScreenY() {
        return yScreen;
    }
    public int getMouseButton() {
        return this.button;
    }
}
