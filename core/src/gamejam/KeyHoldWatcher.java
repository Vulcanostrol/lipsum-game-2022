package gamejam;

import gamejam.event.Event;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.KeyEvent;

import java.util.HashMap;

public class KeyHoldWatcher {
    private HashMap<Integer, Boolean> heldKeys;

    private EventConsumer<KeyEvent> keyEventEventConsumer;

    public KeyHoldWatcher() {
        heldKeys = new HashMap<>();
        keyEventEventConsumer = this::onKeyEvent;
        EventQueue.getInstance().registerConsumer(keyEventEventConsumer, EventType.KEY_EVENT);
    }

    private void onKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.isKeyDown()) {
            heldKeys.put(keyEvent.getKeyCode(), true);
        } else {
            heldKeys.put(keyEvent.getKeyCode(), false);
        }
    }

    public boolean isKeyHeld(int keyCode) {
        return heldKeys.getOrDefault(keyCode, false);
    }

    public void dispose() {
        EventQueue.getInstance().deregisterConsumer(keyEventEventConsumer, EventType.KEY_EVENT);
    }
}
