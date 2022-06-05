package gamejam.event.events;

import gamejam.event.Event;
import gamejam.event.EventType;

public abstract class MouseEvent implements Event {
    private final int xScreen;
    private final int yScreen;

    public MouseEvent(int xScreen, int yScreen) {
        this.xScreen = xScreen;
        this.yScreen = yScreen;
    }

    public int getScreenX() {
        return xScreen;
    }
    public int getScreenY() {
        return yScreen;
    }
}
