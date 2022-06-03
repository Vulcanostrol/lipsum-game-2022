package gamejam.event.events;

import gamejam.event.Event;
import gamejam.event.EventType;

public class PlayerDeathEvent implements Event {
    @Override
    public String getType() {
        return EventType.PLAYER_DEATH;
    }
}
