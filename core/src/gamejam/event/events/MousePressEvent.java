package gamejam.event.events;

import gamejam.event.EventType;

public class MousePressEvent extends MouseButtonEvent {

    public MousePressEvent(int xScreen, int yScreen, int button) {
        super(xScreen, yScreen, button);
    }

    @Override
    public String getType() {
        return EventType.MOUSE_PRESS_EVENT;
    }
}
