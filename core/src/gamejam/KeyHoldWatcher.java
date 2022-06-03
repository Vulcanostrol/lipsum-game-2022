package gamejam;

import gamejam.event.Event;
import gamejam.event.EventQueue;

import java.util.HashMap;

public class KeyHoldWatcher {
    private HashMap<Integer, Boolean> heldKeys;

    public KeyHoldWatcher() {
        heldKeys = new HashMap<>();
        EventQueue.getInstance().registerConsumer(this::onKeyEvent, "KeyEvent");
    }

    private void onKeyEvent(Event keyEvent) {
        // TODO: When key is held, put true in map
    }

    public boolean isKeyHeld(int keyCode) {
        return heldKeys.getOrDefault(keyCode, false);
    }
}
