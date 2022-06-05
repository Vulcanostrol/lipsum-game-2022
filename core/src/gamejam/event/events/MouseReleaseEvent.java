package gamejam.event.events;

import gamejam.event.EventType;

public class MouseReleaseEvent extends MouseButtonEvent {

    public MouseReleaseEvent(int xScreen, int yScreen, int button) {
        super(xScreen, yScreen, button);
    }

    @Override
    public String getType() {
        return EventType.MOUSE_RELEASE_EVENT;
    }
}
