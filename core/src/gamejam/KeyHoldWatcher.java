package gamejam;

import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.KeyEvent;

import java.util.HashMap;

public class KeyHoldWatcher {
    private HashMap<Integer, Boolean> heldKeys;

    public KeyHoldWatcher() {
        heldKeys = new HashMap<>();
        EventConsumer<KeyEvent> keyEventEventConsumer = this::onKeyEvent;
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
}
