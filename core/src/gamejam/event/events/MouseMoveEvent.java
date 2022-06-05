package gamejam.event.events;

import gamejam.event.EventType;

public class MouseMoveEvent extends MouseEvent {
    public MouseMoveEvent(int xScreen, int yScreen) {
        super(xScreen, yScreen);
    }

    @Override
    public String getType() {
        return EventType.MOUSE_MOVE_EVENT;
    }
}
