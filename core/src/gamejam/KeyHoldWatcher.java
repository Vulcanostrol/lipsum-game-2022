package gamejam;

import gamejam.event.Event;
import gamejam.event.EventListener;
import gamejam.event.EventQueue;

import java.util.HashMap;

public class KeyHoldWatcher {
    private HashMap<Integer, Boolean> heldKeys;

    public KeyHoldWatcher() {
        heldKeys = new HashMap<>();
        EventListener listener = new EventListener(this::onKeyEvent, "KeyEvent");
        EventQueue.getInstance().registerListener(listener, "KeyEvent");
    }

    private void onKeyEvent(Event keyEvent) {
        // TODO: When key is held, put true in map
    }

    private boolean isKeyHeld(int keyCode) {
        return heldKeys.getOrDefault(keyCode, false);
    }
}
