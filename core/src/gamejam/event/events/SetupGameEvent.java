package gamejam.event.events;

import gamejam.GameManager;
import gamejam.event.Event;

public class SetupGameEvent implements Event {

    public SetupGameEvent() {
        GameManager.setupGame();
    }

    @Override
    public String getType() {
        return "SetupGameChange";
    }


}
