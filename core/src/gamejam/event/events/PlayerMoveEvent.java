package gamejam.event.events;

import gamejam.event.Event;
import gamejam.event.EventType;

public class PlayerMoveEvent implements Event {
    public final float newX;
    public final float newY;

    public PlayerMoveEvent(float newX, float newY) {
        this.newX = newX;
        this.newY = newY;
    }


    @Override
    public String getType() {
        return EventType.PLAYER_MOVE;
    }
}
