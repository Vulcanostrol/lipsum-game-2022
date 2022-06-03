package gamejam.event.events;

import gamejam.event.Event;
import gamejam.event.EventType;

public class KeyTypedEvent implements Event {
    final char charTyped;

    public KeyTypedEvent(char charTyped) {
        this.charTyped = charTyped;
    }

    public char getCharTyped() {
        return charTyped;
    }

    @Override
    public String getType() {
        return EventType.KEY_TYPED;
    }
}
