package gamejam.event.events;

import gamejam.GameManager;
import gamejam.event.Event;
import gamejam.event.EventType;

public class SetupGameEvent implements Event {

    public SetupGameEvent() {
        GameManager.setupGame();
    }

    @Override
    public String getType() {
        return EventType.SETUP_GAME;
    }


}
