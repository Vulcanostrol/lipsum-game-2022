package gamejam.event.events;

import gamejam.event.Event;
import gamejam.event.EventType;

public class MouseMoveEvent implements Event {

    private final int xScreen;
    private final int yScreen;

    public MouseMoveEvent(int xScreen, int yScreen) {
        this.xScreen = xScreen;
        this.yScreen = yScreen;
    }

    public int getScreenX() {
        return xScreen;
    }
    public int getScreenY() {
        return yScreen;
    }

    @Override
    public String getType() {
        return EventType.MOUSE_MOVE_EVENT;
    }
}
