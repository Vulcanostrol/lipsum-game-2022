package gamejam.event.events;

import gamejam.event.Event;
import gamejam.event.EventType;

public class LevelChangeEvent implements Event {
    @Override
    public String getType() {
        return EventType.LEVEL_CHANGED;
    }
}
