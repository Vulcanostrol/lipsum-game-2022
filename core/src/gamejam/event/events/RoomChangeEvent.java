package gamejam.event.events;

import gamejam.event.Event;
import gamejam.event.EventType;
import gamejam.levels.Direction;

public class RoomChangeEvent implements Event {

    private final Direction direction;
    private final boolean upgrade;

    public RoomChangeEvent(Direction direction, boolean upgrade) {
        this.direction = direction;
        this.upgrade = upgrade;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    @Override
    public String getType() {
        return EventType.ROOM_CHANGED;
    }
}
